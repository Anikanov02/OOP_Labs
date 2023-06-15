package com.anikanov.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull(message = "Login should not be empty")
    @NotBlank(message = "Login should not be empty")
    @Email(message = "Invalid email format")
    private String login;
    @NotNull(message = "Password should not be empty")
    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, max = 32, message = "password length should be from 8 to 32 symbols")
    private String password;
}
