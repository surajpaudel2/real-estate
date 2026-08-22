package com.suraj.realestate.user.mapper;

import com.suraj.realestate.user.dto.response.RegisterResponse;
import com.suraj.realestate.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterResponseMapToMapper {

    public RegisterResponse mapFromUser(User user) {
        return RegisterResponse.builder()
                .userId(user.getId())
                .message("OTP sent to your email, please verify.")
                .build();
    }

}
