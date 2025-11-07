package org.bank.api.dto;

import org.bank.entities.AccountType;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BankAccountResponse(
        UUID id, UUID userId, String name, String bankName, AccountType type,
        String currency, String maskedNumber, String iban,
        BigDecimal balance, OffsetDateTime createdAt, OffsetDateTime updatedAt
) {}
