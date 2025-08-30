package com.example.spring_telecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commitment;
    private String speed;
    private double price;

    @ManyToOne
    @JoinColumn(name = "srvce_id")
    @JsonIgnore
    private Srvce srvce;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCommitment() { return commitment; }
    public void setCommitment(String commitment) { this.commitment = commitment; }
    public String getSpeed() { return speed; }
    public void setSpeed(String speed) { this.speed = speed; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public Srvce getSrvce() { return srvce; }
    public void setSrvce(Srvce srvce) { this.srvce = srvce; }
}