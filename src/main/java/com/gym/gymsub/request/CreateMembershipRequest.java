package com.gym.gymsub.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateMembershipRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer durationDays;

    @NotNull
    private BigDecimal price;
}
