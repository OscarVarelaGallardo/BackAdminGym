package com.gym.gymsub.controller;
import com.gym.gymsub.model.Payment;
import com.gym.gymsub.request.CreatePaymentRequest;
import com.gym.gymsub.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> create(@Valid @RequestBody CreatePaymentRequest request) {
        return ResponseEntity.ok(paymentService.registerPayment(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.findByUser(userId));
    }

    @GetMapping("/subscription/{subscriptionId}")
    public ResponseEntity<List<Payment>> bySubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        return ResponseEntity.ok(paymentService.findByUserMembership(subscriptionId));
    }
}