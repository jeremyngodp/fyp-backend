package com.example.demo.jwt;

import com.example.demo.persistences.Project;
import com.example.demo.persistences.User;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {
    private final String jwttoken;
    private final User user;
    private final List<Project> projects;

    public JwtResponse(String jwttoken, User user, List<Project> projects) {
        this.jwttoken = jwttoken;
        this.user = user;
        this.projects = projects;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public User getUser() {
        return this.user;
    }

    public List<Project> getProjects() {
        return this.projects;
    }
}
