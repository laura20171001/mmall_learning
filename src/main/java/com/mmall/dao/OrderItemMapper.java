package com.mmall.dao;

import com.mmall.pojo.Order;
import com.mmall.pojo.OrderItem;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByUserIdOrderNo( @RequestParam("orderNo") Long orderNo,@RequestParam("userId") Integer userId);

    List<OrderItem> getByOrderNo( @RequestParam("orderNo") Long orderNo);


    void batchInsert( @RequestParam("orderItemList")List<OrderItem> orderItemList);
}