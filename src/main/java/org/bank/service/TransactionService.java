package org.bank.service;

import org.bank.api.dto.TransactionRequest;
import org.bank.api.mapper.TransactionMapper;
import org.bank.entities.Transaction;
import org.bank.repository.BankAccountRepository;
import org.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final TransactionRepository txRepo;
    private final BankAccountRepository accRepo;

    public TransactionService(TransactionRepository t, BankAccountRepository a){
        txRepo = t;
        accRepo = a;
    }

    @Transactional
    public Transaction create(TransactionRequest r){
        var acc = accRepo.findById(r.accountId()).orElseThrow();
        var tx = TransactionMapper.toEntity(r, acc);
        return txRepo.save(tx);
    }
}
