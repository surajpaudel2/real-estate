package com.suraj.realestate.user.mapper;

import com.suraj.realestate.user.dto.response.AuthResponse;
import com.suraj.realestate.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthResponseMapToMapper {

    public AuthResponse mapFromUserAndToken(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

}
