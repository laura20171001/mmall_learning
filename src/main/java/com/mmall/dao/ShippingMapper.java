package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByShippingIdUserId(@RequestParam(value = "userId") Integer userId,@RequestParam(value = "ShippingIdUserId") Integer ShippingIdUserId);

    int updateByShipping(Shipping record);

    Shipping selectByShippingIdUserId(@RequestParam(value = "userId") Integer userId,@RequestParam(value = "ShippingIdUserId") Integer ShippingIdUserId);

    List<Shipping> selectByUserId(@RequestParam(value = "userId") Integer userId);

}