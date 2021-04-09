package com.example.demo.controller;

import com.example.demo.assemblers.CommentAssembler;
import com.example.demo.assemblers.CommentDTOAssembler;
import com.example.demo.dto.CommentDTO;
import com.example.demo.persistences.Comment;
import com.example.demo.persistences.Task;
import com.example.demo.services.CommentService;
import com.example.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("fyp/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentAssembler commentAssembler;
    private final TaskService taskService;
    private final CommentDTOAssembler commentDTOAssembler;

    @Autowired
    public CommentController(CommentService commentService,
                             CommentAssembler commentAssembler,
                             TaskService taskService,
                             CommentDTOAssembler commentDTOAssembler) {

        super();
        this.commentService = commentService;
        this.commentAssembler = commentAssembler;
        this.taskService = taskService;
        this.commentDTOAssembler =commentDTOAssembler;
    }

    //This might no longer be necessary
    @GetMapping("/task")
    public CollectionModel<EntityModel<CommentDTO>> findCommentbyTaskId(@RequestParam int task_id) {

        Task task = this.taskService.findTaskByID(task_id);
        List<Comment> commentsbytaskid = commentService.findCommentByTask(task);
        List<CommentDTO> commentDTOList =  this.commentService.toDTOList(commentsbytaskid);
        List<EntityModel<CommentDTO>> result = commentDTOList.stream()
                                                             .map(commentDTOAssembler::toModel)
                                                             .collect(Collectors.toList());

        return CollectionModel.of(result, linkTo(methodOn(CommentController.class)
                                          .findCommentbyTaskId(task_id))
                                          .withSelfRel());
    }

    // This method use a DTO to send and receive data
    @PostMapping(value = "/addByDto")
    public ResponseEntity<EntityModel<CommentDTO>> addCommentByDTO ( @RequestBody CommentDTO newCommentdto) {

        /*
         The Comment DTO does not have 'task' property and only task_id
         so need to find the corresponding task.
        */
        Task task = this.taskService.findTaskByID((newCommentdto.getTask_id()));

        /*
         To actually add the new Comment to the database, a Comment object must be used, not a DTO
         so a comment object is created from the properties extracted from the DTO.
        */
        Comment newComment = new Comment( task,
                                          newCommentdto.getUser_id(),
                                          newCommentdto.getContent(),
                                          newCommentdto.getCreated_date());

        /*
         This method take in a Comment, add it to the database with the JpaRepository.save() method,
         then create a new CommentDTO from the newly added Comment and return.
        */
        CommentDTO addedCommentDTO =  commentService.addCommentReturnDTO(newComment);

        /* Then the DTO is placed in the body of the response. */
        EntityModel<CommentDTO> entityModel =  commentDTOAssembler.toModel(addedCommentDTO);
        return ResponseEntity.created(entityModel.getRequiredLink("byTaskID").toUri())
                .body(entityModel);
    }

    //This method will assign incoming data into a Comment entity directly
    @PostMapping(value = "/add")
    public ResponseEntity<EntityModel<Comment>> addComment ( @RequestBody Comment newComment) {

        Task task = this.taskService.findTaskByID((newComment.getTask_id()));

        newComment.setTask(task);

        Comment addedComment =  commentService.addCommentReturnPersistence(newComment);

        EntityModel<Comment> entityModel =  commentAssembler.toModel(addedComment);
        return ResponseEntity.created(entityModel.getRequiredLink("byTaskID").toUri())
                .body(entityModel);
    }
}
