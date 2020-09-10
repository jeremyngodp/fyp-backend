package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.services.UserService;


@RestController
@RequestMapping(path="/fyp/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path="/add")
	public @ResponseBody String addUser (@RequestParam String fname,
									     @RequestParam String lname,
									     @RequestParam String email
										) {
		userService.addUser(fname, lname, email);
		
		return "saved";
	}
	
	@GetMapping(path="/all")
	public  List<User> getAllUsers() {
		return userService.findAllUser();
	}
	
	@GetMapping(value="/{id}")
	public Optional<User> getUserbyId(@PathVariable int id) {
		return userService.findUserbyId(id);
	}
}
