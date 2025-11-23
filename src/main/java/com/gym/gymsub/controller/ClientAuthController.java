package com.gym.gymsub.controller;

import com.gym.gymsub.model.ClientUser;
import com.gym.gymsub.request.ClientLoginRequest;
import com.gym.gymsub.request.ClientLoginResponse;
import com.gym.gymsub.service.ClientAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/client/auth")
@CrossOrigin(origins = "*") // ajusta según tu necesidad
public class ClientAuthController {

    private final ClientAuthService clientAuthService;

    public ClientAuthController(ClientAuthService clientAuthService) {
        this.clientAuthService = clientAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientLoginRequest request) {
        try {
            ClientLoginResponse resp = clientAuthService.login(request);
            return ResponseEntity.ok(resp);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Aquí podrías invalidar el token si estuvieras usando uno real
        return ResponseEntity.ok().build();
    }
    //Crear user nuevo
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ClientLoginRequest request) {
        try {
            ClientUser resp = clientAuthService.register(request);
            log.info("Registro de usuario OK: {}", resp.getEmail());
            // 201 Created
            return ResponseEntity.status(201).body(resp);

        } catch (IllegalArgumentException e) {
            // Caso "El usuario ya existe"
            return ResponseEntity
                    .status(400)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            // Cualquier otro error
            return ResponseEntity
                    .status(500)
                    .body("Error interno al registrar el usuario");
        }
    }


}