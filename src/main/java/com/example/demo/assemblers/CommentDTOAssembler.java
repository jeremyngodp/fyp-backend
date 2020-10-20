package com.example.demo.assemblers;

import com.example.demo.controller.CommentController;
import com.example.demo.dto.CommentDTO;
import com.example.demo.persistences.Comment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentDTOAssembler implements RepresentationModelAssembler<CommentDTO, EntityModel<CommentDTO>> {
    @Override
    public EntityModel<CommentDTO> toModel(CommentDTO commentDTO) {
        return EntityModel.of(commentDTO,linkTo(methodOn(CommentController.class)
                                            .findCommentbyTaskId(commentDTO.getTask_id()))
                                            .withRel("byTaskID"));
    }
}
