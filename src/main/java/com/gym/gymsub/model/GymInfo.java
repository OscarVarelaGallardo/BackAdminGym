package com.gym.gymsub.model;

import jakarta.persistence.*;
import lombok.*;
import com.gym.gymsub.model.SubscriptionStatus;
import java.time.LocalDate;

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

    @Column(name = "notifications_enabled", nullable = false)
    private boolean notificationsEnabled = true; // 👈 NUEVO


    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "trial_start_date")
    private LocalDate trialStartDate;

    @Column(name = "trial_end_date")
    private LocalDate trialEndDate;

    @Column(name = "paid_until")
    private LocalDate paidUntil;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    private boolean blocked = false;


    @PrePersist
    public void prePersist() {
        if (trialStartDate == null) {
            trialStartDate = LocalDate.now();
        }
        if (trialEndDate == null) {
            trialEndDate = trialStartDate.plusDays(15);
        }
        if (subscriptionStatus == null) {
            subscriptionStatus = SubscriptionStatus.TRIAL;
        }
    }
}