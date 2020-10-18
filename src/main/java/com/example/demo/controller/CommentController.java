package com.example.demo.controller;

import com.example.demo.assemblers.CommentAssembler;
import com.example.demo.models.Comment;
import com.example.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("fyp/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentAssembler commentAssembler;

    @Autowired
    public CommentController(CommentService commentService, CommentAssembler commentAssembler) {
        super();
        this.commentService = commentService;
        this.commentAssembler = commentAssembler;
    }

    @GetMapping("/task")
    public CollectionModel<EntityModel<Comment>> findCommentbyTaskId(@RequestParam int task_id) {
        List<EntityModel<Comment>> commentsbytaskid = commentService.findCommentByTaskId(task_id)
                                                      .stream()
                                                      .map(commentAssembler::toModel)
                                                      .collect(Collectors.toList());

        return CollectionModel.of(commentsbytaskid, linkTo(methodOn(CommentController.class).findCommentbyTaskId(task_id)).withSelfRel());
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EntityModel<Comment>> addComment (@RequestBody Comment newComment) {
        EntityModel<Comment> entityModel = commentAssembler.toModel(commentService.addComment(newComment));

        return ResponseEntity.created(entityModel.getRequiredLink("byTaskID").toUri())
                .body(entityModel);
    }
}
