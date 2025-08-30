package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Installation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallationRepository extends JpaRepository<Installation, Long> {
}