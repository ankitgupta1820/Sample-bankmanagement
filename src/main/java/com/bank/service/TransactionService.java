package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Transaction deposit(String pin, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Transaction transaction = new Transaction(pin, Transaction.TransactionType.DEPOSIT, amount, description);
        BigDecimal currentBalance = getCurrentBalance(pin);
        transaction.setBalanceAfter(currentBalance.add(amount));
        
        return transactionRepository.save(transaction);
    }
    
    public Transaction withdraw(String pin, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        BigDecimal currentBalance = getCurrentBalance(pin);
        if (currentBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        
        Transaction transaction = new Transaction(pin, Transaction.TransactionType.WITHDRAWAL, amount, description);
        transaction.setBalanceAfter(currentBalance.subtract(amount));
        
        return transactionRepository.save(transaction);
    }
    
    public BigDecimal getCurrentBalance(String pin) {
        BigDecimal balance = transactionRepository.calculateBalance(pin);
        return balance != null ? balance : BigDecimal.ZERO;
    }
    
    public List<Transaction> getTransactionHistory(String pin) {
        return transactionRepository.findByPinOrderByDateDesc(pin);
    }
    
    public List<Transaction> getRecentTransactions(String pin, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return transactionRepository.findRecentTransactions(pin, pageable);
    }
    
    public List<Transaction> getMiniStatement(String pin) {
        return getRecentTransactions(pin, 5);
    }
    
    public List<Transaction> getTransactionsByDateRange(String pin, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findTransactionsByDateRange(pin, startDate, endDate);
    }
    
    public Optional<Account> getAccountByPin(String pin) {
        return accountRepository.findByPin(pin);
    }
}



