package com.bank.repository;

import com.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByCardNumber(String cardNumber);
    
    Optional<Account> findByFormNumber(String formNumber);
    
    Optional<Account> findByCardNumberAndPin(String cardNumber, String pin);
    
    boolean existsByCardNumber(String cardNumber);
    
    boolean existsByFormNumber(String formNumber);
    
    Optional<Account> findByPin(String pin);
}
