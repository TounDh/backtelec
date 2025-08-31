package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Application;
import com.example.spring_telecom.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getApplicationsByUserId(Long userId) {
        return applicationRepository.findByUserIdWithDetails(userId);
    }
}