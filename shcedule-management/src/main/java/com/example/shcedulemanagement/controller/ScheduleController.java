package com.example.shcedulemanagement.controller;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.exceptions.InvalidEntityIdException;
import com.example.shcedulemanagement.exceptions.InvalidPasswordException;
import com.example.shcedulemanagement.repository.ScheduleRepository;
import com.example.shcedulemanagement.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    // BindingResult 검증
    private void validateRequest(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
    }

    @FunctionalInterface
    private interface ServiceOperator {
        String execute();
    }

    // 예외 처리 래퍼 메서드
    private String handleServiceException(ServiceOperator operation) {
        try {
            return operation.execute();
        } catch (InvalidEntityIdException e) {  // 존재하지 않는 ID
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (InvalidPasswordException e) {  // 틀린 비밀번호
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    // 생성자 주입
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 조회
    @GetMapping
    public List<ScheduleResponseDto> getSchedules(@RequestParam(required = false) String sort, Integer manager_id) {
        if ("updateDate".equalsIgnoreCase(sort)) {
            return manager_id != null
                    ? scheduleService.getSchedulesSortedByUpdateDateAndManager(manager_id) // 수정일 & 담당자 id 기준 조회
                    : scheduleService.getSchedulesSortedByUpdateDate();     // 수정일 기준 조회
        } else if (manager_id != null) // 담당자 id 기준 조회
            return scheduleService.getSchedulesByManager(manager_id);
        else // 둘 다 해당 X (전체 조회)
            return scheduleService.getSchedules();
    }

    // 일정 생성
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody @Valid ScheduleRequestDto request, BindingResult bindingResult) {
        validateRequest(bindingResult);     // 잘못된 입력 (할일 최대 200자, 필수값, 비밀번호 필수값)
        return scheduleService.createSchedule(request);
    }

    // id로 단건 일정 검색
    @GetMapping("/{id}")
    public ScheduleResponseDto findById(@PathVariable int id) {
        // 잘못된 정보 조회
        if(scheduleRepository.findById(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        return scheduleRepository.findById(id);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public String updateSchedule(@PathVariable int id, @RequestBody @Valid ScheduleRequestDto request, BindingResult bindingResult) {
        // 잘못된 입력 (할일 최대 200자, 필수값, 비밀번호 필수값)
        validateRequest(bindingResult);
        return handleServiceException(() -> scheduleService.updateSchedule(id, request));
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable int id, @RequestBody ScheduleRequestDto request) {
        return handleServiceException(() -> scheduleService.deleteSchedule(id, request));
    }

    // 페이지네이션
    @GetMapping("/v2")
    public List<ScheduleResponseDto> getSchedulesV2(@RequestParam(required = false) Integer page, Integer size) {
        return (page != null && size != null)
                ? scheduleService.getSchedulesV2(page, size)
                : scheduleService.getSchedules();
    }
}
