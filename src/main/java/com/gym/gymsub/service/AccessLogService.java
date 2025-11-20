package com.gym.gymsub.service;

import com.gym.gymsub.dto.AccessLogNotificationDto;
import com.gym.gymsub.model.AccessLog;
import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.AccessLogRepository;
import com.gym.gymsub.request.RegisterAccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final UserService userService;              // 👈 usamos el service
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public AccessLog registerAccess(RegisterAccessRequest request) {
        User user = userService.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AccessLog accessLog = new AccessLog();
        accessLog.setUser(user);
        accessLog.setType(request.getType());
        accessLog.setSource(request.getSource());
        accessLog.setAccessTime(LocalDateTime.now());

        AccessLog saved = accessLogRepository.save(accessLog);

        // DTO que mandas a los clientes
        AccessLogNotificationDto dto = new AccessLogNotificationDto(
                user.getId(),
                user.getName(),
                saved.getType().name(),
                saved.getSource(),
                saved.getAccessTime()
        );

        // 👇 IMPORTANTE: topic exacto
        messagingTemplate.convertAndSend("/topic/access-logs", dto);

        return saved;
    }

    public List<AccessLog> findByUser(Long userId) {
        return accessLogRepository.findByUserIdOrderByAccessTimeDesc(userId);
    }

    public List<AccessLog> findBetween(LocalDateTime from, LocalDateTime to) {
        return accessLogRepository
                .findByAccessTimeBetweenOrderByAccessTimeDesc(from, to);
    }
}