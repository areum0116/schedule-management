package com.example.shcedulemanagement.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class Manager {
    private int id;
    private String name;
    private String email;
    private Timestamp created_at;
    private Timestamp updated_at;
}
