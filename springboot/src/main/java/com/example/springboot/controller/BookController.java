package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Category;
import com.example.springboot.service.IBookService;
import com.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService iBookService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Book book = iBookService.getById(id);
        return Result.success(book) ;
    }

    @GetMapping("/list")
    public Result listAdmin(){
        List<Book> categories = iBookService.list();
        return Result.success(categories) ;
    }

    @PostMapping("/save")
    public Result save(@RequestBody Book book){
        iBookService.save(book);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Book book){
        iBookService.update(book);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        iBookService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result page(BookPageRequest bookPageRequest){
        return Result.success(iBookService.page(bookPageRequest));
    }

}
