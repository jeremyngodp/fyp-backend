package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.assemblers.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.services.UserService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path="/fyp/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserModelAssembler userAssembler;

	@PostMapping(value="/add")
	public ResponseEntity<?> addUser (@RequestParam String fname,
									  @RequestParam String lname,
									  @RequestParam String email
										) {

		User newUSer = new User(fname, lname, email);
		EntityModel<User> entityModel = userAssembler.toModel(userService.addUser(newUSer));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
	}
	
	@GetMapping(value="/all")
	public CollectionModel<EntityModel<User>> getAllUsers() {
		List<EntityModel<User>> users = userService.findAllUser().stream().map(userAssembler::toModel)
										.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}
	
	@GetMapping(value="/{id}")
	public EntityModel<User> getUserbyId(@PathVariable int id) {
		User user = userService.findUserbyId(id);

		return userAssembler.toModel(user);
	}
}
