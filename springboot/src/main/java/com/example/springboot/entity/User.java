package com.example.springboot.entity;

import cn.hutool.db.DaoTemplate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.lang.model.element.NestingKind;
import java.util.Date;

/**
 * 和数据库表一一对应
 * 数据库表的实体类
 */
@Data
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private String username;
    private String phone;
    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updatetime;
}
