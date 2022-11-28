package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import java.util.List;

public interface IBookService {

    List<Book> list();

    Object page(BaseRequest baseRequest);

    void save(Book book);

    Book getById(Integer id);

    void update(Book book);

    void deleteById(Integer id);

}
