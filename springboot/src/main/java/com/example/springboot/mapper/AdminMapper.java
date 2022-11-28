package com.example.springboot.mapper;

import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 接口类，增删改成查的方法
 */

@Mapper
public interface AdminMapper {

//    @Select("select * from user")  写注解的方式写SQL语句，还可以通过写xml文档形式写SQL
    List<Admin> list();

    List<Admin> listByCondition(AdminPageRequest adminPageRequest);

    void save(Admin admin);

    Admin getById(Integer id);

    void updateById(Admin admin);

    void deleteById(Integer id);

    Admin getByUserAndPassword(LoginRequest request);

    Admin getByUsername(String username);
}
