package com.suraj.realestate.user.service;

import com.suraj.realestate.user.dto.request.LoginRequest;
import com.suraj.realestate.user.dto.request.RegisterRequest;
import com.suraj.realestate.user.dto.request.VerifyOtpRequest;
import com.suraj.realestate.user.dto.response.AuthResponse;
import com.suraj.realestate.user.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    void verifyOtp(VerifyOtpRequest request);
    AuthResponse login(LoginRequest request);
}