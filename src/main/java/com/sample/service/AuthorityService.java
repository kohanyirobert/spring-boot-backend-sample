package com.sample.service;

import com.sample.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Iterable<Role> getAll() {
        return jdbcTemplate.queryForList("SELECT DISTINCT authority FROM authorities",  String.class)
            .stream()
            .map(Role::fromAuthority)
            .toList();
    }
}
