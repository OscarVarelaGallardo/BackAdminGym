package com.gym.gymsub.service;


import com.gym.gymsub.model.AccessLog;
import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.AccessLogRepository;
import com.gym.gymsub.repository.UserRepository;
import com.gym.gymsub.request.RegisterAccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final UserRepository userRepository;

    public AccessLog registerAccess(RegisterAccessRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AccessLog log = AccessLog.builder()
                .user(user)
                .type(request.getType())
                .source(request.getSource())
                .accessTime(LocalDateTime.now())
                .build();

        return accessLogRepository.save(log);
    }

    public List<AccessLog> findByUser(Long userId) {
        return accessLogRepository.findByUserIdOrderByAccessTimeDesc(userId);
    }

    public List<AccessLog> findBetween(LocalDateTime from, LocalDateTime to) {
        return accessLogRepository.findByAccessTimeBetweenOrderByAccessTimeDesc(from, to);
    }
}