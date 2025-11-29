// src/main/java/org/bank/service/TransferService.java
package org.bank.service;

import org.bank.api.dto.TransferRequest;
import org.bank.api.dto.TransferResponse;
import org.bank.entities.BankAccount;
import org.bank.entities.Transaction;
import org.bank.entities.Direction;
import org.bank.entities.TransactionStatus;
import org.bank.repository.BankAccountRepository;
import org.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final BankAccountRepository accounts;
    private final TransactionRepository txs;

    public TransferService(BankAccountRepository accounts, TransactionRepository txs) {
        this.accounts = accounts;
        this.txs = txs;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest req) {
        if (req.fromAccountId().equals(req.toAccountId())) {
            throw new IllegalArgumentException("from and to must be different accounts");
        }

        BankAccount from = accounts.findById(req.fromAccountId())
                .orElseThrow(() -> new IllegalArgumentException("From account not found"));
        BankAccount to = accounts.findById(req.toAccountId())
                .orElseThrow(() -> new IllegalArgumentException("To account not found"));

        BigDecimal amount = req.amount();
        if (amount.signum() <= 0) throw new IllegalArgumentException("Amount must be > 0");

        if (!from.getCurrency().equals(to.getCurrency()))
            throw new IllegalArgumentException("Currency mismatch");

        if (from.getBalance().compareTo(amount) < 0)
            throw new IllegalStateException("Insufficient funds");

        // Update balances
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accounts.save(from);
        accounts.save(to);

        // Create two transactions (DEBIT on from, CREDIT on to)
        Transaction debit = new Transaction();
        debit.setAccount(from);
        debit.setAmount(amount);
        debit.setDirection(Direction.DEBIT);
        debit.setStatus(TransactionStatus.SUCCESS);
        debit.setChannel("TRANSFER");
        debit.setCategory("TRANSFER");
        debit.setDescription(req.note() != null ? req.note() : "Transfer out");

        Transaction credit = new Transaction();
        credit.setAccount(to);
        credit.setAmount(amount);
        credit.setDirection(Direction.CREDIT);
        credit.setStatus(TransactionStatus.SUCCESS);
        credit.setChannel("TRANSFER");
        credit.setCategory("TRANSFER");
        credit.setDescription(req.note() != null ? req.note() : "Transfer in");

        debit = txs.save(debit);
        credit = txs.save(credit);

        return new TransferResponse(
                from.getId(), to.getId(), amount,
                debit.getId(), credit.getId(),
                from.getBalance(), to.getBalance()
        );
    }
}
