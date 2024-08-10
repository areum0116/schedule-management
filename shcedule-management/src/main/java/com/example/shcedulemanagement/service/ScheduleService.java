//package com.example.shcedulemanagement.service;
//
//import com.example.shcedulemanagement.dto.ScheduleResponseDto;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//public class ScheduleService {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public ScheduleService(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    public List<ScheduleResponseDto> getSchedules() {
//        String sql = "select * from schedule";
//
//        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
//            @Override
//            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                int id = rs.getInt("id");
//                String to_do = rs.getString("to_do");
//                String manager = rs.getString("manager");
//                String pw = rs.getString("pw");
//                String create_at = rs.getString("create_at");
//                String updated_at = rs.getString("updated_at");
//                return new ScheduleResponseDto(to_do, manager, create_at, updated_at);
//            }
//        });
//    }
//}
