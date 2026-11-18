
package com.mobiledger.backend.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponse(
        Long id,
        String name,
        String type,
        BigDecimal balance,
        String currency,
        Instant createdAt
) {
}
