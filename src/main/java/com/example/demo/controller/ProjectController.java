package com.example.demo.controller;

import com.example.demo.models.Project;
import com.example.demo.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demo/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping (path = "/bysup/{sup_id}")
    public List<Project> findAllbySupervisorID(@RequestParam int sup_id){
        return projectService.findBySupervisorID(sup_id);
    }

    @GetMapping (path = "/{id}")
    public Optional<Project> findbyID(@RequestParam int id){
        return projectService.findById(id);
    }
}
