package com.example.foodzen.CollectionModels;

public class ModelCartItems {


    private String foodid,foodPid,foodPName,foodUserName,foodTotalOriginalPrice,foodQuantity,foodTotalDiscountedPrice,foodTotalPrice;

    public ModelCartItems() {
    }

    public ModelCartItems(String foodid, String foodPid, String foodPName, String foodUserName, String foodTotalOriginalPrice, String foodQuantity, String foodTotalDiscountedPrice, String foodTotalPrice) {
        this.foodid = foodid;
        this.foodPid = foodPid;
        this.foodPName = foodPName;
        this.foodUserName = foodUserName;
        this.foodTotalOriginalPrice = foodTotalOriginalPrice;
        this.foodQuantity = foodQuantity;
        this.foodTotalDiscountedPrice = foodTotalDiscountedPrice;
        this.foodTotalPrice = foodTotalPrice;
    }


    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getFoodPid() {
        return foodPid;
    }

    public void setFoodPid(String foodPid) {
        this.foodPid = foodPid;
    }

    public String getFoodPName() {
        return foodPName;
    }

    public void setFoodPName(String foodPName) {
        this.foodPName = foodPName;
    }

    public String getFoodUserName() {
        return foodUserName;
    }

    public void setFoodUserName(String foodUserName) {
        this.foodUserName = foodUserName;
    }

    public String getFoodTotalOriginalPrice() {
        return foodTotalOriginalPrice;
    }

    public void setFoodTotalOriginalPrice(String foodTotalOriginalPrice) {
        this.foodTotalOriginalPrice = foodTotalOriginalPrice;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodTotalDiscountedPrice() {
        return foodTotalDiscountedPrice;
    }

    public void setFoodTotalDiscountedPrice(String foodTotalDiscountedPrice) {
        this.foodTotalDiscountedPrice = foodTotalDiscountedPrice;
    }

    public String getFoodTotalPrice() {
        return foodTotalPrice;
    }

    public void setFoodTotalPrice(String foodTotalPrice) {
        this.foodTotalPrice = foodTotalPrice;
    }
}
