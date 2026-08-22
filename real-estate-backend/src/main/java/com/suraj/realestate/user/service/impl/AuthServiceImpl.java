package com.suraj.realestate.user.service.impl;

import com.suraj.realestate.common.email.EmailService;
import com.suraj.realestate.common.security.JwtService;
import com.suraj.realestate.common.security.UserPrincipal;
import com.suraj.realestate.notification.service.NotificationSender;
import com.suraj.realestate.user.dto.request.LoginRequest;
import com.suraj.realestate.user.dto.request.RegisterRequest;
import com.suraj.realestate.user.dto.request.VerifyOtpRequest;
import com.suraj.realestate.user.dto.response.AuthResponse;
import com.suraj.realestate.user.dto.response.RegisterResponse;
import com.suraj.realestate.user.entity.Otp;
import com.suraj.realestate.user.entity.User;
import com.suraj.realestate.user.exception.EmailAlreadyExistsException;
import com.suraj.realestate.user.exception.InvalidOtpException;
import com.suraj.realestate.user.mapper.OtpMapToMapper;
import com.suraj.realestate.user.mapper.RegisterResponseMapToMapper;
import com.suraj.realestate.user.mapper.UserMapToMapper;
import com.suraj.realestate.user.repository.OtpRepository;
import com.suraj.realestate.user.repository.UserRepository;
import com.suraj.realestate.user.service.AuthService;
import com.suraj.realestate.user.validation.RegisterRequestValidator;
import com.suraj.realestate.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final NotificationSender notificationSender;
    private final UserMapToMapper usermaptomapper;
    private final OtpMapToMapper otpmaptomapper;
    private final RegisterResponseMapToMapper registerresponsemaptomapper;
    private final UserValidator userValidator;
    private final RegisterRequestValidator registerRequestValidator;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        registerRequestValidator.validatePassword(request);

        User user = usermaptomapper.mapFromRegisterRequest(request);
        user = userRepository.save(user);

        Otp otpEntity = otpmaptomapper.mapFromUser(user);
        otpRepository.save(otpEntity);

        emailService.sendHtmlEmail(
                user.getEmail(),
                "Verify your account",
                "email/otp-verification",
                Map.of("name", user.getName(), "otp", otpEntity.getOtp())
        );

        return registerresponsemaptomapper.mapFromUser(user);
    }

    @Override
    @Transactional
    public void verifyOtp(VerifyOtpRequest request) {
        Otp otpEntity = otpRepository.findByUserIdAndOtp(request.getUserId(), request.getOtp())
                .orElseThrow(() -> new InvalidOtpException("Invalid or expired OTP."));

        if (otpEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidOtpException("Invalid or expired OTP.");
        }

        User user = otpEntity.getUser();
        user.setEmailVerified(true);
        user.setActive(true);
        userRepository.save(user);

        otpRepository.delete(otpEntity);

        notificationSender.send(
                user.getEmail(),
                "Welcome!",
                "email/welcome",
                Map.of("name", user.getName())
        );
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();

        String token = jwtService.generateToken(principal);

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }


}