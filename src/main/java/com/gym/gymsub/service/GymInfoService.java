package com.gym.gymsub.service;

import com.gym.gymsub.dto.GymInfoDto;
import com.gym.gymsub.model.GymInfo;
import com.gym.gymsub.model.User;
import com.gym.gymsub.repository.GymInfoRepository;
import com.gym.gymsub.request.CreateGymRequest;
import com.gym.gymsub.request.UpdateGymInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GymInfoService {

    private final GymInfoRepository gymInfoRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<GymInfoDto> getAllGymInfoByUserId(Long userId) {
        List<GymInfo> gymInfos = gymInfoRepository.findByUser_Id(userId);
        return gymInfos.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public GymInfoDto createGymInfo(CreateGymRequest request) {
        // buscar usuario dueño
        Optional<User> userOpt = userService.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado para id=" + request.getUserId());
        }

        User user = userOpt.get();

        GymInfo gymInfo = GymInfo.builder()
                .name(request.getName())
                .address(request.getAddress())
                .schedule(request.getSchedule())
                .phone(request.getPhone())
                .logoUrl(request.getLogoUrl())
                .user(user)
                .notificationsEnabled(request.getNotificationsEnabled() != null ? request.getNotificationsEnabled() : true)
                .build();

        GymInfo saved = gymInfoRepository.save(gymInfo);
        return convertToDto(saved);
    }

    @Transactional
    public GymInfoDto updateGymInfo( UpdateGymInfoRequest request) {
        Long gymId = request.getGymId();
        Optional<GymInfo> opt = gymInfoRepository.findById(gymId);
        if (opt.isEmpty()) {
            return null;
        }

        GymInfo gymInfo = opt.get();
        gymInfo.setName(request.getName());
        gymInfo.setAddress(request.getAddress());
        gymInfo.setSchedule(request.getSchedule());
        gymInfo.setPhone(request.getPhone());
        gymInfo.setLogoUrl(request.getLogoUrl());
        if (request.getNotificationsEnabled() != null) {
            gymInfo.setNotificationsEnabled(request.getNotificationsEnabled()); // 👈
        }

        GymInfo updated = gymInfoRepository.save(gymInfo);
        return convertToDto(updated);
    }

    // --- Mapper Entity -> DTO ---
    private GymInfoDto convertToDto(GymInfo gymInfo) {
        GymInfoDto dto = new GymInfoDto();
        dto.setId(gymInfo.getId());
        dto.setName(gymInfo.getName());
        dto.setAddress(gymInfo.getAddress());
        dto.setSchedule(gymInfo.getSchedule());
        dto.setPhone(gymInfo.getPhone());
        dto.setLogoUrl(gymInfo.getLogoUrl());
        dto.setUserId(gymInfo.getUser().getId());
        dto.setNotificationsEnabled(gymInfo.isNotificationsEnabled());
        return dto;
    }
}