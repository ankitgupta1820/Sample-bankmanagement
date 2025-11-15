package com.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "login")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "cardnumber", unique = true, nullable = false)
    @NotBlank(message = "Card number is required")
    private String cardNumber;
    
    @Column(name = "pin", nullable = false)
    @NotBlank(message = "PIN is required")
    private String pin;
    
    @Column(name = "formno", unique = true, nullable = false)
    @NotBlank(message = "Form number is required")
    private String formNumber;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formno", referencedColumnName = "form_number", insertable = false, updatable = false)
    private User user;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Account() {}
    
    public Account(String cardNumber, String pin, String formNumber) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.formNumber = formNumber;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public String getFormNumber() {
        return formNumber;
    }
    
    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}



