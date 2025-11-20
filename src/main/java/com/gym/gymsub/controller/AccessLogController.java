package com.gym.gymsub.controller;


import com.gym.gymsub.model.AccessLog;
import com.gym.gymsub.request.RegisterAccessRequest;
import com.gym.gymsub.service.AccessLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/access")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccessLogController {

    private final AccessLogService accessLogService;

    @PostMapping
    public ResponseEntity<AccessLog> register(@Valid @RequestBody RegisterAccessRequest request) {
        return ResponseEntity.ok(accessLogService.registerAccess(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccessLog>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(accessLogService.findByUser(userId));
    }

    @GetMapping("/range")
    public ResponseEntity<List<AccessLog>> byRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(accessLogService.findBetween(from, to));
    }
}