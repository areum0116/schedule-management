package com.example.shcedulemanagement.service;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.entity.Schedule;
import com.example.shcedulemanagement.exceptions.InvalidEntityIdException;
import com.example.shcedulemanagement.exceptions.InvalidPasswordException;
import com.example.shcedulemanagement.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 입력받은 비밀번호 검사
    private boolean isValidPassword(int id, ScheduleRequestDto request) {
        return scheduleRepository.getPw(id) != null && scheduleRepository.getPw(id).equals(request.getPw());
    }

    // 일정 ID 및 비밀번호 검증
    private void validateSchedule(int id, ScheduleRequestDto request) throws InvalidEntityIdException, InvalidPasswordException {
        // 존재하지 않는 id
        Optional.ofNullable(scheduleRepository.findById(id))
                .orElseThrow(() -> new InvalidEntityIdException("No such entity with id " + id));

        // 틀린 비밀번호
        if (!isValidPassword(id, request))
            throw new InvalidPasswordException("Invalid password");
    }

    // 생성자 주입
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 전체 조회
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAll();
    }

    // 일정 생성
    public ScheduleResponseDto createSchedule(ScheduleRequestDto request) {
        Schedule savedSchedule = scheduleRepository.save(new Schedule(request));
        return new ScheduleResponseDto(savedSchedule);
    }

    // 수정일 기준 일정 조회
    public List<ScheduleResponseDto> getSchedulesSortedByUpdateDate() {
        return scheduleRepository.findLatestUpdated();
    }

    // 담당자 id 기준 일정 조회
    public List<ScheduleResponseDto> getSchedulesByManager(int manager_id) {
        return scheduleRepository.findByManager(manager_id);
    }

    // 담당자 id & 수정일 기준 일정 조회
    public List<ScheduleResponseDto> getSchedulesSortedByUpdateDateAndManager(int manager_id) {
        return scheduleRepository.findLatestUpdatedByManager(manager_id);
    }

    // 일정 수정
    public String updateSchedule(int id, ScheduleRequestDto request) throws InvalidEntityIdException, InvalidPasswordException {
        validateSchedule(id, request);
        scheduleRepository.update(id, request);
        return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
    }

    // 일정 삭제
    public String deleteSchedule(int id, ScheduleRequestDto request) throws InvalidEntityIdException, InvalidPasswordException {
        validateSchedule(id, request);
        scheduleRepository.delete(id, request);
        return "Schedule deleted";
    }

    // 페이지네이션
    public List<ScheduleResponseDto> getSchedulesV2(int page, int size) {
        return scheduleRepository.findByPage(page, size);
    }
}
