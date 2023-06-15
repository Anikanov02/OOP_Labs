package com.anikanov.service;

import com.anikanov.domain.Role;
import com.anikanov.domain.dto.UserDto;
import com.anikanov.domain.model.User;
import com.anikanov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    public User update(UserDto dto) throws Exception {
        final User user = userRepository.findByLogin(dto.getLogin());
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new Exception("This username is already taken");
        }
        if (Objects.nonNull(user)) {
            user.setPassword(dto.getPassword());
            user.setUsername(dto.getUsername());
            return userRepository.save(user);
        }
        return null;
    }

    public User createUser(UserDto dto) throws Exception {
        final User user = User.builder()
                .role(Role.USER)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNumber(dto.getPhoneNumber())
                .password(dto.getPassword())
                .login(dto.getLogin())
                .username(dto.getUsername())
                .build();
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (userRepository.findByUsername(dto.getUsername()) != null) {
                throw new Exception("This username is already taken", e);
            } else if (userRepository.findByPhoneNumber(dto.getPhoneNumber()) != null) {
                throw new Exception("This phone number is already taken", e);
            } else if (userRepository.findByLogin(dto.getLogin()) != null) {
                throw new Exception("This login is already taken", e);
            } else {
                throw new Exception(e);
            }
        }
    }
}
