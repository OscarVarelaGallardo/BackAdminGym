package com.gym.gymsub.service;


import com.gym.gymsub.model.Membership;
import com.gym.gymsub.model.UserMembership;
import com.gym.gymsub.repository.UserMembershipRepository;
import com.gym.gymsub.request.AssignMembershipRequest;
import com.gym.gymsub.service.MembershipService;
import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMembershipService {

    private final UserMembershipRepository userMembershipRepository;
    private final UserRepository userRepository;
    private final MembershipService membershipService;

    public UserMembership assignMembership(AssignMembershipRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Membership membership = membershipService.findById(request.getMembershipId());

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(membership.getDurationDays());

        UserMembership userMembership = UserMembership.builder()
                .user(user)
                .membership(membership)
                .startDate(start)
                .endDate(end)
                .autoRenew(request.isAutoRenew())
                .status(UserMembership.Status.ACTIVE)
                .build();

        return userMembershipRepository.save(userMembership);
    }

    public UserMembership getCurrentActiveMembership(Long userId) {
        return userMembershipRepository
                .findFirstByUserIdAndStatusOrderByEndDateDesc(
                        userId,
                        UserMembership.Status.ACTIVE
                )
                .orElse(null);
    }

    public List<UserMembership> getUserMembershipHistory(Long userId) {
        return userMembershipRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    public UserMembership cancel(Long subscriptionId) {
        UserMembership um = userMembershipRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        um.setStatus(UserMembership.Status.CANCELLED);
        return userMembershipRepository.save(um);
    }
}