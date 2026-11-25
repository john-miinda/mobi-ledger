
package com.mobiledger.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(

        @NotBlank(message = "Account name is required")
        String name,

        @NotBlank(message = "Account type is required")
        String type,

        @NotNull(message = "Initial balance is required")
        @DecimalMin(value = "0.00", message = "Initial balance cannot be negative")
        BigDecimal balance,

        @NotBlank(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must contain 3 characters")
        String currency

) {
}


 