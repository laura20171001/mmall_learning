package com.mmall.controller.backend;


import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
           return iProductService.saveOrUpdateProduct(product);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }

    }


    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iProductService.setSaleStatus(productId,status);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }
    }


    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iProductService.manageProductDetail(productId);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue ="10") int pageSize){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iProductService.getProductList(pageNum,pageSize);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }
    }


    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue ="10") int pageSize){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            //add product logic
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);

        }else {
            return ServerResponse.createByErrorMessage("No admin access");
        }
    }


}





