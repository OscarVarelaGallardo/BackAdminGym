package com.gym.gymsub.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateGymRequest {
    private String name;
    private String address;
    private String schedule;
    private String phone;
    private String logoUrl;
    @NotNull
    private Long userId;
    private Boolean notificationsEnabled;
}
