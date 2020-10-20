package com.example.demo.repository;

import com.example.demo.persistences.Comment;
import com.example.demo.persistences.Task;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
//    @Query("SELECT c FROM Comment c WHERE c.task_id = ?1")
    List<Comment> findByTask(Task task);
}
