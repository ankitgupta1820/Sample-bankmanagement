package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public boolean authenticate(String cardNumber, String pin) {
        Optional<Account> account = accountRepository.findByCardNumberAndPin(cardNumber, pin);
        return account.isPresent();
    }
    
    public Optional<Account> getAccountByCardNumber(String cardNumber) {
        return accountRepository.findByCardNumber(cardNumber);
    }
    
    public Optional<User> getUserByFormNumber(String formNumber) {
        return userRepository.findByFormNumber(formNumber);
    }
    
    public Account createAccount(String cardNumber, String pin, String formNumber) {
        if (accountRepository.existsByCardNumber(cardNumber)) {
            throw new RuntimeException("Card number already exists");
        }
        
        if (accountRepository.existsByFormNumber(formNumber)) {
            throw new RuntimeException("Account already exists for this form number");
        }
        
        Account account = new Account();
        account.setCardNumber(cardNumber);
        account.setPin(passwordEncoder.encode(pin));
        account.setFormNumber(formNumber);
        
        return accountRepository.save(account);
    }
    
    public User saveUser(User user) {
        if (userRepository.existsByFormNumber(user.getFormNumber())) {
            throw new RuntimeException("User with this form number already exists");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        if (userRepository.existsByPanNumber(user.getPanNumber())) {
            throw new RuntimeException("PAN number already exists");
        }
        
        if (userRepository.existsByAadharNumber(user.getAadharNumber())) {
            throw new RuntimeException("Aadhar number already exists");
        }
        
        return userRepository.save(user);
    }
    
    public boolean changePin(String cardNumber, String oldPin, String newPin) {
        // Get account by card number first
        Optional<Account> accountOpt = accountRepository.findByCardNumber(cardNumber);
        
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            
            // Verify old PIN using passwordEncoder
            if (passwordEncoder.matches(oldPin, account.getPin())) {
                // Update to new encrypted PIN
                account.setPin(passwordEncoder.encode(newPin));
                accountRepository.save(account);
                return true;
            }
        }
        return false;
    }
}



