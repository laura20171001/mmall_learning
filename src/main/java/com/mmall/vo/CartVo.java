package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVo {
    private List<CartProductVo> cartProductVolist;
    private BigDecimal cartTotalPrice;

    public List<CartProductVo> getCartProductVolist() {
        return cartProductVolist;
    }

    public void setCartProductVolist(List<CartProductVo> cartProductVolist) {
        this.cartProductVolist = cartProductVolist;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHosts() {
        return imageHosts;
    }

    public void setImageHosts(String imageHosts) {
        this.imageHosts = imageHosts;
    }

    private Boolean allChecked;
    private String imageHosts;


}