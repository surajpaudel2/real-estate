package com.suraj.realestate.user.controller;

import com.suraj.realestate.common.openapi.StandardApiResponses;
import com.suraj.realestate.common.response.ApiResponse;
import com.suraj.realestate.user.dto.request.LoginRequest;
import com.suraj.realestate.user.dto.request.RegisterRequest;
import com.suraj.realestate.user.dto.request.VerifyOtpRequest;
import com.suraj.realestate.user.dto.response.AuthResponse;
import com.suraj.realestate.user.dto.response.RegisterResponse;
import com.suraj.realestate.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user and send an OTP for verification")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success(response, response.getMessage()));
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify the OTP sent at registration to activate the account")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<Void>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        authService.verifyOtp(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Account verified successfully."));
    }

    @PostMapping("/login")
    @Operation(summary = "Log in with email and password, returns a JWT")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful."));
    }
}