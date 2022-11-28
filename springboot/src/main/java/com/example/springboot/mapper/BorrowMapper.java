package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;
import com.example.springboot.mapper.po.BorrowReturCountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowMapper {
    List<Borrow> list();

    List<Borrow> listByCondition(BaseRequest baseRequest);

    List<Retur> listReturByCondition(BaseRequest baseRequest);

    List<BorrowReturCountPO>getCountByTimeRange(@Param("timeRange") String timeRange, @Param("type") int type);

    void save(Borrow borrow);

    void saveRetur(Retur borrow);

    Borrow getById(Integer id);

    void updateById(Borrow borrow);

    void deleteById(Integer id);

    void updateStatus(String  status, Integer id);
}
