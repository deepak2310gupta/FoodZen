package com.example.foodzen.CollectionModels;

public class ModelTopPicks {


    private String shopId, shopName, shopDiscountNoteOff, shopCategorytype, topPickid, topPickuserid;


    public ModelTopPicks() {
    }

    public ModelTopPicks(String shopId, String shopName, String shopDiscountNoteOff, String shopCategorytype, String topPickid, String topPickuserid) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopDiscountNoteOff = shopDiscountNoteOff;
        this.shopCategorytype = shopCategorytype;
        this.topPickid = topPickid;
        this.topPickuserid = topPickuserid;
    }

    public String getTopPickuserid() {
        return topPickuserid;
    }

    public void setTopPickuserid(String topPickuserid) {
        this.topPickuserid = topPickuserid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDiscountNoteOff() {
        return shopDiscountNoteOff;
    }

    public void setShopDiscountNoteOff(String shopDiscountNoteOff) {
        this.shopDiscountNoteOff = shopDiscountNoteOff;
    }

    public String getShopCategorytype() {
        return shopCategorytype;
    }

    public void setShopCategorytype(String shopCategorytype) {
        this.shopCategorytype = shopCategorytype;
    }

    public String getTopPickid() {
        return topPickid;
    }

    public void setTopPickid(String topPickid) {
        this.topPickid = topPickid;
    }
}
