package com.example.springboot.mapper;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 接口类，增删改成查的方法
 */

@Mapper
public interface UserMapper {

//    @Select("select * from user")  写注解的方式写SQL语句，还可以通过写xml文档形式写SQL
    List<User> list();

    List<User> listByCondition(UserPageRequest userPageRequest);

    void save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);
}
