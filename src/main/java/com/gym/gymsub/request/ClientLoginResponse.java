package com.gym.gymsub.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientLoginResponse {

    private String token;
    private ClientUserDto user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientUserDto {
        private String id;
        private String name;
        private String email;
    }
}

