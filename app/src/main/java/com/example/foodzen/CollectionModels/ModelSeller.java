package com.example.foodzen.CollectionModels;

public class ModelSeller {


    public String name, category, address, email, uid, usertype,profileimageseller,discountnote;

    public ModelSeller() {
    }

    public ModelSeller(String name, String category, String address, String email, String uid, String usertype, String profileimageseller, String discountnote) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.email = email;
        this.uid = uid;
        this.usertype = usertype;
        this.profileimageseller = profileimageseller;
        this.discountnote = discountnote;
    }

    public String getProfileimageseller() {
        return profileimageseller;
    }

    public void setProfileimageseller(String profileimageseller) {
        this.profileimageseller = profileimageseller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getDiscountnote() {
        return discountnote;
    }

    public void setDiscountnote(String discountnote) {
        this.discountnote = discountnote;
    }
}
