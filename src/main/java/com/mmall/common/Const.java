package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Const {

    public static final String CURRENT_USER="current_user";

    public static final String EMAIL="email";
    public static final String USERNAME="username";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED =1;
        int UNCHECKED =0;

        String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL" ;
        String LIMIT_NUM_SUCCESS= "LIMIT_NUM_SUCCES";
    }

    public interface Role{
        int ROLL_CUSTOMER=0;
        int ROLL_ADMIN=1;
    }

    public enum ProductStatusEnum{
        ON_SALE(1,"on sale");
        private String value;
        private int code;
        ProductStatusEnum(int code, String value){
            this.code=code;
            this.value=value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

}