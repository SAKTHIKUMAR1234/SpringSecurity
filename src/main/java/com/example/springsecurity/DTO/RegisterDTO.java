package com.example.springsecurity.DTO;


import com.example.springsecurity.Entity.Role;
import lombok.Data;

@Data
public class RegisterDTO {
    private Long Id;
    private String name;
    private String email;
    private String password;
}
