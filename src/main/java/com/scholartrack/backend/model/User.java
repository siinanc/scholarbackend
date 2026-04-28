package com.scholartrack.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private String caste;
    private String schoolName;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    private Double cgpa;
    private String bankAccountNumber;
    
    @Column(columnDefinition = "LONGTEXT")
    private String idProofBase64;

    private String otp;
    private java.time.LocalDateTime otpExpiry;

    public User() {}

    public User(Long id, String name, String email, String password, String role, String caste, String schoolName, String address, Double cgpa, String bankAccountNumber, String idProofBase64) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.caste = caste;
        this.schoolName = schoolName;
        this.address = address;
        this.cgpa = cgpa;
        this.bankAccountNumber = bankAccountNumber;
        this.idProofBase64 = idProofBase64;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getCaste() { return caste; }
    public void setCaste(String caste) { this.caste = caste; }
    
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }
    
    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }
    
    public String getIdProofBase64() { return idProofBase64; }
    public void setIdProofBase64(String idProofBase64) { this.idProofBase64 = idProofBase64; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public java.time.LocalDateTime getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(java.time.LocalDateTime otpExpiry) { this.otpExpiry = otpExpiry; }
}
