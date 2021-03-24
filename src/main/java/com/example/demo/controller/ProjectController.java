package com.example.demo.controller;

import com.example.demo.assemblers.ProjectModelAssembler;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.jwt.UserService;
import com.example.demo.persistences.Project;
import com.example.demo.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/fyp/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectModelAssembler projectAssembler;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectModelAssembler projectModelAssembler, UserService userService) {
        super();
        this.projectService = projectService;
        this.projectAssembler = projectModelAssembler;
        this.userService = userService;
    }

    @GetMapping (path = "/all")
    public CollectionModel<EntityModel<Project>> findAll(){
        List<EntityModel<Project>> projectList =  projectService.findAll()
                .stream()
                .map(projectAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(projectList, linkTo(methodOn(ProjectController.class)
                .findAll())
                .withSelfRel());

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

    @PutMapping (path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> editByID(@RequestBody ProjectDTO projectDTO, @PathVariable int id){
        Project project =  projectService.findById(id);
        project.setStudent_id(projectDTO.getStudent_id());
        project.setStudent(userService.findUserByID(projectDTO.getStudent_id()));
        project.setSupervisor(userService.findUserByID(projectDTO.getSupervisor_id()));
        project.setDescription(projectDTO.getDescription());
        project.setName(projectDTO.getName());
        EntityModel<Project> projectEntityModel =  projectAssembler.toModel(projectService.addProject(project));
        return ResponseEntity
                .created(projectEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(projectEntityModel);
    }

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EntityModel<Project>> addProject(@RequestBody ProjectDTO projectDTO) {
        Project newProject =  new Project();
        newProject.setSupervisor(userService.findUserByID(projectDTO.getSupervisor_id()));
        newProject.setStudent_id(projectDTO.getStudent_id());
        newProject.setName(projectDTO.getName());
        newProject.setDescription((projectDTO.getDescription()));

        Project addedProject = projectService.addProject(newProject);
        EntityModel<Project> entityModel = projectAssembler.toModel(addedProject);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }
}
