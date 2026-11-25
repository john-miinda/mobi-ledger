
package com.mobiledger.backend.dto;

import com.mobiledger.backend.entity.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateTransactionRequest(

        @NotNull
        Long accountId,

        Long destinationAccountId,

        Long categoryId,

        @NotNull
        @DecimalMin(value = "0.0001")
        @Digits(integer = 15, fraction = 4)
        BigDecimal amount,

        @NotNull
        TransactionType type,

        @Size(max = 255)
        String description,

        @NotNull
        Instant transactionDate

) {}
