package com.example.shcedulemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @NotNull
    @Size(min = 1, max = 200)
    private String to_do;

    private int manager_id;

    @NotNull
    private String pw;
}
