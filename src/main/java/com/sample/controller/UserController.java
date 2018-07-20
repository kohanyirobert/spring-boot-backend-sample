package com.sample.controller;

import com.sample.domain.User;
import com.sample.parameter.PasswordChange;
import com.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public Optional<User> get(@PathVariable("id") Integer id) {
        return userService.get(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("")
    public User add(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String confirmationPassword = map.get("confirmationPassword");
        return userService.add(username, password, confirmationPassword);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody PasswordChange passwordChange) {
        userService.changePassword(passwordChange);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{userId}/change-username")
    public void changePassword(
        @PathVariable("userId") Integer userId,
        @RequestBody Map<String, String> map) {
        String newUsername = map.get("newUsername");
        userService.changeUsername(userId, newUsername);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }
}
