package com.gym.gymsub.controller;

import com.gym.gymsub.model.User;
import com.gym.gymsub.request.LoginRequest;
import com.gym.gymsub.request.RegisterUserRequest;
import com.gym.gymsub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.gymsub.security.JwtService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

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
         Optional<User> userOpt =  userService.findByEmail(request.getEmail());
        //Validar la contraseña
        if (userOpt.isEmpty() || !userService.isPasswordValid(userOpt , request.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Correo electrónico o contraseña incorrectos"));

        }
        User user = userOpt.get();
        String token = jwtService.generateToken(String.valueOf(userOpt.get()));
        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail()
                )
        ));


    }
}