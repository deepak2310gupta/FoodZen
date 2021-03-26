package com.example.foodzen.CollectionModels;

public class ModelUsers {


    public String name,phone,address,email,uid,usertype;


    public ModelUsers() {
    }

    public ModelUsers(String name, String phone, String address, String email, String uid, String usertype) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.uid = uid;
        this.usertype = usertype;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
