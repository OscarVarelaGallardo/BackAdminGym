package com.gym.gymsub.repository;

import com.gym.gymsub.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUserMembershipUserIdOrderByCreatedAtDesc(Long userId);

    List<Payment> findByUserMembershipIdOrderByCreatedAtDesc(Long userMembershipId);

    @Query("""
           select coalesce(sum(p.amount), 0)
           from Payment p
           where p.createdAt between :from and :to
           """)
    BigDecimal sumAmountBetween(LocalDateTime from, LocalDateTime to);

}