package com.gym.gymsub.repository;


import com.gym.gymsub.model.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {

    Optional<UserMembership> findFirstByUserIdAndStatusOrderByEndDateDesc(
            Long userId,
            UserMembership.Status status
    );
    @Query("""
           select count(distinct um.user.id)
           from UserMembership um
           where um.status = 'Active'
           """)
    long countActiveUsers();

    @Query("""
           select count(distinct um.user.id)
           from UserMembership um
           where um.status = 'Active'
             and um.endDate between :from and :to
           """)
    long countActiveUsersWithMembershipExpiringBetween(LocalDate from, LocalDate to);
    List<UserMembership> findByUserIdOrderByStartDateDesc(Long userId);
}