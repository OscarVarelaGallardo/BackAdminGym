package com.gym.gymsub.service;


import com.gym.gymsub.model.*;
import com.gym.gymsub.repository.GymInfoRepository;
import com.gym.gymsub.repository.GymPaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SubscriptionService {

    private final GymInfoRepository gymInfoRepository;
    private final GymPaymentRepository gymPaymentRepository;

    public SubscriptionService(GymInfoRepository gymInfoRepository,
                               GymPaymentRepository gymPaymentRepository) {
        this.gymInfoRepository = gymInfoRepository;
        this.gymPaymentRepository = gymPaymentRepository;
    }

    public boolean isGymActive(GymInfo gym) {
        if (gym.isBlocked()) {
            return false;
        }

        LocalDate today = LocalDate.now();

        // Si está en trial y no ha vencido
        if (gym.getSubscriptionStatus() == SubscriptionStatus.TRIAL &&
                gym.getTrialEndDate() != null &&
                !today.isAfter(gym.getTrialEndDate())) {
            return true;
        }

        // Si está activo y la fecha paidUntil sigue vigente
        if (gym.getSubscriptionStatus() == SubscriptionStatus.ACTIVE &&
                gym.getPaidUntil() != null &&
                !today.isAfter(gym.getPaidUntil())) {
            return true;
        }

        return false;
    }

    public void markExpiredIfNeeded(GymInfo gym) {
        if (!isGymActive(gym)) {
            gym.setSubscriptionStatus(SubscriptionStatus.EXPIRED);
            gymInfoRepository.save(gym);
        }
    }

    /**
     * Registrar un pago manualmente (más adelante lo conectas con MercadoPago).
     * Ejemplo: pagó 1 mes más.
     */
    public GymPayment registerPayment(GymInfo gym, BigDecimal amount, int monthsToAdd) {
        LocalDate baseDate;

        // Si tiene tiempo pagado vigente, se suma desde esa fecha
        if (gym.getPaidUntil() != null && gym.getPaidUntil().isAfter(LocalDate.now())) {
            baseDate = gym.getPaidUntil();
        } else {
            baseDate = LocalDate.now();
        }

        LocalDate newPaidUntil = baseDate.plusMonths(monthsToAdd);

        gym.setPaidUntil(newPaidUntil);
        gym.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        gymInfoRepository.save(gym);

        GymPayment payment = GymPayment.builder()
                .gym(gym)
                .amount(amount)
                .coverageUntil(newPaidUntil)
                .build();

        return gymPaymentRepository.save(payment);
    }
}