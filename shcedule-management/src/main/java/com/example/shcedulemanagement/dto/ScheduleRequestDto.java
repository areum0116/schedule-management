package com.example.shcedulemanagement.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ScheduleRequestDto {
    private String to_do;
    private String manager;
    private String pw;
    private Timestamp created_at;
    private Timestamp updated_at;
}
