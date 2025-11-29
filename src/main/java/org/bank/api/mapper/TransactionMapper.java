package org.bank.api.mapper;

import org.bank.api.dto.TransactionRequest;
import org.bank.api.dto.TransactionResponse;
import org.bank.entities.BankAccount;
import org.bank.entities.Direction;
import org.bank.entities.Transaction;

public class TransactionMapper {
    public static Transaction toEntity(TransactionRequest r, BankAccount account) {
        var t = new Transaction();
        t.setAccount(account);
        t.setAmount(r.amount());
        t.setDirection(Direction.valueOf(r.direction()));
        t.setChannel(r.channel());
        t.setCategory(r.category());
        t.setDescription(r.description());
        return t;
    }



    public static TransactionResponse toDto(Transaction t) {


        return new TransactionResponse(
                t.getId(),
                t.getAccount().getId(),
                t.getDirection(),
                t.getAmount(),
                t.getStatus(),        // <-- AJOUT ICI
                t.getChannel(),
                t.getCategory(),
                t.getDescription(),
                t.getOccurredAt()
        );
    }


}
