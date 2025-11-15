package com.bank.repository;

import com.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByFormNumber(String formNumber);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByFormNumber(String formNumber);
    
    boolean existsByEmail(String email);
    
    boolean existsByPanNumber(String panNumber);
    
    boolean existsByAadharNumber(String aadharNumber);
}



