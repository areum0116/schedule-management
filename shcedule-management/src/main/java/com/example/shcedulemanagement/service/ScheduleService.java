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

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 입력받은 비밀번호 검사
    private boolean checkValidPw(int id, ScheduleRequestDto request) {
        return scheduleRepository.getPw(id) != null && scheduleRepository.getPw(id).equals(request.getPw());
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
        if (scheduleRepository.findById(id) == null) {  // 존재하지 않는 id
            throw new InvalidEntityIdException("No such entity with id " + id);
        } else if (!checkValidPw(id, request)) {    // 틀린 비밀번호
            throw new InvalidPasswordException("Invalid password");
        } else {
            scheduleRepository.update(id, request);
            return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        }
    }

    // 일정 삭제
    public String deleteSchedule(int id, ScheduleRequestDto request) throws InvalidEntityIdException, InvalidPasswordException {
        if (scheduleRepository.findById(id) == null) {  // 존재하지 않는 id
            throw new InvalidEntityIdException("No such entity with id " + id);
        } else if (!checkValidPw(id, request)) {    // 틀린 비밀번호
            throw new InvalidPasswordException("Invalid password");
        } else {
            scheduleRepository.delete(id, request);
            return "Schedule deleted";
        }
    }

    // 페이지네이션
    public List<ScheduleResponseDto> getSchedulesV2(int page, int size) {
        return scheduleRepository.findByPage(page, size);
    }
}
