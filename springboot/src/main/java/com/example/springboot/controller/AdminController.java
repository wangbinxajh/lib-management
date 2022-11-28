package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;

    @GetMapping("/list")
    public Result listAdmin(){
       List<Admin> admins = adminService.list();
       return Result.success(admins) ;
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Admin admins = adminService.getById(id);
        return Result.success(admins) ;
    }

    @PostMapping("/save")
    public Result save(@RequestBody Admin admin){
        adminService.save(admin);
        return Result.success();

    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){
        LoginDTO loginDTO = adminService.login(request);
        return Result.success(loginDTO);

    }



    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        adminService.update(admin);
        return Result.success();

    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();

    }


    /**
     *
     * @param adminPageRequest
     * @return
     */
    @GetMapping("/page")
    public Result page(AdminPageRequest adminPageRequest){
        return Result.success(adminService.page(adminPageRequest));
    }
}
