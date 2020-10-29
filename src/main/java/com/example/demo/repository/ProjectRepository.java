package com.example.demo.repository;

import com.example.demo.persistences.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.persistences.Project;


import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT * FROM project WHERE supervisor_id = ?1", nativeQuery = true)
    List<Project> findBySupervisorIDEqual (int supervisor_id);

    @Query(value = "SELECT * FROM project WHERE student_id = ?1" , nativeQuery = true)
    List<Project> findByStudentIDEqual(int student_id);

//    List<Project> findByStudent(User student);

    List<Project> findBySupervisor(User supervisor);
}
