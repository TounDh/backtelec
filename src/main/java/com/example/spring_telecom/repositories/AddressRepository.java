package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Address;
import com.example.spring_telecom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByUser(User user);
    Optional<Address> findByUserId(Long userId);
    boolean existsByUser(User user);
}