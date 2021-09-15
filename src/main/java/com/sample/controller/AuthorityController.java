package com.sample.controller;

import com.sample.service.AuthorityService;
import com.sample.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/authorities")
    public Iterable<Role> getAll() {
        return authorityService.getAll();
    }
}
