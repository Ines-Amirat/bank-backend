package org.bank.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name="transactions")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOccurredAt(OffsetDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    public UUID getId() {
        return id;
    }

    public BankAccount getAccount() {
        return account;
    }

    public Direction getDirection() {
        return direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getChannel() {
        return channel;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getOccurredAt() {
        return occurredAt;
    }

    @ManyToOne(optional=false) @JoinColumn(name="account_id")
    private BankAccount account;

    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private Direction direction;

    @Column(nullable=false, precision=18, scale=2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private TransactionStatus status = TransactionStatus.SUCCESS;

    @Column(length=64)  private String channel;     // "ONLINE", ...
    @Column(length=64)  private String category;    // "FOOD", ...
    @Column(length=256) private String description;
    @Column(name="occurred_at", nullable=false)
    private OffsetDateTime occurredAt = OffsetDateTime.now();



    // getters/setters
}
