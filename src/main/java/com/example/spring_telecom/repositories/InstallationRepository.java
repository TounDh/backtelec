package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Installation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstallationRepository extends JpaRepository<Installation, Long> {
    List<Installation> findByApplicationIdIn(List<Long> applicationIds);

}