package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Srvce;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SrvceRepository extends JpaRepository<Srvce, Long> {
    @EntityGraph(attributePaths = {"offers"})
    List<Srvce> findAll();

    @EntityGraph(attributePaths = {"offers"})
    Optional<Srvce> findById(Long id);

    @Query("SELECT s FROM Srvce s LEFT JOIN FETCH s.offers")
    List<Srvce> findAllWithOffers();

    @Query("SELECT s FROM Srvce s LEFT JOIN FETCH s.offers WHERE s.id = :id")
    Optional<Srvce> findByIdWithOffers(Long id);
}