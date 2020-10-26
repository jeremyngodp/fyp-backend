package com.example.demo.services;

import com.example.demo.dto.CommentDTO;
import com.example.demo.persistences.Comment;
import com.example.demo.persistences.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService (CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> findCommentByTask (Task task) {
        return this.commentRepository.findByTask(task);
    }

    private CommentDTO toindivDTO (Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        String email = userRepository.findById(comment.getUser_id())
                                     .getEmail();

        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setTask_id(comment.getTask().getId());
        commentDTO.setUser_id(comment.getUser_id());
        commentDTO.setUser_email(email);
        commentDTO.setCreated_date(comment.getCreated_date());

        return commentDTO;
    }

    public List<CommentDTO> toDTOList (List<Comment> commentList) {
        List<CommentDTO> commentDTOList = commentList.stream().map(this::toindivDTO).collect(Collectors.toList());

        return commentDTOList;
    }

    public CommentDTO addCommentReturnDTO (Comment newComment) {
         Comment addedComment = this.commentRepository.save(newComment);
         CommentDTO result = toindivDTO(addedComment);
         return result;
    }

    public Comment addCommentReturnPersistence(Comment newComment){
        Comment result =  this.commentRepository.save(newComment);

        result.setUser_email(userRepository.findById(newComment.getUser_id())
                                           .getEmail());

        return result;
    }

}
