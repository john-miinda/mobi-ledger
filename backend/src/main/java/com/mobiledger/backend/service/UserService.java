package com.mobiledger.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mobiledger.backend.repository.UserRepository;
import com.mobiledger.backend.dto.RegisterRequest;
import com.mobiledger.backend.entity.User;
import com.mobiledger.backend.exception.DuplicateEmailException;
import org.springframework.transaction.annotation.Transactional;
import com.mobiledger.backend.dto.LoginRequest;
import com.mobiledger.backend.exception.InvalidCredentialsException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(RegisterRequest request) {
        if (emailExists(request.email())) {
            throw new DuplicateEmailException("Email is already registered");
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        User user = new User(
            request.firstName(),
            request.lastName(),
            request.email(),
            hashedPassword
        );
        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

 public User loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;
 }
}
