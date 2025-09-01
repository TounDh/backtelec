package com.example.spring_telecom.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate deadline;
    private double total;
    private String status;


    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;

    // Constructors
    public Payment() {
        this.status = "PENDING";
    }

    public Payment(Application application, double total, LocalDate deadline) {
        this.application = application;
        this.total = total;
        this.deadline = deadline;
        this.status = "PENDING";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }


}