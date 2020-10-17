package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.assemblers.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.User;
import com.example.demo.services.UserService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping(path="/fyp/api/user")
public class UserController {

	private UserService userService;
	private UserModelAssembler userAssembler;

	@Autowired
	public UserController (UserService userService, UserModelAssembler userModelAssembler) {
		super();
		this.userAssembler = userModelAssembler;
		this.userService = userService;
	}
	@PostMapping(value="/add")
	public ResponseEntity<?> addUser (@RequestParam String fname,
									  @RequestParam String lname,
									  @RequestParam String email,
									  @RequestParam boolean is_staff
										) {

		User newUSer = new User(fname, lname, email, is_staff);
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
	
	@GetMapping(value="/{email}")
	public EntityModel<User> getUserByEmail(@PathVariable String email) {
		User user = userService.findUserByEmail(email);

		return userAssembler.toModel(user);
	}
}
