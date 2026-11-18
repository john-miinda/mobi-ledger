package com.mobiledger.backend.controller;

import com.mobiledger.backend.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import com.mobiledger.backend.dto.RegisterRequest;
import com.mobiledger.backend.dto.LoginRequest;
import com.mobiledger.backend.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;
import com.mobiledger.backend.security.JwtService;

@RestController
@RequestMapping("api/auth")
public class UserController {
    private final UserService userService;
private final JwtService jwtService;

public UserController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
}

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(
            @Valid @RequestBody RegisterRequest request) {
                User user = userService.registerUser(request);

                Map<String, Object> response = Map.of(
                    "message", "User registered successfully",
                    "id", user.getId(),
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName(),
                    "email", user.getEmail()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(
            @Valid @RequestBody LoginRequest request) {
                User user = userService.loginUser(request);
                String token = jwtService.generateToken(user.getId());
                Map<String, Object> response = Map.of(
                    "message", "Login successful",
                    "id", user.getId(),
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName(),
                    "email", user.getEmail(),
                    "token", token
                );
                return ResponseEntity.ok(response);
            }

}
