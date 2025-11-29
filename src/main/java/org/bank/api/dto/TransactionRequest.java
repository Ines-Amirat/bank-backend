package org.bank.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
        UUID accountId,
        BigDecimal amount,
        String direction,
        String channel,
        String category,
        String description
) {}


