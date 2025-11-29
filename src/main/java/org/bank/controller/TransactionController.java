package org.bank.controller;

import org.bank.api.dto.TransactionRequest;
import org.bank.api.mapper.TransactionMapper;
import org.bank.repository.BankAccountRepository;
import org.bank.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TransactionController {

    private final BankAccountRepository accRepo;
    private final TransactionRepository repo;
    public TransactionController(BankAccountRepository accRepo, TransactionRepository repo){
        this.accRepo = accRepo;
        this.repo = repo; }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam UUID accountId){
        var items = repo.findByAccount_IdOrderByOccurredAtDesc(accountId)
                .stream().map(TransactionMapper::toDto).toList();
        return ResponseEntity.ok(items);
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionRequest req) {
        var acc = accRepo.findById(req.accountId()).orElseThrow();
        var saved = repo.save(TransactionMapper.toEntity(req, acc));
        return ResponseEntity.ok(TransactionMapper.toDto(saved));
    }

}


