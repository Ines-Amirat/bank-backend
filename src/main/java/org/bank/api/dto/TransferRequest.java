package org.bank.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull UUID fromAccountId,
        @NotNull UUID toAccountId,
        @NotNull @Min(1) BigDecimal amount,
        String note
) {}
