package org.bank.repository;


import org.bank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;




public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    List<BankAccount> findByUser_IdOrderByCreatedAtDesc(UUID userId);
    boolean existsByIdAndUser_Id(UUID id, UUID userId);
}
