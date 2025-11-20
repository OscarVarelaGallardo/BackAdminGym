package com.gym.gymsub.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateGymInfoRequest {

    @NotBlank(message = "El nombre del gym es obligatorio")
    private String name;
    private Long gymId;
    private String address;
    private String schedule;
    private String phone;
    private String logoUrl;
}