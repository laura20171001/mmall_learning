package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

import java.util.Map;

public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse alicallback(Map<String, String > params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse<String> cancel(Integer userId, long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);
    ServerResponse<OrderVo> manageDetail(long orderNo);
    ServerResponse<PageInfo> manageSearch(long orderNo, int pageNum, int pageSize);
    ServerResponse<String> sendGoods(long orderNo);

}