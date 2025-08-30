package com.example.spring_telecom.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Installation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String status;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
}