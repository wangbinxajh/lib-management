package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
public class BookService implements IBookService {

    @Resource
    BookMapper bookMapper;


    @Override
    public List<Book> list() {
        return bookMapper.list();
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
        List<Book> books = bookMapper.listByCondition(baseRequest);
        return new PageInfo<>(books);

    }

    @Override
    public void save(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            bookMapper.save(book);
        }catch (Exception e){
            log.error("数据插入错误",e);
            throw  new ServiceException("数据插入错误");
        }


    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void update(Book book) {
        book.setCategory(category(book.getCategories()));
        book.setUpdatetime(LocalDate.now());
        bookMapper.updateById(book);
    }

    private String category(List<String> categories){
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)){
            categories.forEach(v ->sb.append(v).append(">"));
            return sb.substring(0,sb.lastIndexOf(">"));
        }
        return sb.toString();
    }


    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

}
