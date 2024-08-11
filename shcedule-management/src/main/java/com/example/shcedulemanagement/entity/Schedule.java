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
    private String manager;
    private String pw;
    private Timestamp created_at;
    private Timestamp updated_at;


    public Schedule(ScheduleRequestDto request) {
        this.to_do = request.getTo_do();
        this.manager = request.getManager();
        this.pw = request.getPw();
        this.created_at = request.getCreated_at();
        this.updated_at = request.getUpdated_at();
    }
}
