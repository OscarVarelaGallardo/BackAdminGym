package com.gym.gymsub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gym_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;      // Nombre del gym

    @Column(columnDefinition = "TEXT")
    private String address;   // Dirección

    @Column(columnDefinition = "TEXT")
    private String schedule;  // Horarios

    private String phone;     // Teléfono

    @Column(name = "logo_url")
    private String logoUrl;   // URL del logo

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}