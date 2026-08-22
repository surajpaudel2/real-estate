package com.suraj.realestate.user.dto.request;

import com.suraj.realestate.user.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Schema(description = "Registration payload")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Schema(example = "Jane Doe")
    @NotBlank
    private String name;

    @Schema(example = "jane@example.com")
    @NotBlank
    @Email
    private String email;

    /** Min 8 characters. */
    @NotBlank
    @Size(min = 8)
    private String password;

    @Schema(description = "At least one role required", example = "[\"BUYER\"]")
    @NotEmpty
    private Set<Role> roles;
}