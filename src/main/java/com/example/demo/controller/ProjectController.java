package com.example.demo.controller;

import com.example.demo.assemblers.ProjectModelAssembler;
import com.example.demo.persistences.Project;
import com.example.demo.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/fyp/api/project")
public class ProjectController {

    private ProjectService projectService;
    private ProjectModelAssembler projectAssembler;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectModelAssembler projectModelAssembler) {
        super();
        this.projectService = projectService;
        this.projectAssembler = projectModelAssembler;
    }

    @GetMapping (path = "/bysup/{sup_id}")
    public CollectionModel<EntityModel<Project>> findAllBySupervisorID(@PathVariable("sup_id") int sup_id){
        List<EntityModel<Project>> projectList =  projectService.findBySupervisorID(sup_id)
                                                                .stream()
                                                                .map(projectAssembler::toModel)
                                                                .collect(Collectors.toList());

        return CollectionModel.of(projectList, linkTo(methodOn(ProjectController.class)
                              .findAllBySupervisorID(sup_id))
                              .withSelfRel());

    }

    @GetMapping (path = "/bystu/{stu_id}")
    public CollectionModel<EntityModel<Project>> findByStudentID(@PathVariable ("stu_id") int stu_id){
        List<EntityModel<Project>> projectList = projectService.findbyStudentID(stu_id)
                                                                .stream()
                                                                .map(projectAssembler::toModel)
                                                                .collect(Collectors.toList());

        return CollectionModel.of(projectList,linkTo(methodOn(ProjectController.class)
                              .findByStudentID(stu_id))
                              .withSelfRel());
    }

    @GetMapping (path = "/{id}")
    public EntityModel<Project> findByID(@PathVariable("id") int id){
        return projectAssembler.toModel(projectService.findById(id));
    }
}
