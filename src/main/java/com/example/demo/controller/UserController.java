package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.assemblers.UserModelAssembler;
import com.example.demo.dto.UserDTO;
import com.example.demo.jwt.JwtRequest;
import com.example.demo.jwt.JwtResponse;
import com.example.demo.jwt.JwtTokenUtil;
import com.example.demo.jwt.UserService;
import com.example.demo.persistences.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping(path="/fyp/api/user")
public class UserController {

	private final UserService userService;
	private final UserModelAssembler userAssembler;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public UserController (UserService userService,
						   UserModelAssembler userModelAssembler,
						   AuthenticationManager authenticationManager,
						   JwtTokenUtil jwtTokenUtil) {
		super();
		this.userAssembler = userModelAssembler;
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
	}
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addUser (@RequestBody UserDTO userdto) {
		User newUser = new User();
		newUser.setUsername(userdto.getUsername());
		newUser.setPassword(userdto.getPassword());
		newUser.setEmail(userdto.getEmail());
		newUser.setFname(userdto.getFname());
		newUser.setLname(userdto.getLname());
		newUser.setIs_staff(userdto.isIs_staff());
		EntityModel<User> entityModel = userAssembler.toModel(userService.addUser(newUser));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri())
				.body(entityModel);
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		User user = userService.findUserByUsername(authenticationRequest.getUsername());

		return ResponseEntity.ok(new JwtResponse(token, user));
	}

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

	@GetMapping(value="/all")
	public CollectionModel<EntityModel<User>> getAllUsers() {
		List<EntityModel<User>> users = userService.findAllUser()
										.stream()
										.map(userAssembler::toModel)
										.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}

	@GetMapping(value="/{username}")
	public EntityModel<User> getUserByUsername(@PathVariable String username) {
		User user = userService.findUserByUsername(username);

		return userAssembler.toModel(user);
	}
}