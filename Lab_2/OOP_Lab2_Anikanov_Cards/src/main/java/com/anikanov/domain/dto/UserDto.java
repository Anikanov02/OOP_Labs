package com.anikanov.domain.dto;

import com.anikanov.domain.model.User;
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
public class UserDto {
    @NotNull(message = "First name should not be empty")
    @NotBlank(message = "First name should not be empty")
    @Size(min = 2, max = 64, message = "First name length should be from 2 to 64")
    private String firstName;
    @NotNull(message = "Last name should not be empty")
    @NotBlank(message = "Last name should not be empty")
    @Size(min = 2, max = 64, message = "Last name length should be from 2 to 64")
    private String lastName;
    @NotNull(message = "Phone number should not be empty")
    @NotBlank(message = "Phone number should not be empty")
    @Size(min = 6, max = 16, message = "Phone number length should be from 6 to 16")
    private String phoneNumber;
    @NotNull(message = "Login should not be empty")
    @NotBlank(message = "Login should not be empty")
    @Email(message = "Invalid email format")
    private String login;
    @NotNull(message = "Password should not be empty")
    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, max = 32, message = "password length should be from 8 to 32 symbols")
    private String password;
    @NotNull(message = "Username should not be empty")
    @NotBlank(message = "Username should not be empty")
    @Size(max = 32, message = "Username size should be less than 32")
    private String username;

    public static UserDto convert(User user) {
        final UserDto dto = new UserDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setLogin(user.getLogin());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
