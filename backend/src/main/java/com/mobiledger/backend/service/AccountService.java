
package com.mobiledger.backend.service;

import com.mobiledger.backend.dto.CreateAccountRequest;
import com.mobiledger.backend.dto.UpdateAccountRequest;
import com.mobiledger.backend.entity.Account;
import com.mobiledger.backend.entity.User;
import com.mobiledger.backend.repository.AccountRepository;
import com.mobiledger.backend.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(
            AccountRepository accountRepository,
            UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Account createAccount(CreateAccountRequest request) {

        Long userId = getAuthenticatedUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated user not found"));

        Account account = new Account(
                user,
                request.name(),
                request.type(),
                request.balance(),
                request.currency()
        );

        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Long accountId, UpdateAccountRequest request) {

        Long userId = getAuthenticatedUserId();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Account not found"));

        if (!userId.equals(account.getUser().getId())) {
            throw new AccessDeniedException(
                    "You do not have permission to update this account");
        }

        account.updateDetails(
                request.name(),
                request.type(),
                request.balance(),
                request.currency()
        );

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getMyAccounts() {

        Long userId = getAuthenticatedUserId();

        return accountRepository.findByUser_Id(userId);
    }

    private Long getAuthenticatedUserId() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof Long)) {
            throw new IllegalStateException(
                    "An authenticated user is required");
        }

        return (Long) authentication.getPrincipal();
    }
}
