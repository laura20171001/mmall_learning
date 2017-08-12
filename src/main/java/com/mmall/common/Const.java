package com.mmall.common;

import com.google.common.collect.Sets;
import com.mmall.pojo.Order;

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

    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY="TRADE_STATUS_WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS="TRADE_STATUS_TRADE_SUCCESS";

        String RESPONSE_SUCCESS="SUCCESS";
        String RESPONSE_FAILED="FAILED";
    }

    public enum OrderStatusEnum{
        CANCELLED(0,"CANCELLED"),
        NO_PAY(10,"NO_PAY"),
        PAID(20,"PAID"),
        SHIPPED(40,"SHIPPED"),
        ORDER_SUCCESS(50,"ORDER_SUCCESS"),
        ORDER_CLOSE(60,"ORDER_CLOSE");

        private int code;
        private String value;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
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

        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum :values()) {
                if(orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("Not find this enum");
        }

    }

    public enum PayPlatformEnum{
        ALIPAY(1,"ALIPAY");

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private int code;
        private String value;

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
    }



    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"AONLINE_PAY");

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private int code;
        private String value;

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum :values()) {
                if(paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("Not find this enum");
        }
    }






}