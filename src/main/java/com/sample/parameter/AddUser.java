package com.sample.parameter;

import com.sample.util.Role;
import lombok.Data;

@Data
public class AddUser {

    private final String username;
    private final String password;
    private final String confirmationPassword;
    private final Role role;
}
