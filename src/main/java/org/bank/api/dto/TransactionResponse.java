package org.bank.api.dto;

import org.bank.entities.Direction;
import org.bank.entities.TransactionStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id, UUID accountId, Direction direction, BigDecimal amount,
        TransactionStatus status, String channel, String category,
        String description, OffsetDateTime occurredAt
) {}
