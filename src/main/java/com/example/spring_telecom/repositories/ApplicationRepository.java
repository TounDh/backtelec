package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}