package com.example.demo.jwt;


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
}

