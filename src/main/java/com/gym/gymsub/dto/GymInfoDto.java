package com.gym.gymsub.dto;
import com.gym.gymsub.model.GymInfo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymInfoDto {

    private Long id;
    private String name;
    private String address;
    private String schedule;
    private String phone;
    private String logoUrl;
    private Boolean notificationsEnabled;
    private Long userId;  // 👈 ESTE CAMPO FALTABA

}