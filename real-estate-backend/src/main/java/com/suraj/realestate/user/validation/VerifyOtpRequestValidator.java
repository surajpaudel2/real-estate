package com.suraj.realestate.user.validation;

import com.suraj.realestate.user.dto.request.VerifyOtpRequest;
import com.suraj.realestate.user.entity.Otp;
import com.suraj.realestate.user.exception.InvalidOtpException;
import com.suraj.realestate.user.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VerifyOtpRequestValidator {

    private final OtpRepository otpRepository;

    public Otp validate(VerifyOtpRequest request) {
        Otp otpEntity = otpRepository.findByUserIdAndOtp(request.getUserId(), request.getOtp())
                .orElseThrow(() -> new InvalidOtpException("Invalid or expired OTP."));

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidOtpException("Invalid or expired OTP.");
        }

        return otpEntity;
    }

}
