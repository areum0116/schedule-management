package com.example.shcedulemanagement.dto;

import com.example.shcedulemanagement.entity.Manager;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ManagerResponseDto {
    private int id;
    private String name;
    private String email;
    private Timestamp create_at;
    private Timestamp update_at;

    public ManagerResponseDto(Manager manager) {
        this.id = manager.getId();
        this.name = manager.getName();
        this.email = manager.getEmail();
        this.create_at = manager.getCreated_at();
        this.update_at = manager.getUpdated_at();
    }
}
