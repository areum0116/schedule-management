package com.example.shcedulemanagement.repository;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    // 입력받은 sql 기준 Dto로 변환 후 리스트로 반환
    private List<ScheduleResponseDto> getScheduleResponseDtos(String sql) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String to_do = rs.getString("to_do");
            int manager_id = rs.getInt("manager_id");
            Timestamp created_at = rs.getTimestamp("created_at");
            Timestamp updated_at = rs.getTimestamp("updated_at");
            return new ScheduleResponseDto(id, to_do, manager_id, created_at, updated_at);
        });
    }

    // ResultSet 있다면 해당 데이터로 Dto 생성 후 반환
    private ScheduleResponseDto getScheduleResponseDto(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getInt("id"));
            schedule.setTo_do(rs.getString("to_do"));
            schedule.setManager_id(rs.getInt("manager_id"));
            schedule.setCreated_at(rs.getTimestamp("created_at"));
            schedule.setUpdated_at(rs.getTimestamp("updated_at"));
            return new ScheduleResponseDto(schedule);
        } else {
            return null;
        }
    }

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ScheduleResponseDto> findAll() {
        String sql = "select * from schedule_list";
        return getScheduleResponseDtos(sql);
    }

    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into schedule_list (to_do, manager_id, pw) values (?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, schedule.getTo_do());
                    preparedStatement.setInt(2, schedule.getManager_id());
                    preparedStatement.setString(3, schedule.getPw());
                    return preparedStatement;
                },
                keyHolder);

        int id = keyHolder.getKey().intValue();
        schedule.setId(id);
        schedule.setCreated_at(new Timestamp(System.currentTimeMillis()));
        schedule.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return schedule;
    }

    public ScheduleResponseDto findById(int id) {
        String sql = "select * from schedule_list where id = ?";
        return jdbcTemplate.query(sql, this::getScheduleResponseDto, id);
    }

    // 수정일 기준 내림차순 정렬 결과 리스트 반환
    public List<ScheduleResponseDto> findLatestUpdated() {
        String sql = "select * from schedule_list order by updated_at desc";
        return getScheduleResponseDtos(sql);
    }

    // 담당자명 기준 조건 리스트 반환
    public List<ScheduleResponseDto> findByManager(int manager_id) {
        String sql = "select * from schedule_list where manager_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String to_do = rs.getString("to_do");
            Timestamp created_at = rs.getTimestamp("created_at");
            Timestamp updated_at = rs.getTimestamp("updated_at");
            return new ScheduleResponseDto(id, to_do, manager_id, created_at, updated_at);
        }, manager_id);
    }

    // 수정일, 담담자명 기준
    public List<ScheduleResponseDto> findLatestUpdatedByManager(int manager_id) {
        String sql = "select * from schedule_list where manager_id = ? order by updated_at desc";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String to_do = rs.getString("to_do");
            Timestamp created_at = rs.getTimestamp("created_at");
            Timestamp updated_at = rs.getTimestamp("updated_at");
            return new ScheduleResponseDto(id, to_do, manager_id, created_at, updated_at);
        }, manager_id);
    }

    public void update(int id, ScheduleRequestDto request) {
        String sql = "update schedule_list set to_do = ?, manager_id = ?, updated_at = ? where id = ? and pw = ?";

        jdbcTemplate.update(sql, request.getTo_do(), request.getManager_id(),
                new Timestamp(System.currentTimeMillis()), id, request.getPw());
    }

    public String getPw(int id) {
        String sql = "select pw from schedule_list where id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getString("pw");
            } else {
                return null;
            }
        }, id);
    }

    public void delete(int id, ScheduleRequestDto request) {
        String sql = "delete from schedule_list where id = ? and pw = ?";
        jdbcTemplate.update(sql, id, request.getPw());
    }
}
