package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.mapper.po.BorrowReturCountPO;
import com.example.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * service 中就是写业务的
 */

@Service
@Slf4j
public class BorrowService implements IBorrowService {

    /**
     * PageHelper 分页插件
     *  new PageInfo<>
     * @param
     * @return
     */

    @Resource
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;


    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }
    @Override
    public Object page (BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);
        for (Borrow borrow : borrows){
            LocalDate returnDate = borrow.getReturnDate();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(returnDate)){
                borrow.setNote("即将到期");
            }else if (now.isEqual(returnDate)){
                borrow.setNote("已经到期");
            }else if (now.isAfter(returnDate)){
                borrow.setNote("已过期");
            }else {
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }

    @Override
    public Object pageRetur(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        return new PageInfo<>(borrowMapper.listReturByCondition(baseRequest));

    }

    // @Transactional  添加事物保证如果执行全部执行，否则全都回滚
    @Transactional
    @Override
    public void save(Borrow borrow) {
        //查询图书数量是否有剩余
        Book book = bookMapper.getByNo(borrow.getBookNo());
        if (Objects.isNull(book)){
                throw  new ServiceException("所借图书不存在");
        }
        if (book.getNums() < 1){
            throw  new ServiceException("所借图书库存不足");
        }
        //更新图书库存
        book.setNums(book.getNums()-1);
        borrow.setReturnDate(LocalDate.now().plus(borrow.getDays(), ChronoUnit.DAYS)); //当前日期+借书天数 = 归还日期
        bookMapper.updateById(book);
        //图书状态
        borrow.setStatus("已借出");

        borrow.setCreatetime(LocalDate.now());
        borrowMapper.save(borrow);
    }

    @Transactional
    @Override
    public void saveRetur(Retur obj) {
        // 改变状态
            obj.setStatus("已归还");
            borrowMapper.updateStatus("已归还",obj.getId());
        //修改图书数量
        obj.setRealDate(LocalDate.now());
        obj.setId(null); // 还书的ID和借书ID要区分开，还书不等于借书的ID，
        obj.setCreatetime(LocalDate.now());
        borrowMapper.saveRetur(obj);
        bookMapper.updateNumByNo(obj.getBookNo());
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow borrow) {
        //borrow.setCategory(category(borrow.getCategories()));
        borrow.setUpdatetime(LocalDate.now());
        borrowMapper.updateById(borrow);
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
        borrowMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String,Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange){
            case "week":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-6),today, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-29),today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-59),today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-89),today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("data",dateStrRange);
        List<BorrowReturCountPO> borrowCount = borrowMapper.getCountByTimeRange(timeRange,1);
        List<BorrowReturCountPO> returCount = borrowMapper.getCountByTimeRange(timeRange,2);
        map.put("borrow",countList(borrowCount,dateStrRange));
        map.put("retur",countList(returCount,dateStrRange));
        return map;
    }


    private List<Integer> countList (List<BorrowReturCountPO> countPOList, List<String> dateRange){
        List<Integer> list =  CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)){
            return list;
        }
        for (String date : dateRange){
            Integer count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate())).map(BorrowReturCountPO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }

    private List<String> datetimeToDateStr(List<DateTime> dateTimeList){
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)){
                return list;
        }
        for (DateTime dateTime : dateTimeList){
            String  date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }




}
