package com.example.springboot6.controller;


import com.example.springboot6.dto.UserRequest;
import com.example.springboot6.dto.UserResponse;
import com.example.springboot6.entity.User;
import com.example.springboot6.jwt.JwtUtil;
import com.example.springboot6.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil util;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User user) {

        userRepo.save(user);

        return ResponseEntity.ok("saved");
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {

       String token=util.generateToken(userRequest.getUsername());
        return ResponseEntity.ok(new UserResponse(token,"Token Generated."));
    }


}
