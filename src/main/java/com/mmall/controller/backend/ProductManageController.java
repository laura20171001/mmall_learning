package com.mmall.controller.backend;


import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS.optional;

@Controller
@RequestMapping
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

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

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("The user didn't login, please login as admin");
        }
        if (iUserService.checkAdminRoll(user).isSuccess()){
        String path=request.getSession().getServletContext().getRealPath("upload");
        String targetFilename = iFileService.upload(file,path);
        String url= PropertiesUtil.getProperty("ftp.server.http.prefix"+targetFilename);

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFilename);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);

        }else {
            return ServerResponse.createByErrorMessage("No access");
        }

    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap=Maps.newHashMap();
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success",false);
            resultMap.put("msg","please login as admin");
            return resultMap;
        }

        //richtext need below return result
       //  {
        // "success": true / false ,
       //     "msg":"error message", #optional
       //         "file_path":"[real_file_path]";
       // }
        if (iUserService.checkAdminRoll(user).isSuccess()){
            String path=request.getSession().getServletContext().getRealPath("upload");
            String targetFilename = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFilename)){
                resultMap.put("success",false);
                resultMap.put("msg","failed to upload");
                return resultMap;

            }

            String url= PropertiesUtil.getProperty("ftp.server.http.prefix"+targetFilename);

            resultMap.put("success",true);
            resultMap.put("msg","uploaded success");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;

        }else {
            resultMap.put("success",false);
            resultMap.put("msg","No admin access");
            return resultMap;
        }

    }


}





