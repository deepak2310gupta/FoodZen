package com.example.foodzen.CollectionModels;

public class ModelAddProducts {


    public String pId,pName,pDesc,oriPrice,itemType,discountPrice,discontNote,productUserId;

    public ModelAddProducts(String pId, String pName, String pDesc, String oriPrice, String itemType, String discountPrice, String discontNote, String productUserId) {
        this.pId = pId;
        this.pName = pName;
        this.pDesc = pDesc;
        this.oriPrice = oriPrice;
        this.itemType = itemType;
        this.discountPrice = discountPrice;
        this.discontNote = discontNote;
        this.productUserId = productUserId;
    }
}
