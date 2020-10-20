package com.example.demo.services;

import com.example.demo.dto.CommentDTO;
import com.example.demo.persistences.Comment;
import com.example.demo.persistences.Task;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService (CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findCommentByTask (Task task) {
        return this.commentRepository.findByTask(task);
    }

    private CommentDTO toindivDTO (Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setTask_id(comment.getTask().getId());
        commentDTO.setUser_id(comment.getUser_id());
        commentDTO.setCreated_date(comment.getCreated_date());

        return commentDTO;
    }
    public List<CommentDTO> toDTOList (List<Comment> commentList) {
        List<CommentDTO> commentDTOList = commentList.stream().map(this::toindivDTO).collect(Collectors.toList());

        return commentDTOList;
    }

    public Comment addComment (Comment newComment) {
        return this.commentRepository.save(newComment);
    }
}
