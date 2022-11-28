package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Book {

    private Integer id;
    private String name;
    private String description;
    private String publishDate;
    private String author;
    private String publisher;
    private String category;
    private String bookNo;
    private String cover;
    private Integer nums;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createtime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate updatetime;


    private List<String> categories;
}
