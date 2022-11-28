package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 和数据库表一一对应
 * 管理员表
 */
@Data
public class Admin {

        private Integer id;
        private String email;
        private String username;
        private String phone;
        private String password;
        private boolean status;

        @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
        private Date createtime;

        @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
        private Date updatetime;
}


