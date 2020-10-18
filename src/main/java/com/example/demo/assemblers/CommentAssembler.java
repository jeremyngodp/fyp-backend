package com.example.demo.assemblers;

import com.example.demo.controller.CommentController;
import com.example.demo.models.Comment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {

    @Override
    public EntityModel<Comment> toModel(Comment comment) {
        return EntityModel.of(comment,linkTo(methodOn(CommentController.class).findCommentbyTaskId(comment.getTask_id())).withRel("byTaskID"));
    }
}
