package com.example.demo.jwt;

import com.example.demo.persistences.User;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private final String jwttoken;
    private final User user;

    public JwtResponse(String jwttoken, User user) {
        this.jwttoken = jwttoken;
        this.user = user;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public User getUser() {
        return this.user;
    }
}
