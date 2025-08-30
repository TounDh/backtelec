package com.example.spring_telecom.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate submissionDate;
    private LocalDate responseDate;
    private boolean response;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "srvce_id")
    private Srvce srvce;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private Installation installation;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private Payment payment;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDate submissionDate) { this.submissionDate = submissionDate; }
    public LocalDate getResponseDate() { return responseDate; }
    public void setResponseDate(LocalDate responseDate) { this.responseDate = responseDate; }
    public boolean isResponse() { return response; }
    public void setResponse(boolean response) { this.response = response; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Srvce getSrvce() { return srvce; }
    public void setSrvce(Srvce srvce) { this.srvce = srvce; }
    public Installation getInstallation() { return installation; }
    public void setInstallation(Installation installation) { this.installation = installation; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
}