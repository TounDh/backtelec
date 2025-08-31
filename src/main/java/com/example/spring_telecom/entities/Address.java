package com.example.spring_telecom.entities;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String governorate;
    private String city;
    private String zipCode;
    private Double longitude;
    private Double latitude;
    private String country;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public Address() {}


    public Address(String street, String governorate, String city, String zipCode,
                   Double longitude, Double latitude, String country, User user) {
        this.street = street;
        this.governorate = governorate;
        this.city = city;
        this.zipCode = zipCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getGovernorate() { return governorate; }
    public void setGovernorate(String governorate) { this.governorate = governorate; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}