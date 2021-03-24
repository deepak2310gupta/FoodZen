package com.example.foodzen.CollectionModels;

public class ModelFoodItem {

    private String foodId,foodName,foodDesc,foodOriginalPrice,foodImage,foodDiscountedPrice,foodType,foodUserid;


    public ModelFoodItem(String foodId, String foodName, String foodDesc, String foodOriginalPrice, String foodImage, String foodDiscountedPrice, String foodType, String foodUserid) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDesc = foodDesc;
        this.foodOriginalPrice = foodOriginalPrice;
        this.foodImage = foodImage;
        this.foodDiscountedPrice = foodDiscountedPrice;
        this.foodType = foodType;
        this.foodUserid = foodUserid;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodOriginalPrice() {
        return foodOriginalPrice;
    }

    public void setFoodOriginalPrice(String foodOriginalPrice) {
        this.foodOriginalPrice = foodOriginalPrice;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodDiscountedPrice() {
        return foodDiscountedPrice;
    }

    public void setFoodDiscountedPrice(String foodDiscountedPrice) {
        this.foodDiscountedPrice = foodDiscountedPrice;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodUserid() {
        return foodUserid;
    }

    public void setFoodUserid(String foodUserid) {
        this.foodUserid = foodUserid;
    }
}
