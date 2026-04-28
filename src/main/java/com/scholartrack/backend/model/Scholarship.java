package com.scholartrack.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "scholarships")
public class Scholarship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String status = "open";

    public Scholarship() {}

    public Scholarship(Long id, String title, String description, Integer amount, LocalDate deadline, String category, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.deadline = deadline;
        this.category = category;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
