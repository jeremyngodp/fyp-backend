package com.example.demo.assemblers;

import com.example.demo.controller.TaskController;
import com.example.demo.persistences.Task;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {

    @Override
    public EntityModel<Task> toModel(Task task){
        return EntityModel.of(task, linkTo(methodOn(TaskController.class)
                                    .findTaskByID(task.getId()))
                                    .withSelfRel(),
                                    linkTo(methodOn(TaskController.class)
                                    .findTaskByStudentID(task.getStudent_id()))
                                    .withRel("byStudentID"),
                                    linkTo(methodOn(TaskController.class)
                                    .findTaskByProjectId(task.getProject_id()))
                                    .withRel("byProjectID")
                             );
    }
}
