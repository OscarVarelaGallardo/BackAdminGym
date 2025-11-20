package com.gym.gymsub.controller;

import com.gym.gymsub.model.UserMembership;
import com.gym.gymsub.request.AssignMembershipRequest;
import com.gym.gymsub.service.UserMembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserMembershipController {

    private final UserMembershipService userMembershipService;

    @PostMapping
    public ResponseEntity<UserMembership> assign(@Valid @RequestBody AssignMembershipRequest request) {
        return ResponseEntity.ok(userMembershipService.assignMembership(request));
    }

    @GetMapping("/user/{userId}/current")
    public ResponseEntity<UserMembership> getCurrent(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

     UserMembership findUserMembership= userMembershipService.getCurrentActiveMembership(userId);
        if (findUserMembership == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findUserMembership);
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<UserMembership>> getHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(userMembershipService.getUserMembershipHistory(userId));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<UserMembership> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(userMembershipService.cancel(id));
    }
}