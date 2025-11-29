package org.bank.service;


import org.bank.api.dto.BankAccountRequest;
import org.bank.api.mapper.BankAccountMapper;
import org.bank.entities.BankAccount;
import org.bank.repository.BankAccountRepository;
import org.bank.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.UUID;

@Service
public class BankAccountService {

    private final BankAccountRepository repo;
    private final UserRepository userRepo;

    public BankAccountService(BankAccountRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Transactional
    public BankAccount create(BankAccountRequest req){
        var e = BankAccountMapper.toEntity(req);

        var user = userRepo.findById(req.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        e.setUser(user);

        return repo.save(e);
    }

    public Optional<BankAccount> get(UUID id){
        return repo.findById(id);
    }

    public List<BankAccount> listByUser(UUID userId){
        return repo.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public Optional<BankAccount> update(UUID id, UUID userId, BankAccountRequest req){
        return repo.findById(id)
                .filter(a -> a.getUser().getId().equals(userId))
                .map(a -> {
                    a.setName(req.name());
                    a.setBankName(req.bankName());
                    a.setType(req.type());
                    a.setCurrency(req.currency());
                    a.setMaskedNumber(req.maskedNumber());
                    a.setIban(req.iban());
                    return a;
                });
    }

    @Transactional
    public boolean delete(UUID id, UUID userId){
        if (!repo.existsByIdAndUser_Id(id, userId))
            return false;

        repo.deleteById(id);
        return true;
    }
}
