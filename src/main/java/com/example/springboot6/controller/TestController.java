package com.example.springboot6.controller;


import com.example.springboot6.entity.User;
import com.example.springboot6.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TestController{

    @Autowired
    private UserRepo userRepo;



    @PostMapping("/addUser")
    public Optional<User> addUser(@RequestBody User user){


        return Optional.of(userRepo.save(user));

    }
    

}
