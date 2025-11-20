package com.gym.gymsub.request;
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
}