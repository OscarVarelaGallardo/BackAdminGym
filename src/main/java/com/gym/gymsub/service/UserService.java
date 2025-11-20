package com.gym.gymsub.service;


import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.UserRepository;
import com.gym.gymsub.request.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return null;
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .qrToken(UUID.randomUUID().toString())
                .build();
        return  userRepository.save(user);
    }
    public User findByQrToken(String qrToken) {
        return userRepository.findByQrToken(qrToken).orElse(null);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isPasswordValid(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}