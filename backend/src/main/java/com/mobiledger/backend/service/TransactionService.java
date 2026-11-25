
package com.mobiledger.backend.service;

import com.mobiledger.backend.dto.CreateTransactionRequest;
import com.mobiledger.backend.entity.Account;
import com.mobiledger.backend.entity.Transaction;
import com.mobiledger.backend.entity.TransactionType;
import com.mobiledger.backend.entity.User;
import com.mobiledger.backend.repository.AccountRepository;
import com.mobiledger.backend.repository.TransactionRepository;
import com.mobiledger.backend.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // Create an income or expense transaction
    @Transactional
    public Transaction createTransaction(CreateTransactionRequest request) {

        // Get authenticated user ID
        Long userId = getAuthenticatedUserId();

        // Retrieve authenticated user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        // Retrieve account and verify ownership
        Account account = accountRepository
                .findByIdAndUser_Id(request.accountId(), userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Account not found"));

        // Transfers will be implemented separately
        if (request.type() == TransactionType.TRANSFER) {
            throw new IllegalArgumentException(
                    "Transfers are not supported yet");
        }

        // Process income
        if (request.type() == TransactionType.INCOME) {

            account.increaseBalance(request.amount());

        }
        // Process expense
        else if (request.type() == TransactionType.EXPENSE) {

            account.decreaseBalance(request.amount());

        }
        else {
            throw new IllegalArgumentException(
                    "Invalid transaction type");
        }

        // Create transaction entity
        Transaction transaction = new Transaction(
                user,
                account,
                null,
                request.categoryId(),
                request.amount(),
                request.type(),
                request.description(),
                request.transactionDate()
        );

        // Save transaction
        return transactionRepository.save(transaction);
    }

    // Retrieve all transactions belonging to the authenticated user
    @Transactional(readOnly = true)
    public List<Transaction> getMyTransactions() {

        Long userId = getAuthenticatedUserId();

        return transactionRepository
                .findByUser_IdOrderByTransactionDateDesc(userId);
    }

    // Retrieve one transaction belonging to the authenticated user
    @Transactional(readOnly = true)
    public Transaction getMyTransaction(Long transactionId) {

        Long userId = getAuthenticatedUserId();

        return transactionRepository
                .findByIdAndUser_Id(transactionId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Transaction not found"));
    }

    // Get authenticated user ID from Spring Security
    private Long getAuthenticatedUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof Long)) {

            throw new IllegalStateException(
                    "An authenticated user is required");
        }

        return (Long) authentication.getPrincipal();
    }
}
