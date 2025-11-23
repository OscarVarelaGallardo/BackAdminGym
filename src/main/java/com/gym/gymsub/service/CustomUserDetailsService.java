package com.gym.gymsub.service;

import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service // 👈 esto registra el bean que Spring necesita
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Aquí mapeas tu entidad User al objeto que Spring Security entiende
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // 👈 debe estar ya encriptado con BCrypt
                .roles("ADMIN")               // por ahora fijo, luego podemos meter roles
                .build();
    }
}