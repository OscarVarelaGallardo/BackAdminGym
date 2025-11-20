package com.gym.gymsub.repository;


import com.gym.gymsub.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    List<AccessLog> findByUserIdOrderByAccessTimeDesc(Long userId);
    long countByTypeAndAccessTimeBetween(AccessLog.AccessType type,
                                         LocalDateTime from,
                                         LocalDateTime to);
    List<AccessLog> findByAccessTimeBetweenOrderByAccessTimeDesc(LocalDateTime from, LocalDateTime to);
}