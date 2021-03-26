package com.example.foodzen.CollectionModels;

public class ModelFoodItem {

    private String foodid,foodname,fooddesc,foodoriginalprice,foodimage,fooddiscountedprice,foodtype,fooduserid;

    public ModelFoodItem() {
    }

    public ModelFoodItem(String foodid, String foodname, String fooddesc, String foodoriginalprice, String foodimage, String fooddiscountedprice, String foodtype, String fooduserid) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.fooddesc = fooddesc;
        this.foodoriginalprice = foodoriginalprice;
        this.foodimage = foodimage;
        this.fooddiscountedprice = fooddiscountedprice;
        this.foodtype = foodtype;
        this.fooduserid = fooduserid;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFooddesc() {
        return fooddesc;
    }

    public void setFooddesc(String fooddesc) {
        this.fooddesc = fooddesc;
    }

    public String getFoodoriginalprice() {
        return foodoriginalprice;
    }

    public void setFoodoriginalprice(String foodoriginalprice) {
        this.foodoriginalprice = foodoriginalprice;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getFooddiscountedprice() {
        return fooddiscountedprice;
    }

    public void setFooddiscountedprice(String fooddiscountedprice) {
        this.fooddiscountedprice = fooddiscountedprice;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getFooduserid() {
        return fooduserid;
    }

    public void setFooduserid(String fooduserid) {
        this.fooduserid = fooduserid;
    }
}
