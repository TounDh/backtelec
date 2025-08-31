package com.example.spring_telecom.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_date", nullable = false)
    private LocalDateTime applicationDate;

    private String status; // PENDING, APPROVED, REJECTED

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "srvce_id")
    private Srvce srvce;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    // Constructor that sets the current date automatically
    public Application() {
        this.applicationDate = LocalDateTime.now();
        this.status = "PENDING"; // Default status
    }

    // PrePersist callback to ensure date is set before saving
    @PrePersist
    protected void onCreate() {
        if (applicationDate == null) {
            applicationDate = LocalDateTime.now();
        }
        if (status == null) {
            status = "PENDING";
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Srvce getSrvce() { return srvce; }
    public void setSrvce(Srvce srvce) { this.srvce = srvce; }

    public Offer getOffer() { return offer; }
    public void setOffer(Offer offer) { this.offer = offer; }

    // Utility method to get formatted date
    public String getFormattedApplicationDate() {
        return applicationDate.toString(); // You can format this as needed
    }
}