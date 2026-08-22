package com.suraj.realestate.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Registration result — no token yet, verify OTP to activate")
@Getter
@Builder
@AllArgsConstructor
public class RegisterResponse {

    @Schema(example = "1")
    private Long userId;

    @Schema(example = "OTP sent to your email, please verify.")
    private String message;
}