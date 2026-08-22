package com.suraj.realestate.user.mapper;

import com.suraj.realestate.user.entity.Otp;
import com.suraj.realestate.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OtpMapToMapper {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int OTP_VALIDITY_MINUTES = 10;

    public Otp mapFromUser(User user) {
        String otp = generateOtp();

        return Otp.builder()
                .otp(otp)
                .generatedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES))
                .user(user)
                .build();
    }

    private String generateOtp() {
        return String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));
    }

}
