package com.example.shcedulemanagement.dto;

import com.example.shcedulemanagement.entity.Schedule;
import lombok.Getter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
public class ScheduleResponseDto {
    private int id;
    private String to_do;
    private int manager_id;
    private String manager_name;
    private String created_at;
    private String updated_at;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.to_do = schedule.getTo_do();
        this.manager_id = schedule.getManager_id();
        this.created_at = new SimpleDateFormat("yyyy-MM-dd").format(schedule.getCreated_at());
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd").format(schedule.getCreated_at());
    }

    public ScheduleResponseDto(int id, String toDo, int manager_id, Timestamp createAt, Timestamp updatedAt) {
        this.id = id;
        this.to_do = toDo;
        this.manager_id = manager_id;
        this.created_at = new SimpleDateFormat("yyyy-MM-dd").format(createAt);
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd").format(updatedAt);
    }
}
