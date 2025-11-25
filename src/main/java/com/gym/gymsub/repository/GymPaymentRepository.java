package com.gym.gymsub.repository;

import com.gym.gymsub.model.GymPayment;
import com.gym.gymsub.model.GymInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymPaymentRepository extends JpaRepository<GymPayment, Long> {

    List<GymPayment> findByGym(GymInfo gym);

    List<GymPayment> findByGymId(Long gymId);
}