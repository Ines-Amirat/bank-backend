package org.bank.controller;

import org.bank.api.dto.TransferRequest;
import org.bank.api.dto.TransferResponse;
import org.bank.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
public class TransferController {

    private final TransferService svc;

    public TransferController(TransferService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<TransferResponse> create(@Valid @RequestBody TransferRequest req) {
        return ResponseEntity.ok(svc.transfer(req));
    }
}
