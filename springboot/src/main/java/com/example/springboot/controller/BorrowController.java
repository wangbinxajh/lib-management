package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;
import com.example.springboot.service.IBookService;
import com.example.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    IBorrowService iBorrowService;


    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Borrow borrow = iBorrowService.getById(id);
        return Result.success(borrow) ;
    }

    @GetMapping("/list")
    public Result listAdmin(){
        List<Borrow> categories = iBorrowService.list();
        return Result.success(categories) ;
    }

    @PostMapping("/save")
    public Result save(@RequestBody Borrow borrow){
        iBorrowService.save(borrow);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Borrow borrow){
        iBorrowService.update(borrow);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        iBorrowService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/page")
    public Result page(BorrowPageRequest borrowPageRequest){
        return Result.success(iBorrowService.page(borrowPageRequest));
    }

    @GetMapping("/pageRetur")
    public Result pageRetur(BorrowPageRequest borrowPageRequest){
        return Result.success(iBorrowService.pageRetur(borrowPageRequest));
    }

    @PostMapping("/saveRetur")
    public Result saveRetur(@RequestBody Retur obj){
        iBorrowService.saveRetur(obj);
        return Result.success();
    }

    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange){
        return Result.success(iBorrowService.getCountByTimeRange(timeRange));
    }
}
