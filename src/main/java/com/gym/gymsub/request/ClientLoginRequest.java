package com.gym.gymsub.request;

import lombok.Data;

@Data

public class ClientLoginRequest {
    private String email;
    private String password;
    private String name; // para registro

    // getters y setters
}