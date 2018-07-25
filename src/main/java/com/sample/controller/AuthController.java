package com.sample.controller;

import com.sample.domain.User;
import com.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public User get(Principal principal) {
        return userRepository.findByUsername(principal.getName())
            .orElseThrow();
    }

    @DeleteMapping("")
    public void delete(HttpSession session) {
        session.invalidate();
    }
}
