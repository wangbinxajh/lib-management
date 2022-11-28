package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 聚合业务写一写方法
 */

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASS = "123";
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    /**
     * PageHelper 分页插件
     *  new PageInfo<> 
     * @param adminPageRequest
     * @return
     */
    @Override
    public Object page (AdminPageRequest adminPageRequest) {
        PageHelper.startPage(adminPageRequest.getPageNum(),adminPageRequest.getPageSize());
        List<Admin> users = adminMapper.listByCondition(adminPageRequest);
        return new PageInfo<>(users);

    }

    @Override
    public void save(Admin admin) {
        Date date = new Date();
        //MD5加密
        if (StrUtil.isBlank(admin.getPassword())){
            admin.setPassword(DEFAULT_PASS);
        }
        admin.setPassword(SecureUtil.md5(admin.getPassword()));

        try {
            adminMapper.getByUsername(admin.getUsername());
        }catch (DuplicateKeyException e){
            log.error("数据插入失败,：",admin.getUsername(),e);
            throw new ServiceException("用户名重复");
        }

        adminMapper.save(admin);
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request) {
        request.setPassword(SecureUtil.md5(request.getPassword()));
        Admin admin = null;
        try {
            admin =  adminMapper.getByUsername(request.getUsername());
        }catch (Exception e){
            log.error("根据用户名{} 查询出错，",request.getUsername());
            throw new ServiceException("用户名错误");
        }

        admin = adminMapper.getByUserAndPassword(request);
        if(admin == null){
            throw new ServiceException("用户名密码错误");
        }


        if(!admin.isStatus()){
            throw new ServiceException("当前用户是被禁用状态请联系管理员");
        }
        LoginDTO  loginDTO = new LoginDTO();
        //查询出来的值是admin对象里面的，如何赋值给dto,就需要用copyProperties方法，把admin里面的值给loginDTO
        BeanUtils.copyProperties(admin,loginDTO);
        String token = TokenUtils.genToken(String.valueOf(admin.getId()),admin.getPassword());
        loginDTO.setToken(token);
        return loginDTO;

    }

}
