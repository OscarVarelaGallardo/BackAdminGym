package com.gym.gymsub.controller;

import com.gym.gymsub.model.User;
import com.gym.gymsub.request.LoginRequest;
import com.gym.gymsub.request.RegisterUserRequest;
import com.gym.gymsub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequest request) {
        User user = userService.register(request);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "El correo electrónico ya está en uso"));
        }
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return userService.findByEmail(request.getEmail())
                .filter(u -> userService.isPasswordValid(u, request.getPassword()))
                .map(u -> ResponseEntity.ok(Map.of(
                        "id", u.getId(),
                        "name", u.getName(),
                        "email", u.getEmail()

                        // luego aquí devolvemos también el JWT
                )))
                .orElse(ResponseEntity
                        .status(401)
                        .body(Map.of("message", "Credenciales inválidas")));
    }
}