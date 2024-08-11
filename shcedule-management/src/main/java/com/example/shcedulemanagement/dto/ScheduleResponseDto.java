package com.example.shcedulemanagement.dto;

import com.example.shcedulemanagement.entity.Schedule;
import lombok.Getter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
public class ScheduleResponseDto {
    private int id;
    private String to_do;
    private String manager;
    private String pw;
    private String created_at;
    private String updated_at;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.to_do = schedule.getTo_do();
        this.manager = schedule.getManager();
        this.pw = schedule.getPw();
        this.created_at = new SimpleDateFormat("yyyy-MM-dd").format(schedule.getCreated_at());
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd").format(schedule.getCreated_at());
    }

    public ScheduleResponseDto(int id, String toDo, String manager, String pw, Timestamp createAt, Timestamp updatedAt) {
        this.id = id;
        this.to_do = toDo;
        this.manager = manager;
        this.pw = pw;
        this.created_at = new SimpleDateFormat("yyyy-MM-dd").format(createAt);
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd").format(updatedAt);
    }
}
