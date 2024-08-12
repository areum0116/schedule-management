package com.example.shcedulemanagement.entity;

import com.example.shcedulemanagement.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    private int id;
    private String to_do;
    private int manager_id;
    private String pw;
    private Timestamp created_at;
    private Timestamp updated_at;


    public Schedule(ScheduleRequestDto request) {
        this.to_do = request.getTo_do();
        this.manager_id = request.getManager_id();
        this.pw = request.getPw();
    }
}
