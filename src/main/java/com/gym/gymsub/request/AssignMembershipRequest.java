package com.gym.gymsub.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignMembershipRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long membershipId;

    private boolean autoRenew = false;
}