
package com.mobiledger.backend.controller;

import com.mobiledger.backend.dto.AccountResponse;
import com.mobiledger.backend.dto.CreateAccountRequest;
import com.mobiledger.backend.dto.UpdateAccountRequest;
import com.mobiledger.backend.entity.Account;
import com.mobiledger.backend.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        Account account = accountService.createAccount(request);

        AccountResponse response = new AccountResponse(
                account.getId(),
                account.getName(),
                account.getType(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getMyAccounts() {

        List<AccountResponse> accounts = accountService.getMyAccounts()
                .stream()
                .map(account -> new AccountResponse(
                        account.getId(),
                        account.getName(),
                        account.getType(),
                        account.getBalance(),
                        account.getCurrency(),
                        account.getCreatedAt()
                ))
                    .collect(Collectors.toList());

        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAccountRequest request) {

        Account account = accountService.updateAccount(id, request);

        AccountResponse response = new AccountResponse(
                account.getId(),
                account.getName(),
                account.getType(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}
