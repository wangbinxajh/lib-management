package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.caller.CallerUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category category = iCategoryService.getById(id);
        return Result.success(category) ;
    }

    @GetMapping("/list")
    public Result listAdmin(){
        List<Category> categories = iCategoryService.list();
        return Result.success(categories) ;
    }

    @GetMapping("/tree")
    public Result tree(){
        List<Category> list = iCategoryService.list();
       // List<Category> treeList = categories.stream().filter(v->v.getPid() == null).collect(Collectors.toList());
        return Result.success(createTree(null,list)) ;
    }

    /**
     * 递归生成菜单树
     */
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories) {
            if (pid == null) {
                if (category.getPid() == null){
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(),categories));
                }
            }else {
                if (pid.equals(category.getPid())){
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())){
                    category.setChildren(null);
            }
        }
        return treeList;
    }

    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        iCategoryService.save(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        iCategoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        iCategoryService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest){
        return Result.success(iCategoryService.page(categoryPageRequest));
    }

}
