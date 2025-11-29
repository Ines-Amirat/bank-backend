package org.bank.api.mapper;


import org.bank.api.dto.*;
import org.bank.entities.BankAccount;

public class BankAccountMapper {
    public static BankAccount toEntity(BankAccountRequest r){
        var e = new BankAccount();
        e.setName(r.name());
        e.setBankName(r.bankName());
        e.setType(r.type());
        e.setCurrency(r.currency());
        e.setMaskedNumber(r.maskedNumber());
        e.setIban(r.iban());
        if (r.initialBalance()!=null) e.setBalance(r.initialBalance());
        return e;
    }
    public static BankAccountResponse toDto(BankAccount e){
        return new BankAccountResponse(
                e.getId(),
                e.getUser().getId(),
                e.getName(),
                e.getBankName(),
                e.getType(),
                e.getCurrency(),
                e.getMaskedNumber(),
                e.getIban(),
                e.getBalance(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

}
