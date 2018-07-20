package com.sample.parameter;

import lombok.Data;

@Data
public class PasswordChange {

    private String oldPassword;
    private String newPassword;
    private String confirmationPassword;
}
