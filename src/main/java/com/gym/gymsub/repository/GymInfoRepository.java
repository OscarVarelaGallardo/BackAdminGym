package com.gym.gymsub.repository;

import com.gym.gymsub.model.GymInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymInfoRepository extends JpaRepository<GymInfo, Long> {

    // Busca gyms por el id del usuario dueño
    List<GymInfo> findByUser_Id(Long userId);
}