package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Installation;
import com.example.spring_telecom.entities.Payment;
import com.example.spring_telecom.repositories.ApplicationRepository;
import com.example.spring_telecom.repositories.InstallationRepository;
import com.example.spring_telecom.repositories.PaymentRepository;
import com.example.spring_telecom.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService service;
    @Autowired
    private PaymentRepository paymentRepository;


    @Autowired
    private InstallationRepository installationRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    public List<Payment> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Payment create(@RequestBody Payment payment) {
        return service.create(payment);
    }

    @PutMapping("/{id}")
    public Payment update(@PathVariable Long id, @RequestBody Payment payment) {
        return service.update(id, payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    @PostMapping("/by-applications")
    public List<Payment> getPaymentsByApplicationIds(@RequestBody List<Long> applicationIds) {
        return paymentRepository.findByApplicationIdIn(applicationIds);
    }


    @PostMapping("/{paymentId}/process")
    public ResponseEntity<?> processPayment(@PathVariable Long paymentId, @RequestBody Map<String, String> paymentData) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);

        if (optionalPayment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Payment payment = optionalPayment.get();

        // Validate payment data
        String cardNumber = paymentData.get("cardNumber");
        String cvv = paymentData.get("cvv");
        String expiryDate = paymentData.get("expiryDate"); // This is likely "MM/YY" format

        // Check if card number is the test card
        if (!"4242424242424242".equals(cardNumber)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid card number"));
        }

        // Check if CVV is the test value
        if (!"123".equals(cvv)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid CVV"));
        }

        try {
            // Parse MM/YY format (e.g., "12/34" -> December 2034)
            String[] parts = expiryDate.split("/");
            if (parts.length != 2) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid expiry date format. Use MM/YY"));
            }

            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            // Convert 2-digit year to 4-digit year (assuming 2000s)
            int fullYear = 2000 + year;

            // Validate month is valid (1-12)
            if (month < 1 || month > 12) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid month in expiry date"));
            }

            // Create a LocalDate for the last day of the expiry month
            LocalDate expiry = LocalDate.of(fullYear, month, 1)
                    .with(TemporalAdjusters.lastDayOfMonth());

            // Check if card is expired
            if (expiry.isBefore(LocalDate.now())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Card expired"));
            }

            // Update payment status
            payment.setStatus("PAID");
            paymentRepository.save(payment);

            // Create installation
            Installation installation = new Installation();
            installation.setApplication(payment.getApplication());
            installation.setStatus("UNSCHEDULED");
            installationRepository.save(installation);

            return ResponseEntity.ok(Map.of("message", "Payment successful", "installationId", installation.getId()));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid expiry date format"));
        } catch (DateTimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid date values"));
        }
    }
}