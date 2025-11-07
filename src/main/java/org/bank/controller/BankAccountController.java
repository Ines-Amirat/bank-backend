package org.bank.controller;


import org.bank.api.dto.*;
import org.bank.api.mapper.BankAccountMapper;
import org.bank.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
public class BankAccountController {
    private final BankAccountService svc;
    public BankAccountController(BankAccountService svc){ this.svc = svc; }

    @PostMapping
    public ResponseEntity<BankAccountResponse> create(@Valid @RequestBody BankAccountRequest req){
        var saved = svc.create(req);
        return ResponseEntity.created(URI.create("/api/accounts/"+saved.getId()))
                .body(BankAccountMapper.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<?> listByUser(@RequestParam UUID userId){
        var items = svc.listByUser(userId).stream().map(BankAccountMapper::toDto).toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> get(@PathVariable UUID id){
        return svc.get(id).map(BankAccountMapper::toDto)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountResponse> update(@PathVariable UUID id,
                                                      @Valid @RequestBody BankAccountRequest req){
        var out = svc.update(id, req.userId(), req);
        return out.map(BankAccountMapper::toDto)
                .map(ResponseEntity::ok).orElse(ResponseEntity.status(404).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestParam UUID userId){
        return svc.delete(id, userId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
