package com.scholartrack.backend.repository;

import com.scholartrack.backend.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByEmail(String email);
    List<Application> findByScholarship(String scholarship);
}
