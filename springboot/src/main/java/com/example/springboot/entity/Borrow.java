package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Borrow {

    private Integer id;
    private String bookName;
    private String bookNo;
    private String userNo;
    private String  userName;
    private String userPhone;

    //提醒状态
    private String note;

    private String  status;
    private Integer days;
    private LocalDate returnDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createtime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate updatetime;

    private Integer score;
}
