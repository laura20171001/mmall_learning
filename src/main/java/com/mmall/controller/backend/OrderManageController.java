package com.mmall.controller.backend;


import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Controller
@RequestMapping("/manage/order")
public class OrderManageController {


    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){

        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iOrderService.manageList(pageNum,pageSize);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }

    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpSession session,Long orderNo){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iOrderService.manageDetail(orderNo);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }

    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpSession session,Long orderNo, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iOrderService.manageSearch(orderNo,pageNum,pageSize);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }

    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> sendGoods(HttpSession session,Long orderNo){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iOrderService.sendGoods(orderNo);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }

    }






















}