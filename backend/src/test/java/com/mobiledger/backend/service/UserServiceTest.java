package com.mobiledger.backend.service;

import com.mobiledger.backend.dto.RegisterRequest;
import com.mobiledger.backend.entity.User;
import com.mobiledger.backend.exception.DuplicateEmailException;
import com.mobiledger.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserService userService = new UserService(userRepository, passwordEncoder);

    @Test
    void throwsDuplicateEmailExceptionWhenEmailAlreadyExists() {
        when(userRepository.findByEmail("alex@example.com"))
                .thenReturn(Optional.of(mock(User.class)));

        assertThrows(
                DuplicateEmailException.class,
                () -> userService.registerUser(registrationRequest()));

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void savesNewUserWithEncodedPassword() {
        User savedUser = mock(User.class);
        when(userRepository.findByEmail("alex@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secure-password")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(registrationRequest());

        assertSame(savedUser, result);
        verify(passwordEncoder).encode("secure-password");
    }

    private RegisterRequest registrationRequest() {
        return new RegisterRequest(
                "Alex",
                "Ledger",
                "alex@example.com",
                "secure-password");
    }
}
