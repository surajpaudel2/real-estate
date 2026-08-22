package com.suraj.realestate.user.mapper;

import com.suraj.realestate.user.dto.request.RegisterRequest;
import com.suraj.realestate.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapToMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapFromRegisterRequest(RegisterRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();
    }
}
