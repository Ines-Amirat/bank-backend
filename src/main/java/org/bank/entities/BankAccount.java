package org.bank.entities;


import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id @GeneratedValue
    private UUID id;

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Getter
    @Column(name="user_id", nullable=false)
    private UUID userId;

    @Getter
    @Column(nullable=false, length=120)
    private String name;

    @Getter
    @Column(name="bank_name", nullable=false, length=120)
    private String bankName;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=30)
    private AccountType type;

    @Getter
    @Column(nullable=false, length=10)
    private String currency = "EUR";

    @Getter
    @Column(name="masked_number", length=32)
    private String maskedNumber;

    @Getter
    @Column(length=34)
    private String iban;

    @Getter
    @Column(nullable=false, precision=18, scale=2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Getter
    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Getter
    @Column(name="updated_at", nullable=false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @PreUpdate
    void onUpdate(){ this.updatedAt = OffsetDateTime.now(); }

    // getters/setters …
}
