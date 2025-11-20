package com.gym.gymsub.request;



import com.gym.gymsub.model.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {

    @NotNull
    private Long userMembershipId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Payment.PaymentMethod method;

    private String reference;
}