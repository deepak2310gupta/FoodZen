package com.example.foodzen.CollectionModels;

public class ModelLikedFoods {

    public String likedfoodid, likedfoodname, likedfoodshopname, likedfooduserid, likedfoodoriprice, likedfooddiscountprice, likedfooditemtype, likedfooddiscountnote;

    public ModelLikedFoods() {
    }

    public ModelLikedFoods(String likedfoodid, String likedfoodname, String likedfoodshopname, String likedfooduserid, String likedfoodoriprice, String likedfooddiscountprice, String likedfooditemtype, String likedfooddiscountnote) {
        this.likedfoodid = likedfoodid;
        this.likedfoodname = likedfoodname;
        this.likedfoodshopname = likedfoodshopname;
        this.likedfooduserid = likedfooduserid;
        this.likedfoodoriprice = likedfoodoriprice;
        this.likedfooddiscountprice = likedfooddiscountprice;
        this.likedfooditemtype = likedfooditemtype;
        this.likedfooddiscountnote = likedfooddiscountnote;
    }

    public String getLikedfoodid() {
        return likedfoodid;
    }

    public void setLikedfoodid(String likedfoodid) {
        this.likedfoodid = likedfoodid;
    }

    public String getLikedfoodname() {
        return likedfoodname;
    }

    public void setLikedfoodname(String likedfoodname) {
        this.likedfoodname = likedfoodname;
    }

    public String getLikedfoodshopname() {
        return likedfoodshopname;
    }

    public void setLikedfoodshopname(String likedfoodshopname) {
        this.likedfoodshopname = likedfoodshopname;
    }

    public String getLikedfooduserid() {
        return likedfooduserid;
    }

    public void setLikedfooduserid(String likedfooduserid) {
        this.likedfooduserid = likedfooduserid;
    }

    public String getLikedfoodoriprice() {
        return likedfoodoriprice;
    }

    public void setLikedfoodoriprice(String likedfoodoriprice) {
        this.likedfoodoriprice = likedfoodoriprice;
    }

    public String getLikedfooddiscountprice() {
        return likedfooddiscountprice;
    }

    public void setLikedfooddiscountprice(String likedfooddiscountprice) {
        this.likedfooddiscountprice = likedfooddiscountprice;
    }

    public String getLikedfooditemtype() {
        return likedfooditemtype;
    }

    public void setLikedfooditemtype(String likedfooditemtype) {
        this.likedfooditemtype = likedfooditemtype;
    }

    public String getLikedfooddiscountnote() {
        return likedfooddiscountnote;
    }

    public void setLikedfooddiscountnote(String likedfooddiscountnote) {
        this.likedfooddiscountnote = likedfooddiscountnote;
    }
}
