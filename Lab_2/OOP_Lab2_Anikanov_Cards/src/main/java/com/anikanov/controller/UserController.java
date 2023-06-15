package com.anikanov.controller;

import com.anikanov.domain.dto.LoginDto;
import com.anikanov.domain.dto.UserDto;
import com.anikanov.domain.model.User;
import com.anikanov.service.PermissionService;
import com.anikanov.service.SecurityService;
import com.anikanov.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;
    private final PermissionService permissionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto data) {
        securityService.login(data.getLogin(), data.getPassword());
        log.debug("logged new user: " + data.getLogin());
        return new ResponseEntity<>(UserDto.convert(userService.findByLogin(data.getLogin())), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto) {
        try {
            userService.createUser(userDto);
            log.debug("registered new user: " + userDto.getFirstName() + " " + userDto.getLastName());
            securityService.login(userDto.getLogin(), userDto.getPassword());
            return new ResponseEntity<>(UserDto.convert(userService.findByLogin(userDto.getLogin())), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("exception during registration: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserData(@PathVariable Long userId, Principal auth) {
        if (permissionService.isAuthenticated(userId, auth.getName())) {
            final User user = userService.findById(userId);
            return new ResponseEntity<>(UserDto.convert(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody @Valid UserDto userDto, Principal auth) {
        if (permissionService.isAuthenticated(userId, auth.getName())) {
            try {
                final User user = userService.update(userDto);
                return new ResponseEntity<>(UserDto.convert(user), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, Principal auth) {
        if (permissionService.isAuthenticated(userId, auth.getName())) {
            userService.delete(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
