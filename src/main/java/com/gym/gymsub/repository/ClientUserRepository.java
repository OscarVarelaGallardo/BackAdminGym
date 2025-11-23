package com.gym.gymsub.repository;

import com.gym.gymsub.model.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientUserRepository extends JpaRepository<ClientUser, String> {

   ClientUser findByEmailAndActiveTrue(String email);
    ClientUser findByEmail(String email);
}