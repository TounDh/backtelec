package com.example.spring_telecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isRole() { return role; }
    public void setRole(boolean role) { this.role = role; }
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}