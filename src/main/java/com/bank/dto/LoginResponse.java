package com.bank.dto;

public class LoginResponse {
    
    private String token;
    private String cardNumber;
    private String message;
    
    public LoginResponse() {}
    
    public LoginResponse(String token, String cardNumber, String message) {
        this.token = token;
        this.cardNumber = cardNumber;
        this.message = message;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}



