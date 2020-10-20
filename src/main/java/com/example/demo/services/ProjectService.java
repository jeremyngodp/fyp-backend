package com.example.demo.services;

import com.example.demo.persistences.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService (ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findBySupervisorID (int id) {
        return this.projectRepository.findBySupervisorIDEqual(id);
    }

    public List<Project> findbyStudentID (int id) {
        return this.projectRepository.findByStudentIDEqual(id);
    }

    public Optional<Project> findById (int id) {
        return this.projectRepository.findById(id);
    }
}
