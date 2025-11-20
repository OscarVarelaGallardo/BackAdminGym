package com.gym.gymsub.service;


import com.gym.gymsub.dto.DashboardSummaryResponse;
import com.gym.gymsub.model.AccessLog.AccessType;
import com.gym.gymsub.repository.AccessLogRepository;
import com.gym.gymsub.repository.PaymentRepository;
import com.gym.gymsub.repository.UserMembershipRepository;
import com.gym.gymsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AccessLogRepository accessLogRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final UserMembershipRepository userMembershipRepository;

    public DashboardSummaryResponse getSummary() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime tomorrowStart = today.plusDays(1).atStartOfDay();

        // Rango del mes actual
        YearMonth currentMonth = YearMonth.from(today);
        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        LocalDateTime monthStart = firstDayOfMonth.atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        // HOY: entradas (solo ENTRY)
        long entriesToday = accessLogRepository
                .countByTypeAndAccessTimeBetween(AccessType.ENTRY, todayStart, tomorrowStart);

        // HOY: pagos
        BigDecimal paymentsTodayAmount = paymentRepository
                .sumAmountBetween(todayStart, tomorrowStart);

        // HOY: nuevos clientes
        long newClientsToday = userRepository
                .countByCreatedAtBetween(todayStart, tomorrowStart);

        // GENERALES: clientes activos (por membresía)
        long activeClients = userMembershipRepository.countActiveUsers();

        // GENERALES: membresías por vencer próximos 7 días
        LocalDate in7Days = today.plusDays(7);
        long expiringNext7Days = userMembershipRepository
                .countActiveUsersWithMembershipExpiringBetween(today, in7Days);

        // MES: total pagos del mes
        BigDecimal paymentsThisMonthAmount = paymentRepository
                .sumAmountBetween(monthStart, now);

        return DashboardSummaryResponse.builder()
                .entriesToday(entriesToday)
                .paymentsTodayAmount(paymentsTodayAmount)
                .newClientsToday(newClientsToday)
                .activeClients(activeClients)
                .expiringMembershipsNext7Days(expiringNext7Days)
                .paymentsThisMonthAmount(paymentsThisMonthAmount)
                .build();
    }
}