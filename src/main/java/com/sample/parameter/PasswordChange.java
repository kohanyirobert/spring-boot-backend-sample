package com.sample.parameter;

import lombok.Data;

@Data
public class PasswordChange {

    private final String oldPassword;
    private final String newPassword;
    private final String confirmationPassword;
}
