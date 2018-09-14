package com.sample.service;

import com.sample.domain.User;
import com.sample.parameter.PasswordChange;
import com.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionService sessionService;

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> get(String username) {
        return userRepository.findByUsername(username);
    }

    public User add(String username, String password, String confirmationPassword) {
        if (!password.equals(confirmationPassword)) {
            throw new IllegalArgumentException();
        }

        userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
            username,
            passwordEncoder.encode(password),
            AuthorityUtils.createAuthorityList("ROLE_USER")));
        return userRepository.findByUsername(username).orElseThrow();
    }

    public Optional<User> get(Integer id) {
        return userRepository.findById(id);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public void changePassword(PasswordChange passwordChange) {
        if (!passwordChange.getNewPassword().equals(passwordChange.getConfirmationPassword())) {
            throw new IllegalArgumentException();
        }

        String encodedNewPassword = passwordEncoder.encode(passwordChange.getNewPassword());
        userDetailsManager.changePassword(passwordChange.getOldPassword(), encodedNewPassword);
    }

    @Transactional
    public void changeUsername(Integer userId, String newUsername) {
        User user = userRepository.findById(userId).orElseThrow();
        String oldUsername = user.getUsername();
        user.setUsername(newUsername);
        sessionService.delete(oldUsername);
    }
}
