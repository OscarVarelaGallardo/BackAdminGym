package com.gym.gymsub.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateMembershipRequest {

    private String name;
    private String description;
    private Integer durationDays;
    private BigDecimal price;
}