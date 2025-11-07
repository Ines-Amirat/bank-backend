package org.bank.api.dto;


import org.bank.entities.AccountType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountRequest(
        @NotNull UUID userId,
        @NotBlank @Size(max=120) String name,
        @NotBlank @Size(max=120) String bankName,
        @NotNull AccountType type,
        @NotBlank @Size(max=10) String currency,
        @DecimalMin(value="0.00") BigDecimal initialBalance,
        @Size(max=32) String maskedNumber,
        @Size(max=34) String iban
) {}
