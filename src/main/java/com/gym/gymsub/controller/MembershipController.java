package com.gym.gymsub.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.gymsub.dto.CreateMembershipRequest;
import com.gym.gymsub.dto.UpdateMembershipRequest;
import com.gym.gymsub.model.Membership;
import com.gym.gymsub.service.MembershipService;
import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<Membership> create(@Valid @RequestBody CreateMembershipRequest request) {
        return ResponseEntity.ok(membershipService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Membership>> findAll() {
        return ResponseEntity.ok(membershipService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membership> findById(@PathVariable Long id) {
        return ResponseEntity.ok(membershipService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membership> update(
            @PathVariable Long id,
            @RequestBody UpdateMembershipRequest request
    ) {
        return ResponseEntity.ok(membershipService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        membershipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}