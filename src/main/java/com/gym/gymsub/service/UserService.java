package com.gym.gymsub.service;


import ch.qos.logback.classic.Logger;
import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.UserRepository;
import com.gym.gymsub.request.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterUserRequest request) {


        logger.info("Registrando usuario con email: " + request.getEmail());
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return null;
        }
        try {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setQrToken(UUID.randomUUID().toString());
            return userRepository.save(user);
        }catch (Exception e){
            logger.error("Error al registrar el usuario: " + e.getMessage());
            return null;
         }
        }

    public User findByQrToken(String qrToken) {
        return userRepository.findByQrToken(qrToken).orElse(null);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public boolean isPasswordValid(Optional<User> user, String password) {
        Logger log = (Logger) LoggerFactory.getLogger(UserService.class);
        String passwordEncode = passwordEncoder.encode(password);
        String userPassword = user.isPresent() ? user.get().getPassword() : "N/A";
        log.info(" Contraseña codificada: " + passwordEncode);
        log.info(" Contraseña del usuario: " + userPassword);
        return passwordEncoder.matches(password, userPassword);
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}