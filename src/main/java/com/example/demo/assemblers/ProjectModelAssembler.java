package com.example.demo.assemblers;

import com.example.demo.controller.ProjectController;
import com.example.demo.persistences.Project;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {

    @Override
    public EntityModel<Project> toModel(Project project){
        return EntityModel.of(project, linkTo(methodOn(ProjectController.class)
                                                .findbyID(project.getId()))
                                                .withSelfRel(),
                                        linkTo(methodOn(ProjectController.class)
                                                .findbyStudentID(project.getStudent_id()))
                                                .withRel("byStudentID"),
                                        linkTo(methodOn(ProjectController.class)
                                                .findAllbySupervisorID(project.getSupervisor_id()))
                                                .withRel("bySupervisorID"));
    }
}
