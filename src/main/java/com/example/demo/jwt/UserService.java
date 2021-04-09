package com.example.demo.jwt;


import com.example.demo.persistences.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.persistences.User;
import com.example.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(),
																	  user.getPassword(),
																	  new ArrayList<>());
	}

	public void resetPassword(String username) {
		User user = userRepository.findByUsername(username);
		user.setPassword((bcryptEncoder.encode("NtuIsGreat")));
		userRepository.save(user);
	}

	public void remove(String username) {
		User user = userRepository.findByUsername(username);
		userRepository.delete(user);
//		List<Project> projects = projectRepository.findByStudentIDEqual(user.getId());
//		projects.stream().map(project -> {
//			project.setStudent_id(0);
//			project.setStudent(null);
//			return projectRepository.save(project);
//		});
	}

	public void changePassword(String username, String password) {
		User user = userRepository.findByUsername(username);
		user.setPassword((bcryptEncoder.encode(password)));
		System.out.println(password);
		userRepository.save(user);
	}

	public User addUser(User user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findUserByID (int id) {
		return userRepository.findById(id);
	}
}

