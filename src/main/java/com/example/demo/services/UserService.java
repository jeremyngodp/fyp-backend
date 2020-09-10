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
		Iterable<User> useriter =  this.userRepository.findAll();
		List<User> userlist = new ArrayList<>();
		useriter.forEach(user -> {
			User singleuser = new User();
			singleuser.setFname(user.getFname());
			singleuser.setLname(user.getLname());
			singleuser.setEmail(user.getEmail());
			userlist.add(singleuser);
		});
		
		return userlist;
	}
	
	public Optional<User> findUserbyId(int id) {
		return  userRepository.findById(id);
	}
	
	public void addUser(String fname, String lname, String email) {
		User new_user= new User();
		new_user.setFname(fname);
		new_user.setLname(lname);
		new_user.setEmail(email);
		
		userRepository.save(new_user);
	}
}
