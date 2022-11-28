package com.example.springboot.service;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.User;

import java.util.List;

public interface ICategoryService {

    List<Category> list();

    Object page(BaseRequest baseRequest);

    void save(Category category);

    Category getById(Integer id);

    void update(Category category);

    void deleteById(Integer id);

}
