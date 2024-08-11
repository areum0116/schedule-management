package com.example.shcedulemanagement.controller;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.repository.ScheduleRepository;
import com.example.shcedulemanagement.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        scheduleService = new ScheduleService(jdbcTemplate);
        scheduleRepository = new ScheduleRepository(jdbcTemplate);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(@RequestParam(required = false) String sort, String manager) {
        if(sort != null && sort.equalsIgnoreCase("updateDate")){
            if(manager != null)
                return scheduleService.getSchedulesSortedByUpdateDateAndManager(manager);
            else
                return scheduleService.getSchedulesSortedByUpdateDate();
        }
        else if(manager != null)
            return scheduleService.getSchedulesByManager(manager);
        else
            return scheduleService.getSchedules();
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto request) {
        return scheduleService.createSchedule(request);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto findById(@PathVariable int id) {
        return scheduleRepository.findById(id);
    }

    @PutMapping("/schedules/{id}")
    public String updateSchedule(@PathVariable int id, @RequestBody ScheduleRequestDto request) {
        return scheduleService.updateSchedule(id, request);
    }

    @DeleteMapping("/schedules/{id}")
    public String deleteSchedule(@PathVariable int id, @RequestBody ScheduleRequestDto request) {
        return scheduleService.deleteSchedule(id, request);
    }
}
