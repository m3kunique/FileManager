package com.m3k.CloudFileStorage.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    private Integer id;

    @NotEmpty(message = "Login should not be empty")
    @Size(min = 4, max = 30, message = "Login must consist of at least 4 characters")
    private String login;

    @Size(min = 4, max = 30, message = "Password must consist of at least 4 characters")
    private String password;

    private String confirmedPassword;
}
