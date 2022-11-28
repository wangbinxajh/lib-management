package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/list")
    public Result listUsers(){
       List<User> users = iUserService.list();
       return Result.success(users) ;
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        User users = iUserService.getById(id);
        return Result.success(users) ;
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        iUserService.save(user);
        return Result.success();

    }


    @PutMapping("/update")
    public Result update(@RequestBody User user){
        iUserService.update(user);
        return Result.success();

    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        iUserService.deleteById(id);
        return Result.success();

    }


    /**
     *
     * @param userPageRequest
     * @return
     */
    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest){
        return Result.success(iUserService.page(userPageRequest));
    }
}
