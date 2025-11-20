package com.gym.gymsub.request;


import com.gym.gymsub.model.AccessLog;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterAccessRequest {

    @NotNull
    private Long userId;

    @NotNull
    private AccessLog.AccessType type;

    private String source;
}