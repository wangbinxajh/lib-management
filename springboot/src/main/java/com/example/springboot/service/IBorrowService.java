package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;

import java.util.List;
import java.util.Map;

public interface IBorrowService {

    List<Borrow> list();

    Object page(BaseRequest baseRequest);

    Object pageRetur(BaseRequest baseRequest);

    void save(Borrow borrow);

    void saveRetur(Retur borrow);

    Borrow getById(Integer id);

    void update(Borrow borrow);

    void deleteById(Integer id);

    Map<String,Object> getCountByTimeRange(String timeRange);
}
