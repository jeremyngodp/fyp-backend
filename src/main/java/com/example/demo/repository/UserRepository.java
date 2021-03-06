package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistences.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findById(int id);

    User findByUsername(String username);
}
