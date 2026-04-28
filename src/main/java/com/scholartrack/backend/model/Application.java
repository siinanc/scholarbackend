package com.scholartrack.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String scholarship;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate submittedDate;

    @Column(nullable = false)
    private String status = "pending";

    @Column(name = "caste")
    private String caste;
    private String schoolName;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    private Double cgpa;
    private String bankAccountNumber;
    
    @Column(columnDefinition = "LONGTEXT")
    private String idProofBase64;

    public Application() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getScholarship() { return scholarship; }
    public void setScholarship(String scholarship) { this.scholarship = scholarship; }
    
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(LocalDate submittedDate) { this.submittedDate = submittedDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
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
}
