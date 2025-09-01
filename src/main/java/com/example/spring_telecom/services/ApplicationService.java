package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Application;
import com.example.spring_telecom.entities.Payment;
import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.repositories.ApplicationRepository;
import com.example.spring_telecom.repositories.PaymentRepository;
import com.example.spring_telecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

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

    @Transactional
    public void updateApplicationStatus(Long id, String status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        String oldStatus = application.getStatus();

        // Update the status
        application.setStatus(status);
        applicationRepository.save(application);

        // If status changed to APPROVED, create a payment
        if ("APPROVED".equals(status) && !"APPROVED".equals(oldStatus)) {
            createPaymentForApprovedApplication(application);
        }
    }

    private void createPaymentForApprovedApplication(Application application) {
        Payment payment = new Payment();
        payment.setApplication(application);
        payment.setTotal(application.getSrvce().getInstallationFees());
        payment.setDeadline(LocalDate.now().plusDays(7)); // 30 days from now
        payment.setStatus("PENDING"); // Initial payment status

        paymentRepository.save(payment);
    }


    public Application createApplication(Application application) {
        // Set default values if needed
        if (application.getApplicationDate() == null) {
            application.setApplicationDate(LocalDateTime.now());
        }
        if (application.getStatus() == null) {
            application.setStatus("PENDING");
        }

        return applicationRepository.save(application);
    }
}