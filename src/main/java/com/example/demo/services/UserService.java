package com.example.demo.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	@Autowired
	public UserService (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> findAllUser(){
		return userRepository.findAll();
	}
	
	public User findUserbyId(int id) {
		return  userRepository.findById(id).orElse(null);
	}
	
	public User addUser(User user) {
		User new_user= user;
		return userRepository.save(new_user);
	}
}
