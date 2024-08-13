package com.example.shcedulemanagement.repository;

import com.example.shcedulemanagement.dto.ManagerResponseDto;
import com.example.shcedulemanagement.dto.ScheduleResponseDto;
import com.example.shcedulemanagement.entity.Manager;
import com.example.shcedulemanagement.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Manager save(Manager manager) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into manager (name, email) values (?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, manager.getName());
                    preparedStatement.setString(2, manager.getEmail());
                    return preparedStatement;
                },
                keyHolder);

        int id = keyHolder.getKey().intValue();
        manager.setId(id);
        manager.setCreated_at(new Timestamp(System.currentTimeMillis()));
        manager.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return manager;
    }

    public List<ManagerResponseDto> findAll() {
        String sql = "select * from manager";

        return jdbcTemplate.query(sql, new RowMapper<ManagerResponseDto>() {
           @Override
           public ManagerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
               Manager manager = new Manager();
               manager.setId(rs.getInt("id"));
               manager.setName(rs.getString("name"));
               manager.setEmail(rs.getString("email"));
               manager.setCreated_at(rs.getTimestamp("created_at"));
               manager.setUpdated_at(rs.getTimestamp("updated_at"));
               return new ManagerResponseDto(manager);
           }
        });
    }
}
