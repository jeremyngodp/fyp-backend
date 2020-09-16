package com.example.demo.controller;

import com.example.demo.assemblers.TaskModelAssembler;
import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("fyp/api/task")
public class TaskController {


    private TaskService taskService;
    private TaskModelAssembler taskAssembler;

    @Autowired
    public TaskController (TaskService taskService, TaskModelAssembler taskModelAssembler){
        super();
        this.taskService = taskService;
        this.taskAssembler = taskModelAssembler;
    }

    @GetMapping("/student/{student_id}/all")
    public CollectionModel<EntityModel<Task>> findTaskByStudentID(@RequestParam int student_id){
        List<EntityModel<Task>> tasks = taskService. findTaskByStudentID(student_id).stream().map(taskAssembler::toModel)
                                        .collect(Collectors.toList());

        return CollectionModel.of(tasks, linkTo(methodOn(TaskController.class).findTaskByStudentID(student_id)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Task> findTaskByID(@RequestParam int id) {
        Task task = taskService.findTaskByID(id);
        return taskAssembler.toModel(task);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTask (@RequestParam int student_id,
                                   @RequestParam int project_id,
                                   @RequestParam String description,
                                   @RequestParam Date deadline ) {

        Task newTask = new Task();
        newTask.setAssignee_id(student_id);
        newTask.setProject_id(project_id);
        newTask.setDescription(description);
        newTask.setDeadline(deadline);
        EntityModel<Task> entityModel = taskAssembler.toModel(taskService.addTask(newTask));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                .toUri())
                .body(entityModel);
    }

}
