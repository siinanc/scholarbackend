package com.scholartrack.backend.controller;

import com.scholartrack.backend.model.User;
import com.scholartrack.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.scholartrack.backend.service.EmailService emailService;

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already registered!");
        }
        user.setPassword(hashPassword(user.getPassword()));
        User saved = userRepository.save(user);
        System.out.println("✅ NEW REGISTRATION: " + saved.getName() + " (" + saved.getEmail() + ") as " + saved.getRole());
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginReq) {
        Optional<User> userOpt = userRepository.findByEmail(loginReq.getEmail())
                .filter(u -> u.getPassword().equals(hashPassword(loginReq.getPassword())));
        
        if (userOpt.isPresent()) {
            User u = userOpt.get();
            // Generate OTP
            String otp = String.format("%06d", new java.util.Random().nextInt(999999));
            u.setOtp(otp);
            u.setOtpExpiry(java.time.LocalDateTime.now().plusMinutes(5));
            userRepository.save(u);
            
            // Send OTP via Email
            emailService.sendOtpEmail(u.getEmail(), otp);
            
            System.out.println("📨 OTP for " + u.getEmail() + " is: " + otp);
            
            java.util.Map<String, String> response = new java.util.HashMap<>();
            response.put("status", "REQUIRES_OTP");
            response.put("message", "OTP sent to registered email (For demo: Check backend logs)");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Error: Invalid email or password");
        }
    }

    
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody java.util.Map<String, String> req) {
        String email = req.get("email");
        String otp = req.get("otp");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User u = userOpt.get();
            if (u.getOtp() != null && u.getOtp().equals(otp) && 
                u.getOtpExpiry() != null && u.getOtpExpiry().isAfter(java.time.LocalDateTime.now())) {
                
                // Success - Clear OTP and return user
                u.setOtp(null);
                u.setOtpExpiry(null);
                userRepository.save(u);
                
                u.setPassword(null);
                return ResponseEntity.ok(u);
            } else {
                return ResponseEntity.status(401).body("Error: Invalid or expired OTP");
            }
        }
        return ResponseEntity.status(404).body("Error: User not found");
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody User profileData) {
        return userRepository.findById(id).map(user -> {
            user.setCaste(profileData.getCaste());
            user.setSchoolName(profileData.getSchoolName());
            user.setAddress(profileData.getAddress());
            user.setCgpa(profileData.getCgpa());
            user.setBankAccountNumber(profileData.getBankAccountNumber());
            user.setIdProofBase64(profileData.getIdProofBase64());
            User saved = userRepository.save(user);
            saved.setPassword(null);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }
}
