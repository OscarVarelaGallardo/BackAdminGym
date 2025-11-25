package com.gym.gymsub.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "gym_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Gym al que aplica
    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private GymInfo gym;

    // Monto pagado
    @Column(nullable = false)
    private BigDecimal amount;

    // Periodo que está cubriendo (por ejemplo el mes)
    @Column(name = "coverage_until", nullable = false)
    private LocalDate coverageUntil;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}