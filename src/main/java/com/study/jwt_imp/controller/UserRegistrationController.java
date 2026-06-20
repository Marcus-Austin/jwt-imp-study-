package com.study.jwt_imp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.jwt_imp.dto.UserDto;
import com.study.jwt_imp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        log.info("Registering user: {}",userDto);
        boolean status = userService.registerUser(userDto);
        if (status) 
            return ResponseEntity.ok("User registered successfully");
      
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed");
    }
}
