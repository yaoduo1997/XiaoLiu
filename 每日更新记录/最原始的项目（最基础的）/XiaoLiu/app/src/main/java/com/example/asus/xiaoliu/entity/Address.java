package com.example.asus.xiaoliu.entity;

import cn.bmob.v3.BmobObject;

public class Address extends BmobObject {
    private User user;
    private  String  address;

    public User getUser() {
        return user;
    }

    public Address setUser(User user) {
        this.user = user;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Address setAddress(String address) {
        this.address = address;
        return this;
    }
}
