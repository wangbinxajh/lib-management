package com.example.springboot.controller.request;

import lombok.Data;

/**
 * 这个类是从前端获取数据的类
 */
@Data
public class LoginRequest {

    private String username;
    private String password;


}
