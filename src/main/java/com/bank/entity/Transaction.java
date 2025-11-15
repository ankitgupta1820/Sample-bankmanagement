package com.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "pin", nullable = false)
    @NotBlank(message = "PIN is required")
    private String pin;
    
    @Column(name = "date", nullable = false)
    @NotNull(message = "Transaction date is required")
    private LocalDateTime date;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    
    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "balance_after")
    private BigDecimal balanceAfter;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (date == null) {
            date = LocalDateTime.now();
        }
    }
    
    // Constructors
    public Transaction() {}
    
    public Transaction(String pin, TransactionType type, BigDecimal amount) {
        this.pin = pin;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }
    
    public Transaction(String pin, TransactionType type, BigDecimal amount, String description) {
        this.pin = pin;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }
    
    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Enums
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER, INTEREST, FEE
    }
}



