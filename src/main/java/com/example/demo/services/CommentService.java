package com.example.demo.services;

import com.example.demo.models.Comment;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService (CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findCommentByTaskId (int task_id) {
        return this.commentRepository.findByTaskId(task_id);
    }

    public Comment addComment (Comment newComment) {
        return this.commentRepository.save(newComment);
    }
}
