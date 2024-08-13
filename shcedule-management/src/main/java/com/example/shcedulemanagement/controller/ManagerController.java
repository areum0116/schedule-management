package com.example.shcedulemanagement.controller;

import com.example.shcedulemanagement.dto.ManagerRequestDto;
import com.example.shcedulemanagement.dto.ManagerResponseDto;
import com.example.shcedulemanagement.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor // 생성자 주입
public class ManagerController {

    private final ManagerService managerService;

    // 담당자 생성
    @PostMapping("/managers")
    public ManagerResponseDto createManager(@RequestBody @Valid ManagerRequestDto request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) // 잘못된 입력 (이메일 형식)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        return managerService.createManager(request);
    }

    // 담당자 조회
    @GetMapping("/managers")
    public List<ManagerResponseDto> getManagers() {
        return managerService.getManagers();
    }
}
