package com.example.demo.repository;

import com.example.demo.persistences.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistences.Task;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE t.student_id = ?1")
    List<Task> findbystudentID (int student_id);

//    @Query("SELECT t FROM Task t WHERE t.project_id = ?1")
    List<Task> findByProject(Project project);

    @Query(value = "SELECT * FROM task WHERE project_id = ?1", nativeQuery = true)
    List<Task> findByProjectId(int project_id);
}
