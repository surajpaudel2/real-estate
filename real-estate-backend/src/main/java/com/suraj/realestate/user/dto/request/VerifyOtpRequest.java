package com.suraj.realestate.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** OTP verification payload. userId comes from the register response. */
@Schema(description = "OTP verification payload")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpRequest {

    @Schema(example = "1")
    @NotNull
    private Long userId;

    @Schema(example = "482913")
    @NotBlank
    private String otp;
}