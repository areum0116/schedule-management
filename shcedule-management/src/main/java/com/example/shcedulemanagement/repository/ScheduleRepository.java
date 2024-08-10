package com.example.shcedulemanagement.repository;

import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ScheduleResponseDto> findAll() {
        String sql = "select * from schedule_list";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String to_do = rs.getString("to_do");
            String manager = rs.getString("manager");
            String pw = rs.getString("pw");
            Timestamp create_at = rs.getTimestamp("created_at");
            Timestamp updated_at = rs.getTimestamp("updated_at");
            return new ScheduleResponseDto(id, to_do, manager, pw, create_at, updated_at);
        });
    }

    public Schedule save(Schedule schedule) {
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
        return schedule;
    }

    public ScheduleResponseDto findById(int id) {
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
