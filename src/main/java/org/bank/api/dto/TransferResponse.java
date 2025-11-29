package org.bank.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferResponse(
        UUID fromAccountId,
        UUID toAccountId,
        BigDecimal amount,
        UUID debitTransactionId,
        UUID creditTransactionId,
        BigDecimal fromNewBalance,
        BigDecimal toNewBalance
) {}
