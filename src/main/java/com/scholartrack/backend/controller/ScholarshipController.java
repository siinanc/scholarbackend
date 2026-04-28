package com.scholartrack.backend.controller;

import com.scholartrack.backend.model.Scholarship;
import com.scholartrack.backend.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarships")

public class ScholarshipController {

    @Autowired
    private ScholarshipRepository scholarshipRepository;

    @GetMapping
    public List<Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }

    @PostMapping
    public Scholarship createScholarship(@RequestBody Scholarship scholarship) {
        return scholarshipRepository.save(scholarship);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scholarship> updateScholarship(@PathVariable Long id, @RequestBody Scholarship scholarshipDetails) {
        return scholarshipRepository.findById(id)
                .map(scholarship -> {
                    scholarship.setTitle(scholarshipDetails.getTitle());
                    scholarship.setDescription(scholarshipDetails.getDescription());
                    scholarship.setAmount(scholarshipDetails.getAmount());
                    scholarship.setDeadline(scholarshipDetails.getDeadline());
                    scholarship.setCategory(scholarshipDetails.getCategory());
                    scholarship.setStatus(scholarshipDetails.getStatus());
                    return ResponseEntity.ok(scholarshipRepository.save(scholarship));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScholarship(@PathVariable Long id) {
        return scholarshipRepository.findById(id)
                .map(scholarship -> {
                    scholarshipRepository.delete(scholarship);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
