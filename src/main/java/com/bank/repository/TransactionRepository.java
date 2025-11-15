package com.bank.repository;

import com.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByPinOrderByDateDesc(String pin);
    
    @Query("SELECT t FROM Transaction t WHERE t.pin = :pin ORDER BY t.date DESC")
    List<Transaction> findRecentTransactions(@Param("pin") String pin, org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE t.pin = :pin AND t.date BETWEEN :startDate AND :endDate ORDER BY t.date DESC")
    List<Transaction> findTransactionsByDateRange(@Param("pin") String pin, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE -t.amount END) FROM Transaction t WHERE t.pin = :pin")
    java.math.BigDecimal calculateBalance(@Param("pin") String pin);
}



