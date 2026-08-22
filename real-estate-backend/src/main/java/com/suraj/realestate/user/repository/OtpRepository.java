package com.suraj.realestate.user.repository;

import com.suraj.realestate.user.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByUserIdAndOtp(Long userId, String otp);

    long deleteByExpiresAtBefore(LocalDateTime now);
}