package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.TelecomUser; // Add this import
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelecomUserRepository extends JpaRepository<TelecomUser, Long> {
}