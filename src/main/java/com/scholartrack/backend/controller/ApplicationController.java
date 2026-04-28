package com.scholartrack.backend.controller;

import com.scholartrack.backend.model.Application;
import com.scholartrack.backend.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")

public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private com.scholartrack.backend.service.EmailService emailService;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @GetMapping("/student")
    public List<Application> getApplicationsByStudent(@RequestParam String email) {
        return applicationRepository.findByEmail(email);
    }

    @PostMapping
    public Application submitApplication(@RequestBody Application application) {
        return applicationRepository.save(application);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> updates) {
        String newStatus = updates.get("status");
        return applicationRepository.findById(id)
                .map(application -> {
                    application.setStatus(newStatus);
                    Application saved = applicationRepository.save(application);
                    
                    // Notify student
                    notifyStudentOfStatusChange(saved);
                    
                    return ResponseEntity.ok(saved);
                }).orElse(ResponseEntity.notFound().build());
    }

    private void notifyStudentOfStatusChange(Application app) {
        String subject = "Update on your Scholarship Application: " + app.getScholarship();
        String message = "Hello " + app.getStudentName() + ",\n\n" +
                "The status of your application for '" + app.getScholarship() + "' has been updated to: " + app.getStatus() + ".\n\n" +
                (app.getStatus().equalsIgnoreCase("Approved") ? 
                    "Congratulations! Your application has been approved. Further details will be sent to your bank account." :
                    "You can log in to your dashboard to see more details.") + "\n\n" +
                "Best regards,\n" +
                "The ScholarTrack Team";
        
        emailService.sendEmail(app.getEmail(), subject, message);
    }
}
