package com.gym.gymsub.service;

import com.gym.gymsub.model.Membership;
import com.gym.gymsub.repository.MembershipRepository;
import com.gym.gymsub.request.CreateMembershipRequest;
import com.gym.gymsub.request.UpdateMembershipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public Membership create(CreateMembershipRequest request) {
        Membership membership = Membership.builder()
                .name(request.getName())
                .description(request.getDescription())
                .durationDays(request.getDurationDays())
                .price(request.getPrice())
                .build();
        return membershipRepository.save(membership);
    }

    public List<Membership> findAll() {
        return membershipRepository.findAll();
    }

    public Membership findById(Long id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found"));
    }

    public Membership update(Long id, UpdateMembershipRequest request) {
        Membership membership = findById(id);

        if (request.getName() != null) membership.setName(request.getName());
        if (request.getDescription() != null) membership.setDescription(request.getDescription());
        if (request.getDurationDays() != null) membership.setDurationDays(request.getDurationDays());
        if (request.getPrice() != null) membership.setPrice(request.getPrice());

        return membershipRepository.save(membership);
    }

    public void delete(Long id) {
        membershipRepository.deleteById(id);
    }
}