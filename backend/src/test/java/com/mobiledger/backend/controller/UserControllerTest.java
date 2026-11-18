package com.mobiledger.backend.controller;

import com.mobiledger.backend.entity.User;
import com.mobiledger.backend.exception.DuplicateEmailException;
import com.mobiledger.backend.exception.GlobalExceptionHandler;
import com.mobiledger.backend.security.SecurityConfig;
import com.mobiledger.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void registersAnUnauthenticatedRequestWithCreatedAndNoPasswordFields() throws Exception {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        when(user.getFirstName()).thenReturn("Alex");
        when(user.getLastName()).thenReturn("Ledger");
        when(user.getEmail()).thenReturn("alex@example.com");
        when(userService.registerUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("""
                                {
                                  "firstName": "Alex",
                                  "lastName": "Ledger",
                                  "email": "alex@example.com",
                                  "password": "secure-password"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("alex@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    void returnsConflictWhenEmailIsAlreadyRegistered() throws Exception {
        when(userService.registerUser(any()))
                .thenThrow(new DuplicateEmailException("Email is already registered"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("""
                                {
                                  "firstName": "Alex",
                                  "lastName": "Ledger",
                                  "email": "alex@example.com",
                                  "password": "secure-password"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email is already registered"));
    }
}
