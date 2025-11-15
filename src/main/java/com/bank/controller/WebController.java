package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String home() {
        return "login";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    
    @GetMapping("/transactions")
    public String transactions() {
        return "transactions";
    }
    
    @GetMapping("/balance")
    public String balance() {
        return "balance";
    }
    
    @GetMapping("/deposit")
    public String deposit() {
        return "deposit";
    }
    
    @GetMapping("/withdraw")
    public String withdraw() {
        return "withdraw";
    }
    
    @GetMapping("/ministatement")
    public String miniStatement() {
        return "ministatement";
    }
    
    @GetMapping("/pinchange")
    public String pinChange() {
        return "pinchange";
    }
}
