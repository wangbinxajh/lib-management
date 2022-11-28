package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.ICategoryService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class CategoryService implements ICategoryService {

    @Resource
    CategoryMapper categoryMapper;


    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    /**
     * PageHelper 分页插件
     *  new PageInfo<>
     * @param
     * @return
     */
    @Override
    public Object page (BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Category> categories = categoryMapper.listByCondition(baseRequest);
        return new PageInfo<>(categories);

    }

    @Override
    public void save(Category category) {
       categoryMapper.save(category);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdatetime(LocalDate.now());
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

}
