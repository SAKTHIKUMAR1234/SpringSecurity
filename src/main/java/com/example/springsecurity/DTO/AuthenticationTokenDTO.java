package com.example.springsecurity.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationTokenDTO {

    private String token;
}
