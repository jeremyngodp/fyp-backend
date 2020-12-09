package com.example.demo.controller;

import com.example.demo.assemblers.TaskModelAssembler;
import com.example.demo.persistences.Project;
import com.example.demo.persistences.Task;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.services.CommentService;
import com.example.demo.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("fyp/api/task")
public class TaskController {

    private final TaskService taskService;
    private final TaskModelAssembler taskAssembler;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskController (TaskService taskService, TaskModelAssembler taskModelAssembler, ProjectRepository projectRepository){
        super();
        this.taskService = taskService;
        this.taskAssembler = taskModelAssembler;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/student/{student_id}")
    public CollectionModel<EntityModel<Task>> findTaskByStudentID(@PathVariable("student_id") int student_id){
        List<EntityModel<Task>> tasks = taskService.findTaskByStudentID(student_id)
                                        .stream()
                                        .map(taskAssembler::toModel)
                                        .collect(Collectors.toList());

        return CollectionModel.of(tasks, linkTo(methodOn(TaskController.class)
                                         .findTaskByStudentID(student_id))
                                         .withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Task> findTaskByID(@PathVariable("id") int id) {
        Task task = taskService.findTaskByID(id);

        return taskAssembler.toModel(task);
    }

    //This endpoint is for StaffPage.jsx to look for all tasks belong to a particular project.
    @GetMapping("/project/{project_id}")
    public CollectionModel<EntityModel<Task>> findTaskByProjectId (@PathVariable("project_id") int project_id) {
        List<EntityModel<Task>> tasks = taskService.findTaskByProjectID(project_id)
                                        .stream()
                                        .map(taskAssembler::toModel)
                                        .collect(Collectors.toList());
        if (tasks.size() == 0) {
            List<EntityModel<Task>> taskList = Collections.emptyList();
            return CollectionModel.of(taskList, linkTo(methodOn(TaskController.class)
                    .findTaskByProjectId(project_id))
                    .withSelfRel());
        }
        return CollectionModel.of(tasks, linkTo(methodOn(TaskController.class)
                .findTaskByProjectId(project_id))
                .withSelfRel());
    }

//    This allow new task to be added through content-type = 'application/x-form-www-urlencoded"
//    @PostMapping("/add")
//    public ResponseEntity<?> addTask (@RequestParam int student_id,
//                                      @RequestParam int project_id,
//                                      @RequestParam String title,
//                                      @RequestParam Date deadline,
//                                      @RequestParam Date created_date,
//                                      @RequestParam String task_type) {
//
//        Task newTask = new Task(student_id, project_id, title, deadline, task_type, created_date);
//
//        EntityModel<Task> entityModel = taskAssembler.toModel(taskService.addTask(newTask));
//
//        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
//                .toUri())
//                .body(entityModel);
//    }

    // This allow new task to be added via POST request with JSON body data.
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EntityModel<Task>> addTask (@RequestBody Task requestBodyTask)  {
        Project project = projectRepository.findById(requestBodyTask.getProject_id()).orElse(null);
        requestBodyTask.setProject(project);
        EntityModel<Task> entityModel = taskAssembler.toModel(taskService.addTask(requestBodyTask));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                .toUri())
                .body(entityModel);
    }

}
