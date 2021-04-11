package com.example.foodzen.CollectionModels;

public class ModelPromoCodes {


    public String promoCodeid, promocodename, promocodedesc, promocodetitle, promodiscountamount, promocodeUserId;

    public ModelPromoCodes() {
    }

    public ModelPromoCodes(String promoCodeid, String promocodename, String promocodedesc, String promocodetitle, String promodiscountamount, String promocodeUserId) {
        this.promoCodeid = promoCodeid;
        this.promocodename = promocodename;
        this.promocodedesc = promocodedesc;
        this.promocodetitle = promocodetitle;
        this.promodiscountamount = promodiscountamount;
        this.promocodeUserId = promocodeUserId;
    }

    public String getPromoCodeid() {
        return promoCodeid;
    }

    public void setPromoCodeid(String promoCodeid) {
        this.promoCodeid = promoCodeid;
    }

    public String getPromocodename() {
        return promocodename;
    }

    public void setPromocodename(String promocodename) {
        this.promocodename = promocodename;
    }

    public String getPromocodedesc() {
        return promocodedesc;
    }

    public void setPromocodedesc(String promocodedesc) {
        this.promocodedesc = promocodedesc;
    }

    public String getPromocodetitle() {
        return promocodetitle;
    }

    public void setPromocodetitle(String promocodetitle) {
        this.promocodetitle = promocodetitle;
    }

    public String getPromodiscountamount() {
        return promodiscountamount;
    }

    public void setPromodiscountamount(String promodiscountamount) {
        this.promodiscountamount = promodiscountamount;
    }

    public String getPromocodeUserId() {
        return promocodeUserId;
    }

    public void setPromocodeUserId(String promocodeUserId) {
        this.promocodeUserId = promocodeUserId;
    }
}
