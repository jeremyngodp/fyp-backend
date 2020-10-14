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

    @GetMapping("/student/{student_id}")
    public CollectionModel<EntityModel<Task>> findTaskByStudentID(@PathVariable("student_id") int student_id){
        List<EntityModel<Task>> tasks = taskService. findTaskByStudentID(student_id).stream().map(taskAssembler::toModel)
                                        .collect(Collectors.toList());

        return CollectionModel.of(tasks, linkTo(methodOn(TaskController.class).findTaskByStudentID(student_id)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Task> findTaskByID(@PathVariable("id") int id) {
        Task task = taskService.findTaskByID(id);
        return taskAssembler.toModel(task);
    }

    @PostMapping("/add/{student_id}")
    public ResponseEntity<?> addTask (@PathVariable("student_id") int student_id,
                                      @RequestParam int project_id,
                                      @RequestParam String description,
                                      @RequestParam Date deadline,
                                      @RequestParam Date created_date,
                                      @RequestParam String task_type) {

        Task newTask = new Task(student_id, project_id, description, deadline, task_type, created_date);

        EntityModel<Task> entityModel = taskAssembler.toModel(taskService.addTask(newTask));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                .toUri())
                .body(entityModel);
    }

}
