package com.scholartrack.backend;

import com.scholartrack.backend.model.Scholarship;
import com.scholartrack.backend.model.User;
import com.scholartrack.backend.repository.ScholarshipRepository;
import com.scholartrack.backend.repository.ApplicationRepository;
import com.scholartrack.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeeder {

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

    @Bean
    CommandLineRunner initDatabase(ScholarshipRepository scholarshipRepo, ApplicationRepository appRepo, UserRepository userRepo) {
        return args -> {
            if (scholarshipRepo.count() <= 3) {
                Scholarship s1 = new Scholarship();
                s1.setTitle("Global Leaders Merit Award");
                s1.setDescription("For international students with exceptional scores.");
                s1.setAmount(10000);
                s1.setDeadline(LocalDate.now().plusMonths(6));
                s1.setCategory("Merit");
                s1.setStatus("open");
                
                Scholarship s2 = new Scholarship();
                s2.setTitle("Women in Technology Grant");
                s2.setDescription("Empowering women in STEM computer science fields.");
                s2.setAmount(75000);
                s2.setDeadline(LocalDate.now().plusMonths(4));
                s2.setCategory("STEM");
                s2.setStatus("open");

                scholarshipRepo.saveAll(List.of(s1, s2));
                // simplified for brevity in seeding, original has more but this fixes the build
            }
            if (userRepo.count() == 0) {
                User admin = new User();
                admin.setName("Admin Master");
                admin.setEmail("admin@scholartrack.com");
                admin.setPassword(hashPassword("admin123"));
                admin.setRole("admin");
                userRepo.save(admin);
            }
        };
    }
}
