package com.gym.gymsub.controller;

import com.gym.gymsub.dto.GymInfoDto;
import com.gym.gymsub.model.User;
import com.gym.gymsub.request.CreateGymRequest;
import com.gym.gymsub.request.UpdateGymInfoRequest;
import com.gym.gymsub.service.GymInfoService;
import com.gym.gymsub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gym")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GymInfoController {

    private final GymInfoService gymInfoService;
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<List<GymInfoDto>> getGymInfo(@RequestParam(required = false) Long userId) {
        // Validar userId
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        // Buscar usuario
        Optional<User> findUser = userService.findById(userId);
        if (findUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Buscar gyms asociados al usuario
        List<GymInfoDto> gymInfos = gymInfoService.getAllGymInfoByUserId(userId);

        // Siempre 200, aunque sea lista vacía
        return ResponseEntity.ok(gymInfos);
    }

    @PostMapping("/info")
    public ResponseEntity<GymInfoDto> createGymInfo(@Valid @RequestBody CreateGymRequest createRequest) {
        GymInfoDto createdGymInfo = gymInfoService.createGymInfo(createRequest);
        return ResponseEntity.ok(createdGymInfo);
    }

    @PutMapping("/info")
    public ResponseEntity<GymInfoDto> updateGymInfo(@Valid @RequestBody UpdateGymInfoRequest updateRequest) {
        GymInfoDto updatedGymInfo = gymInfoService.updateGymInfo(updateRequest);
        if (updatedGymInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedGymInfo);
    }
}