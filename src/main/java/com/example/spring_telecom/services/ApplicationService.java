package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Application;
import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.repositories.ApplicationRepository;
import com.example.spring_telecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Application> getAllApplications() {
        List<Application> applications = applicationRepository.findAllWithDetails();

        // Manually set user details for each application
        for (Application app : applications) {
            if (app.getUser() == null && app.getUser() != null) {
                Optional<User> user = userRepository.findById(app.getUser().getId());
                user.ifPresent(app::setUser);
            }
        }

        return applications;
    }

    public List<Application> getApplicationsByUserId(Long userId) {
        return applicationRepository.findByUserIdWithDetails(userId);
    }
}