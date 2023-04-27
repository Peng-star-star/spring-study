package com.example.demo.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("T_USER")
public class User {
    
    private @Id Long id;
    private String name;
    private String lastname;
}
