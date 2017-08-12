package com.mmall.dao;

import com.mmall.pojo.Order;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndOrderNo(@RequestParam("userId") Integer userId, @RequestParam("orderNo") Long orderNo);

    Order selectByOrderNo( @RequestParam("orderNo") Long orderNo);

    List<Order> selectByUserId (@RequestParam("userId") Integer userId);

    List<Order> selectAllOrder();



}