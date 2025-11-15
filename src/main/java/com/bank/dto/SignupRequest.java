package com.bank.dto;

import com.bank.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class SignupRequest {
    
    @NotBlank(message = "Form number is required")
    private String formNumber;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Father's name is required")
    @Size(min = 2, max = 50, message = "Father's name must be between 2 and 50 characters")
    private String fathersName;
    
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
    
    @NotNull(message = "Gender is required")
    private User.Gender gender;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    
    @NotNull(message = "Marital status is required")
    private User.MaritalStatus maritalStatus;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotBlank(message = "Pin code is required")
    @Size(min = 6, max = 6, message = "Pin code must be 6 digits")
    private String pinCode;
    
    @NotBlank(message = "Religion is required")
    private String religion;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotBlank(message = "Income is required")
    private String income;
    
    @NotBlank(message = "Education is required")
    private String education;
    
    @NotBlank(message = "Occupation is required")
    private String occupation;
    
    @NotBlank(message = "PAN number is required")
    private String panNumber;
    
    @NotBlank(message = "Aadhar number is required")
    private String aadharNumber;
    
    @NotBlank(message = "Senior citizen status is required")
    private String seniorCitizen;
    
    @NotBlank(message = "Existing account status is required")
    private String existingAccount;
    
    @NotBlank(message = "PIN is required")
    private String pin;
    
    // Constructors
    public SignupRequest() {}
    
    // Getters and Setters
    public String getFormNumber() {
        return formNumber;
    }
    
    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFathersName() {
        return fathersName;
    }
    
    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public User.Gender getGender() {
        return gender;
    }
    
    public void setGender(User.Gender gender) {
        this.gender = gender;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public User.MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }
    
    public void setMaritalStatus(User.MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getPinCode() {
        return pinCode;
    }
    
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    
    public String getReligion() {
        return religion;
    }
    
    public void setReligion(String religion) {
        this.religion = religion;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getIncome() {
        return income;
    }
    
    public void setIncome(String income) {
        this.income = income;
    }
    
    public String getEducation() {
        return education;
    }
    
    public void setEducation(String education) {
        this.education = education;
    }
    
    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String getPanNumber() {
        return panNumber;
    }
    
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
    
    public String getAadharNumber() {
        return aadharNumber;
    }
    
    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
    
    public String getSeniorCitizen() {
        return seniorCitizen;
    }
    
    public void setSeniorCitizen(String seniorCitizen) {
        this.seniorCitizen = seniorCitizen;
    }
    
    public String getExistingAccount() {
        return existingAccount;
    }
    
    public void setExistingAccount(String existingAccount) {
        this.existingAccount = existingAccount;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
}



