package com.gym.gymsub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogNotificationDto {
    private Long userId;
    private String userName;
    private String type;        // ENTRY / EXIT
    private String source;      // QR_CODE, MANUAL, etc.
    private LocalDateTime timestamp;
}
