package com.example.shcedulemanagement.service;

import com.example.shcedulemanagement.dto.ManagerRequestDto;
import com.example.shcedulemanagement.dto.ManagerResponseDto;
import com.example.shcedulemanagement.entity.Manager;
import com.example.shcedulemanagement.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ManagerResponseDto createManager(ManagerRequestDto request) {
        Manager savedManager = managerRepository.save(new Manager(request));
        return new ManagerResponseDto(savedManager);
    }

    public List<ManagerResponseDto> getManagers() {
        return managerRepository.findAll();
    }
}
