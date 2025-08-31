package com.example.spring_telecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Srvce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double installationFees;

    @OneToMany(mappedBy = "srvce", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Application> applications;


    @OneToMany(mappedBy = "srvce", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("srvce")
    private List<Offer> offers;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getInstallationFees() { return installationFees; }
    public void setInstallationFees(double installationFees) { this.installationFees = installationFees; }
    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
    public List<Offer> getOffers() { return offers; }
    public void setOffers(List<Offer> offers) { this.offers = offers; }
    public void addOffer(Offer offer) {
        if (offers == null) {
            offers = new ArrayList<>();
        }
        offers.add(offer);
        offer.setSrvce(this);
    }
}