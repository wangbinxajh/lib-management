package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> list();

    List<Category> listByCondition(BaseRequest baseRequest);

    void save(Category user);

    Category getById(Integer id);

    void updateById(Category user);

    void deleteById(Integer id);
}
