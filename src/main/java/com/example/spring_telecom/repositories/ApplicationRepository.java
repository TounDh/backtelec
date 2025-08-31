package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // ADD THIS METHOD to get all applications with details
    @Query("SELECT a FROM Application a " +
            "LEFT JOIN FETCH a.srvce " +
            "LEFT JOIN FETCH a.offer " +
            "LEFT JOIN FETCH a.user")
    List<Application> findAllWithDetails();

    @Query("SELECT a FROM Application a " +
            "LEFT JOIN FETCH a.srvce " +
            "LEFT JOIN FETCH a.offer " +
            "LEFT JOIN FETCH a.user " +
            "WHERE a.user.id = :userId")
    List<Application> findByUserIdWithDetails(@Param("userId") Long userId);
}