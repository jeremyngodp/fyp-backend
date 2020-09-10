package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.models.Project;


import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("SELECT p FROM Project p WHERE p.supervisor_id = ?1")
    List<Project> findBySupervisorIDEqual (int supervisor_id);
}
