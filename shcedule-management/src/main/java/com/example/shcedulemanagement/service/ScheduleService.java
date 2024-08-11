package com.example.shcedulemanagement.service;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.entity.Schedule;
import com.example.shcedulemanagement.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(JdbcTemplate jdbcTemplate) {
        scheduleRepository = new ScheduleRepository(jdbcTemplate);
    }

    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAll();
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto request) {
        Schedule savedSchedule = scheduleRepository.save(new Schedule(request));
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getSchedulesSortedByUpdateDate() {
        return scheduleRepository.findLatestUpdated();
    }

    public List<ScheduleResponseDto> getSchedulesByManager(String manager) {
        return scheduleRepository.findByManager(manager);
    }

    public List<ScheduleResponseDto> getSchedulesSortedByUpdateDateAndManager(String manager) {
        return scheduleRepository.findLatestUpdatedByManager(manager);
    }
}
