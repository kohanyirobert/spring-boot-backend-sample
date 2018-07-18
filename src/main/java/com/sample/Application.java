package com.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@SpringBootApplication
public class Application extends WebSecurityConfigurerAdapter {

    // Allows authentication through the /auth endpoint using GET requests exclusively
    // using HTTP basic authentication. With this configuration no other endpoint allows this.
    @Configuration
    @Order(1)
    public static class AuthConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            http.antMatcher("/auth")
                .authorizeRequests()
                .antMatchers(HttpMethod.GET)
                .authenticated()
                .and()
                .httpBasic();
        }
    }

    // Configurations access to the API endpoints which require authenticated sessions (i.e. /users)
    // and for some that doesn't requires authentication at all (i.e. /register).
    @Configuration
    @Order(2)
    public static class ApiConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .anyRequest().authenticated();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Official way to expose the AuthenticationManager as a bean.
    // https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide#authenticationmanager-bean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    UserDetailsManager userDetailsManager(
        @Autowired DataSource dataSource,
        @Autowired JdbcTemplate jdbcTemplate,
        @Autowired AuthenticationManager authenticationManager) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setJdbcTemplate(jdbcTemplate);
        userDetailsManager.setDataSource(dataSource);
        // Using AuthenticationManager re-authenticates the session on password change.
        userDetailsManager.setAuthenticationManager(authenticationManager);
        return userDetailsManager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
