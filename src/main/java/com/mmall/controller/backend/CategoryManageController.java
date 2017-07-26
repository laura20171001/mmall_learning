package com.mmall.controller.backend;


import com.mmall.common.Const;
import com.mmall.common.ResponceCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody

    public ServerResponse addCatogery(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.NEED_LOGIN.getCode(),"The user didn't login, please login");
        }
        //validate the user is admin or not
        if(iUserService.checkAdminRoll(user).isSuccess()){
            // is admin
            //add deal with catogery logic
            return iCategoryService.addCategory(categoryName,parentId);

        }else{
            return ServerResponse.createByErrorMessage("No access to do. Need admin access");
        }

    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCatogeryName(HttpSession session,Integer categoryId,String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.NEED_LOGIN.getCode(),"The user didn't login, please login");
        }
        //validate the user is admin or not
        if(iUserService.checkAdminRoll(user).isSuccess()){
            // is admin
            //add deal with update catogery logic
            return iCategoryService.updateCategoryName(categoryId,categoryName);

        }else{
            return ServerResponse.createByErrorMessage("No access to do. Need admin access");
        }

    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildParallelCategory(HttpSession session,@RequestParam(value="categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.NEED_LOGIN.getCode(),"The user didn't login, please login");
        }
        //validate the user is admin or not
        if(iUserService.checkAdminRoll(user).isSuccess()){
            // is admin
            //add deal with select zi jie dian category information, and no digui , keep the same level
            return iCategoryService.getChildParallelCategory(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("No access to do. Need admin access");
        }

    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildCategory(HttpSession session,@RequestParam(value="categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.NEED_LOGIN.getCode(),"The user didn't login, please login");
        }
        //validate the user is admin or not
        if(iUserService.checkAdminRoll(user).isSuccess()){
            // is admin
            //find current nodeId and recursion child nodeId
            //0->100->10000
            return iCategoryService.selectCategoryAndChildById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("No access to do. Need admin access");
        }

    }


}