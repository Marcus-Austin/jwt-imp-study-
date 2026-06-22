package com.study.jwt_imp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secured")
public class ProtectedController {

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public  String userAcess(){
    return "Hi user you are allowed";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAcess(){
        return "Hi admin you are allowed";
    }

}
