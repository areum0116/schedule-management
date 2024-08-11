package com.example.shcedulemanagement.service;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.entity.Schedule;
import com.example.shcedulemanagement.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 입력받은 비밀번호 검사
    private boolean checkValidPw(int id, ScheduleRequestDto request) {
        return scheduleRepository.getPw(id) != null && scheduleRepository.getPw(id).equals(request.getPw());
    }

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

    public String updateSchedule(int id, ScheduleRequestDto request) {
        if(scheduleRepository.findById(id) == null) {
            return "Schedule not found";
        }
        else if(!checkValidPw(id, request)) {
            return "Incorrect password";
        }
        else {
            scheduleRepository.update(id, request);
            return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        }
    }

    public String deleteSchedule(int id, ScheduleRequestDto request) {
        if(scheduleRepository.findById(id) == null) {
            return "Schedule not found";
        }
        else if(!checkValidPw(id, request)) {
            return "Incorrect password";
        }
        else {
            scheduleRepository.delete(id, request);
            return "Schedule deleted";
        }
    }

}
