package com.example.shcedulemanagement.controller;

import com.example.shcedulemanagement.dto.ManagerRequestDto;
import com.example.shcedulemanagement.dto.ManagerResponseDto;
import com.example.shcedulemanagement.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/managers")
    public ManagerResponseDto createManager(@RequestBody ManagerRequestDto request) {
        return managerService.createManager(request);
    }

    @GetMapping("/managers")
    public List<ManagerResponseDto> getManagers() {
        return managerService.getManagers();
    }
}
