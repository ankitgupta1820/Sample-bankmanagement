package com.bank.controller;

import com.bank.dto.LoginRequest;
import com.bank.dto.LoginResponse;
import com.bank.dto.SignupRequest;
import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.service.AuthService;
import com.bank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Optional<Account> accountOpt = authService.getAccountByCardNumber(loginRequest.getCardNumber());
            
            if (accountOpt.isPresent()) {
                Account account = accountOpt.get();
                
                // Compare encrypted PIN with plain text PIN using passwordEncoder
                if (passwordEncoder.matches(loginRequest.getPin(), account.getPin())) {
                    String token = jwtUtil.generateToken(loginRequest.getCardNumber());
                    
                    LoginResponse response = new LoginResponse();
                    response.setToken(token);
                    response.setCardNumber(loginRequest.getCardNumber());
                    response.setMessage("Login successful");
                    
                    return ResponseEntity.ok(response);
                }
            }
            
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid card number or PIN");
            return ResponseEntity.badRequest().body(error);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            User user = new User();
            user.setFormNumber(signupRequest.getFormNumber());
            user.setName(signupRequest.getName());
            user.setFathersName(signupRequest.getFathersName());
            user.setDateOfBirth(signupRequest.getDateOfBirth());
            user.setGender(signupRequest.getGender());
            user.setEmail(signupRequest.getEmail());
            user.setMaritalStatus(signupRequest.getMaritalStatus());
            user.setAddress(signupRequest.getAddress());
            user.setCity(signupRequest.getCity());
            user.setState(signupRequest.getState());
            user.setPinCode(signupRequest.getPinCode());
            user.setReligion(signupRequest.getReligion());
            user.setCategory(signupRequest.getCategory());
            user.setIncome(signupRequest.getIncome());
            user.setEducation(signupRequest.getEducation());
            user.setOccupation(signupRequest.getOccupation());
            user.setPanNumber(signupRequest.getPanNumber());
            user.setAadharNumber(signupRequest.getAadharNumber());
            user.setSeniorCitizen(signupRequest.getSeniorCitizen());
            user.setExistingAccount(signupRequest.getExistingAccount());
            
            User savedUser = authService.saveUser(user);
            
            // Create account with generated card number
            String cardNumber = generateCardNumber();
            Account account = authService.createAccount(cardNumber, signupRequest.getPin(), savedUser.getFormNumber());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Account created successfully");
            response.put("cardNumber", cardNumber);
            response.put("formNumber", savedUser.getFormNumber());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Signup failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/change-pin")
    public ResponseEntity<?> changePin(@RequestHeader("Authorization") String authHeader,
                                     @RequestParam String oldPin,
                                     @RequestParam String newPin) {
        try {
            String token = authHeader.substring(7);
            String cardNumber = jwtUtil.getUsernameFromToken(token);
            
            boolean success = authService.changePin(cardNumber, oldPin, newPin);
            
            if (success) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "PIN changed successfully");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid old PIN");
                return ResponseEntity.badRequest().body(error);
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "PIN change failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    private String generateCardNumber() {
        // Generate a 16-digit card number
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append((int) (Math.random() * 10));
        }
        return cardNumber.toString();
    }
}

