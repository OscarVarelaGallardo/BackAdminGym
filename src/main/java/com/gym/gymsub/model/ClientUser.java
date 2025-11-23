package com.gym.gymsub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "client_user")
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(unique = true, nullable = false)
    public String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private boolean active = true;

    private LocalDate membershipEndDate;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private GymInfo gym;  // reutilizamos tu GymInfo



    // getters y setters
}