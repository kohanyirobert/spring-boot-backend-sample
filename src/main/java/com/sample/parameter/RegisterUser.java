package com.sample.parameter;

import lombok.Data;

@Data
public class RegisterUser {

    private final String username;
    private final String password;
    private final String confirmationPassword;
}
