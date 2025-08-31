package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a " +
            "LEFT JOIN FETCH a.srvce " +
            "LEFT JOIN FETCH a.offer " +
            "WHERE a.user.id = :userId")
    List<Application> findByUserIdWithDetails(@Param("userId") Long userId);
}