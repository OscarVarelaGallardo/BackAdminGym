// src/main/java/com/gym/gymsub/service/ClientAuthService.java
package com.gym.gymsub.service;

import com.gym.gymsub.controller.AuthController;
import com.gym.gymsub.request.ClientLoginRequest;
import com.gym.gymsub.request.ClientLoginResponse;
import com.gym.gymsub.model.ClientUser;
import com.gym.gymsub.repository.ClientUserRepository;
import com.gym.gymsub.security.JwtService;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


@Service
public class ClientAuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final ClientUserRepository clientUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ClientAuthService(ClientUserRepository clientUserRepository,
                             PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.clientUserRepository = clientUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ClientLoginResponse login(ClientLoginRequest request) {

        ClientUser user = clientUserRepository.findByEmailAndActiveTrue(request.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado o inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user.getId());

        ClientLoginResponse.ClientUserDto userDto =
                new ClientLoginResponse.ClientUserDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                );

        return new ClientLoginResponse(token, userDto);
    }

    public ClientUser register(ClientLoginRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();
        log.info("Registrando usuario: {}", email);

        try {

            ClientUser existingUser = clientUserRepository.findByEmail(email);

            if (existingUser != null && existingUser.isActive()) {
                throw new IllegalArgumentException("El usuario ya existe");
            }


            ClientUser newUser = new ClientUser();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPasswordHash(passwordEncoder.encode(password));
            newUser.setActive(true);

            log.info("Usuario registrado con éxito: {}", email);
            return clientUserRepository.save(newUser);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage());
        }
    }
}