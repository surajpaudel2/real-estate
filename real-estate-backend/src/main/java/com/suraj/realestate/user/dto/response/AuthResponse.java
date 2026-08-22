package com.suraj.realestate.user.dto.response;

import com.suraj.realestate.user.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/** Returned after successful login. Never includes the password. */
@Schema(description = "Login result")
@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    private Long id;
    private String name;

    @Schema(example = "jane@example.com")
    private String email;

    @Schema(example = "[\"BUYER\"]")
    private Set<Role> roles;
}