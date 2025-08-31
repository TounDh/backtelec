package com.example.spring_telecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"applications", "password"})// Prevents circular reference
    private User user;

    @ManyToOne
    @JoinColumn(name = "srvce_id")
    private Srvce srvce;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    // Constructors, getters and setters
    public Application() {
        this.applicationDate = LocalDateTime.now();
        this.status = "PENDING";
    }

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

    // Add this method to Application.java
    public boolean isApproved() {
        return "APPROVED".equals(this.status);
    }
}