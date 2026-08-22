package com.suraj.realestate.user.validation;

import com.suraj.realestate.user.dto.request.RegisterRequest;
import com.suraj.realestate.user.exception.EmailAlreadyExistsException;
import com.suraj.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterRequestValidator {

    private final UserRepository userRepository;

        public void validatePassword(RegisterRequest request) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("An account with this email already exists.");
            }

        }

}
