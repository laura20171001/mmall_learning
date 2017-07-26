package com.mmall.service.impl;

import ch.qos.logback.classic.Logger;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;


@Service("iCategoryService")
public class CategoryServiceimpl implements ICategoryService {

        private org.slf4j.Logger logger= LoggerFactory.getLogger(CategoryServiceimpl.class);


        @Autowired
        private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName,Integer parentId){
        if(parentId==null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("Add category wrong.");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount=categoryMapper.insert(category);
        if(rowCount>0){
            return ServerResponse.createBySuccess("Add category success !");
        }

        return ServerResponse.createByErrorMessage("Add category failed ");

    }

    public ServerResponse updateCategoryName(Integer categoryId,String categoryName){
        if(categoryId==null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("Update category name wrong.");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount=categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount>0){
            return ServerResponse.createBySuccess("Update category name success !");
        }

        return ServerResponse.createByErrorMessage("Update category name failed ! ");
    }

    public ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("failed to find current category 's child");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse selectCategoryAndChildById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    //cursion algorithm, to find child node
   private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId){
        Category category =categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        // select child node, cursion algorithm must have a exit condition
       List<Category> categoryList=categoryMapper.selectCategoryChildByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
   }
}