package com.example.foodzen.CollectionModels;

public class ModelAddProducts {


    public String pId, pName, pDesc, oriPrice, itemType, productimage,discountPrice, discontNote, productUserId;


    public ModelAddProducts() {
    }

    public ModelAddProducts(String pId, String pName, String pDesc, String oriPrice, String itemType, String productimage, String discountPrice, String discontNote, String productUserId) {
        this.pId = pId;
        this.pName = pName;
        this.pDesc = pDesc;
        this.oriPrice = oriPrice;
        this.itemType = itemType;
        this.productimage = productimage;
        this.discountPrice = discountPrice;
        this.discontNote = discontNote;
        this.productUserId = productUserId;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(String oriPrice) {
        this.oriPrice = oriPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscontNote() {
        return discontNote;
    }

    public void setDiscontNote(String discontNote) {
        this.discontNote = discontNote;
    }

    public String getProductUserId() {
        return productUserId;
    }

    public void setProductUserId(String productUserId) {
        this.productUserId = productUserId;
    }
}
