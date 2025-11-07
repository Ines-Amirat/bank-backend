package org.bank.service;


import org.bank.api.dto.BankAccountRequest;
import org.bank.api.mapper.BankAccountMapper;
import org.bank.entities.BankAccount;
import org.bank.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.UUID;

@Service
public class BankAccountService {
    private final BankAccountRepository repo;
    public BankAccountService(BankAccountRepository repo){ this.repo = repo; }

    @Transactional
    public BankAccount create(BankAccountRequest req){
        var e = BankAccountMapper.toEntity(req);
        return repo.save(e);
    }

    public Optional<BankAccount> get(UUID id){ return repo.findById(id); }

    public List<BankAccount> listByUser(UUID userId){
        return repo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public Optional<BankAccount> update(UUID id, UUID userId, BankAccountRequest req){
        return repo.findById(id).filter(a -> a.getUserId().equals(userId)).map(a -> {
            a.setName(req.name());
            a.setBankName(req.bankName());
            a.setType(req.type());
            a.setCurrency(req.currency());
            a.setMaskedNumber(req.maskedNumber());
            a.setIban(req.iban());
            // balance non modifié ici (géré par transferts)
            return a;
        });
    }

    @Transactional
    public boolean delete(UUID id, UUID userId){
        if (!repo.existsByIdAndUserId(id, userId)) return false;
        repo.deleteById(id);
        return true;
    }
}
