package com.gym.gymsub.controller;


import com.gym.gymsub.model.AccessLog;
import com.gym.gymsub.model.User;
import com.gym.gymsub.request.RegisterAccessRequest;
import com.gym.gymsub.request.ScanQrRequest;
import com.gym.gymsub.service.AccessLogService;
import com.gym.gymsub.service.UserService;
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

    private final UserService userService;

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
    @PostMapping("/scan-qr")
    public ResponseEntity<AccessLog> registerByQr(@RequestBody ScanQrRequest request) {
        String token = request.qrToken();
        if (token == null || token.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // buscar usuario por qrToken
        User user = userService.findByQrToken(token);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        //

        List<AccessLog> logs = accessLogService.findByUser(user.getId());
        AccessLog.AccessType type = AccessLog.AccessType.ENTRY;
        if (!logs.isEmpty()) {
            AccessLog lastLog = logs.getFirst();
            if (lastLog.getType() == AccessLog.AccessType.ENTRY) {
                type = AccessLog.AccessType.EXIT;
            }
        }
        // Registrar acceso
        RegisterAccessRequest accessRequest = new RegisterAccessRequest();
        accessRequest.setUserId(user.getId());
        accessRequest.setType(type);
        accessRequest.setSource("QR");
        AccessLog log = accessLogService.registerAccess(accessRequest);
        //eliminar la información del usuario para no exponerla
        log.setUser(null);
        return ResponseEntity.ok(log);

    }
}