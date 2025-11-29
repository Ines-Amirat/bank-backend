package org.bank.repository;

import org.bank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByAccount_IdOrderByOccurredAtDesc(UUID accountId);
}

