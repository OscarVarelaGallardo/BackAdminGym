package com.gym.gymsub.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardSummaryResponse {

    // HOY
    private long entriesToday;
    private BigDecimal paymentsTodayAmount;
    private long newClientsToday;

    // GENERALES
    private long activeClients;
    private long expiringMembershipsNext7Days;

    // MES
    private BigDecimal paymentsThisMonthAmount;
}