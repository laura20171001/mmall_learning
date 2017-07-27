package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponceCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("iProductService")

public class ProductServiceImpl implements IProductService{



    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdateProduct(Product product){
        if(product !=null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if(product.getId() !=null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount>0){
                    return ServerResponse.createBySuccess("Update product success!");
                }
                return ServerResponse.createByErrorMessage("Update product failed!");
            }
            else{
                int rowCount =  productMapper.insert(product);
                if(rowCount>0){
                    return ServerResponse.createBySuccess("Insert new product success!");
                }
                return ServerResponse.createByErrorMessage("Insert new product failed!");
            }
        }else{
            return ServerResponse.createByErrorMessage("Insert or Update product failed.");
        }
    }

    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if(productId ==null || status ==null){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.ILLEGAL_ARGUMENT.getCode(),ResponceCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount=productMapper.updateByPrimaryKeySelective(product);
        if(rowCount>0){
            return ServerResponse.createBySuccess("Change the product status success.");
        }

        return ServerResponse.createByErrorMessage("Change the product status failed.");

    }

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId ==null){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.ILLEGAL_ARGUMENT.getCode(),ResponceCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product =productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByErrorMessage("The product is not on sale.");
        }
        //VO- Value Object
        //pojo->bo->vo
        ProductDetailVo productDetailVo =assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo =new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImage(product.getSubImages());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        //imageHost
        //parentCategoryId
        //createTime
        //updateTime

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category==null){
            productDetailVo.setParentCategoryId(0); //default root node
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTIme(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;

    }


    public ServerResponse getProductList (int pageNum, int pageSize){
        //startPge -- start
        //fill your sql logic
        //pageHelper end
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();

        List<ProductListVo> productListVoList = Lists.newArrayList();

        for (Product productItem: productList){
            ProductListVo productListVo = assembleProductListvo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);

    }

    private ProductListVo assembleProductListvo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());
        return productListVo;
    }

    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId, int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNoneBlank(productName)){
            productName=new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();

        for (Product productItem: productList){
            ProductListVo productListVo = assembleProductListvo(productItem);
            productListVoList.add(productListVo);
        }

        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        if(productId ==null){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.ILLEGAL_ARGUMENT.getCode(),ResponceCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product =productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByErrorMessage("The product is not on sale.");
        }
        //VO- Value Object
        //pojo->bo->vo
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("The product is not on sale.");
        }
        ProductDetailVo productDetailVo =assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null ){
            return ServerResponse.createByErrorCodeMessage(ResponceCode.ILLEGAL_ARGUMENT.getCode(),ResponceCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList= new ArrayList<Integer>();
        if(categoryId != null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if(category==null && StringUtils.isBlank(keyword)){
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo= new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildById(category.getId()).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);

        if (StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray=orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+""+orderByArray[1]);
            }
    }

    List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword, categoryIdList.size()==0?null:categoryIdList);
    List<ProductListVo> productListVoList = Lists.newArrayList();
    for(Product product:productList){
        ProductListVo productListVo=assembleProductListvo(product);
        productListVoList.add(productListVo);
    }

    PageInfo pageInfo = new PageInfo(productList);
    pageInfo.setList(productListVoList);
    return ServerResponse.createBySuccess(pageInfo);
}


}