package com.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Iterable<String> getAll() {
        return jdbcTemplate.queryForList("SELECT DISTINCT authority FROM authorities", String.class);
    }
}
