package com.gym.gymsub.controller;



import com.gym.gymsub.model.GymInfo;
import com.gym.gymsub.model.SubscriptionStatus;
import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.UserRepository;
import com.gym.gymsub.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;

    public BillingController(UserRepository userRepository,
                             SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestBody String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        GymInfo gym = user.getGymInfo();
        if (gym == null) {
            return ResponseEntity.badRequest().body("El usuario no tiene gym asignado");
        }

        LocalDate today = LocalDate.now();

        long daysLeft = 0;

        if (gym.getSubscriptionStatus() == SubscriptionStatus.TRIAL && gym.getTrialEndDate() != null) {
            daysLeft = ChronoUnit.DAYS.between(today, gym.getTrialEndDate());
        }
        else if (gym.getPaidUntil() != null) {
            daysLeft = ChronoUnit.DAYS.between(today, gym.getPaidUntil());
        }

        boolean active = subscriptionService.isGymActive(gym);

        return ResponseEntity.ok(
                new BillingStatusDto(
                        gym.getSubscriptionStatus().name(),
                        gym.getTrialEndDate(),
                        gym.getPaidUntil(),
                        daysLeft,
                        active
                )
        );
    }

    public record BillingStatusDto(
            String status,
            LocalDate trialEndDate,
            LocalDate paidUntil,
            long daysLeft,
            boolean active
    ) {}
}