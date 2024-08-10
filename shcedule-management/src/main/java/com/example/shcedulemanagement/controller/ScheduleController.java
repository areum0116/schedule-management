package com.example.shcedulemanagement.controller;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;

@RestController
public class ScheduleController {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getScedules() {
        // DB 조회
        String sql = "select * from schedule_list";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String to_do = rs.getString("to_do");
                String manager = rs.getString("manager");
                String pw = rs.getString("pw");
                Timestamp create_at = rs.getTimestamp("created_at");
                Timestamp updated_at = rs.getTimestamp("updated_at");
                return new ScheduleResponseDto(id, to_do, manager, pw, create_at, updated_at);
            }
        });
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto request) {
        Schedule schedule = new Schedule(request);
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into schedule_list (to_do, manager, pw) values (?, ?, ?)";
        jdbcTemplate.update( con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, schedule.getTo_do());
            preparedStatement.setString(2, schedule.getManager());
            preparedStatement.setString(3, schedule.getPw());
            return preparedStatement;
        },
        keyHolder);

        int id = keyHolder.getKey().intValue();
        schedule.setId(id);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto findById(@PathVariable int id) {
        String sql = "select * from schedule_list where id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setTo_do(resultSet.getString("to_do"));
                schedule.setManager(resultSet.getString("manager"));
                schedule.setPw(resultSet.getString("pw"));
                schedule.setCreated_at(resultSet.getTimestamp("created_at"));
                schedule.setUpdated_at(resultSet.getTimestamp("updated_at"));
                return new ScheduleResponseDto(schedule);
            } else {
                return null;
            }
        }, id);

    }

}
