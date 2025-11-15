package com.bank.controller;

import com.bank.entity.Transaction;
import com.bank.service.TransactionService;
import com.bank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String cardNumber = jwtUtil.getUsernameFromToken(token);
            
            // Get PIN from card number (you might need to modify this based on your logic)
            String pin = cardNumber; // Simplified for demo
            
            BigDecimal balance = transactionService.getCurrentBalance(pin);
            
            Map<String, Object> response = new HashMap<>();
            response.put("balance", balance);
            response.put("cardNumber", cardNumber);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get balance: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader("Authorization") String authHeader,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam(required = false) String description) {
        try {
            String token = authHeader.substring(7);
            String cardNumber = jwtUtil.getUsernameFromToken(token);
            String pin = cardNumber; // Simplified for demo
            
            Transaction transaction = transactionService.deposit(pin, amount, description);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Deposit successful");
            response.put("amount", amount);
            response.put("transactionId", transaction.getId());
            response.put("balance", transaction.getBalanceAfter());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Deposit failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam BigDecimal amount,
                                    @RequestParam(required = false) String description) {
        try {
            String token = authHeader.substring(7);
            String cardNumber = jwtUtil.getUsernameFromToken(token);
            String pin = cardNumber; // Simplified for demo
            
            Transaction transaction = transactionService.withdraw(pin, amount, description);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Withdrawal successful");
            response.put("amount", amount);
            response.put("transactionId", transaction.getId());
            response.put("balance", transaction.getBalanceAfter());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Withdrawal failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String cardNumber = jwtUtil.getUsernameFromToken(token);
            String pin = cardNumber; // Simplified for demo
            
            List<Transaction> transactions = transactionService.getTransactionHistory(pin);
            
            Map<String, Object> response = new HashMap<>();
            response.put("transactions", transactions);
            response.put("count", transactions.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get transaction history: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/mini-statement")
    public ResponseEntity<?> getMiniStatement(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String cardNumber = jwtUtil.getUsernameFromToken(token);
            String pin = cardNumber; // Simplified for demo
            
            List<Transaction> transactions = transactionService.getMiniStatement(pin);
            
            Map<String, Object> response = new HashMap<>();
            response.put("transactions", transactions);
            response.put("count", transactions.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to get mini statement: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/fast-cash")
    public ResponseEntity<?> fastCash(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam BigDecimal amount) {
        try {
            String token = authHeader.substring(7);
            String cardNumber = jwtUtil.getUsernameFromToken(token);
            String pin = cardNumber; // Simplified for demo
            
            Transaction transaction = transactionService.withdraw(pin, amount, "Fast Cash");
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Fast cash withdrawal successful");
            response.put("amount", amount);
            response.put("transactionId", transaction.getId());
            response.put("balance", transaction.getBalanceAfter());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Fast cash withdrawal failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}



