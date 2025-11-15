package com.bank.security;

import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {
        Optional<Account> accountOpt = accountRepository.findByCardNumber(cardNumber);
        
        if (accountOpt.isEmpty()) {
            throw new UsernameNotFoundException("Account not found with card number: " + cardNumber);
        }
        
        Account account = accountOpt.get();
        
        return new User(
                account.getCardNumber(),
                account.getPin(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}



