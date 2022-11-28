package com.example.springboot.service;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;

import java.util.List;

public interface IAdminService {
    List<Admin> list();

    Object page(AdminPageRequest adminPageRequest);

    void save(Admin admin);

    Admin getById(Integer id);

    void update(Admin admin);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest request);
}
