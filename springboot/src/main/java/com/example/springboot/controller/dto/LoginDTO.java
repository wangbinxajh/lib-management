package com.example.springboot.controller.dto;


import lombok.Data;

@Data
public class LoginDTO {

    private Integer id;
    private String email;
    private String username;
    private String phone;
    private String token;
}
