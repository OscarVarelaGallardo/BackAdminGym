package com.gym.gymsub.model;


import com.gym.gymsub.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime accessTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccessType type; // ENTRY / EXIT

    private String source; // QR, recepción, etc.

    public enum AccessType {
        ENTRY,
        EXIT
    }

    @PrePersist
    public void prePersist() {
        if (accessTime == null) {
            accessTime = LocalDateTime.now();
        }
    }
}