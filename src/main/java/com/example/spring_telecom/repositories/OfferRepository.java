package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findBySrvceId(Long srvceId);

}