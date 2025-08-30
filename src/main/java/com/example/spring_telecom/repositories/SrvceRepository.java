package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Srvce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SrvceRepository extends JpaRepository<Srvce, Long> {
}