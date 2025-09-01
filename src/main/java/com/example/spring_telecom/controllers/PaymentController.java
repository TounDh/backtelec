package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Installation;
import com.example.spring_telecom.entities.Payment;
import com.example.spring_telecom.repositories.ApplicationRepository;
import com.example.spring_telecom.repositories.InstallationRepository;
import com.example.spring_telecom.repositories.PaymentRepository;
import com.example.spring_telecom.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @Autowired
    private PaymentService paymentService;

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













    // Get payments by application IDs


    // Get all payments with details (application, user, service)
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPaymentsWithDetails() {
        try {
            List<Payment> payments = paymentRepository.findAllWithApplicationAndUserAndService();
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Search payments with filters
    @GetMapping("/search")
    public ResponseEntity<List<Payment>> searchPayments(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Payment> payments = paymentRepository.searchPayments(search, status, date);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Update payment status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        try {
            Payment payment = paymentService.updatePaymentStatus(id, statusUpdate.get("status"));
            if (payment != null) {
                return ResponseEntity.ok(payment);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Get payments by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable String status) {
        try {
            List<Payment> payments = paymentRepository.findByStatus(status);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Get overdue payments
    @GetMapping("/overdue")
    public ResponseEntity<List<Payment>> getOverduePayments() {
        try {
            List<Payment> payments = paymentRepository.findOverduePayments();
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Get payments by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable Long userId) {
        try {
            List<Payment> payments = paymentRepository.findByUserId(userId);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
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