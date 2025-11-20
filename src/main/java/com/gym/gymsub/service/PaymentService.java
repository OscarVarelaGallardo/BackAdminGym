package com.gym.gymsub.service;


import com.gym.gymsub.model.Payment;
import com.gym.gymsub.model.UserMembership;
import com.gym.gymsub.repository.PaymentRepository;
import com.gym.gymsub.repository.UserMembershipRepository;
import com.gym.gymsub.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserMembershipRepository userMembershipRepository;

    public Payment registerPayment(CreatePaymentRequest request) {
        UserMembership um = userMembershipRepository.findById(request.getUserMembershipId())
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        Payment payment = Payment.builder()
                .userMembership(um)
                .amount(request.getAmount())
                .method(request.getMethod())
                .reference(request.getReference())
                .build();

        return paymentRepository.save(payment);
    }

    public List<Payment> findByUser(Long userId) {
        return paymentRepository.findByUserMembershipUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Payment> findByUserMembership(Long userMembershipId) {
        return paymentRepository.findByUserMembershipIdOrderByCreatedAtDesc(userMembershipId);
    }
}