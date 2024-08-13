package com.example.shcedulemanagement.controller;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.exceptions.InvalidEntityIdException;
import com.example.shcedulemanagement.repository.ScheduleRepository;
import com.example.shcedulemanagement.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    // 생성자 주입
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(@RequestParam(required = false) String sort, Integer manager_id) {
        if (sort != null && sort.equalsIgnoreCase("updateDate")) {
            if (manager_id != null) // 수정일 & 담당자 id 기준 조회
                return scheduleService.getSchedulesSortedByUpdateDateAndManager(manager_id);
            else    // 수정일 기준 정렬 조회
                return scheduleService.getSchedulesSortedByUpdateDate();
        } else if (manager_id != null) // 담당자 id 기준 조회
            return scheduleService.getSchedulesByManager(manager_id);
        else // 둘 다 해당 X (전체 조회)
            return scheduleService.getSchedules();
    }

    // 일정 생성
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto request) {
        return scheduleService.createSchedule(request);
    }

    // id로 단건 일정 검색
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto findById(@PathVariable int id) {
        // 잘못된 정보 조회
        if(scheduleRepository.findById(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        else return scheduleRepository.findById(id);
    }

    // 일정 수정
    @PutMapping("/schedules/{id}")
    public String updateSchedule(@PathVariable int id, @RequestBody ScheduleRequestDto request) {
        try {
            return scheduleService.updateSchedule(id, request);
        } catch (Exception e) {
            if(e instanceof InvalidEntityIdException)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // 존재하지 않는 id
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage()); // 비밀번호 불일치
        }
    }

    // 일정 삭제
    @DeleteMapping("/schedules/{id}")
    public String deleteSchedule(@PathVariable int id, @RequestBody ScheduleRequestDto request) {
        try {
            return scheduleService.deleteSchedule(id, request);
        } catch (Exception e) {
            if(e instanceof InvalidEntityIdException)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); // 존재하지 않는 id
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage()); // 비밀번호 불일치
        }
    }

    // 페이지네이션
    @GetMapping("/v2/schedules")
    public List<ScheduleResponseDto> getSchedulesV2(@RequestParam(required = false) Integer page, Integer size) {
        if(page != null && size != null) {
            return scheduleService.getSchedulesV2(page, size);
        } else {
            return scheduleService.getSchedules();
        }
    }
}
