package com.example.asus.xiaoliu.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

public class User extends BmobUser {
    private  String  userIdentity;
    //private  String  username
    // private  String  password;
    //email yoghurt邮箱
    //emailVerfiled 邮箱认证状态

    //private  String  mobilePhoneNumber;
    // mobilePhoneNumberVerified 用户手机认证状态
    private BmobFile userImage;//用户的头像，
    private  String  userSex;
    private  int userAge;
    private String userBirthday;
    private  String userCardNumber;
    private  String  userWechat;
    private  String  userAlipay;
    private  String userBlog;
    private String userMessage;
    private int workYear;           //遛狗师工作年份或者用户的养狗时间
    private  int userFlag;          //用户是否处于订单中
    private BmobPointer dogs;
    private BmobPointer dynamics;
    private BmobPointer comments;
    private BmobPointer address;    //地址
    private BmobPointer feedback;
    private String device;//用户手机的型号

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public BmobPointer getAddress() {
        return address;
    }

    public void setAddress(BmobPointer address) {
        this.address = address;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public User setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    public BmobFile getUserImage() {
        return userImage;
    }

    public User setUserImage(BmobFile userImage) {
        this.userImage = userImage;
        return this;
    }

    public String getUserSex() {
        return userSex;
    }

    public User setUserSex(String userSex) {
        this.userSex = userSex;
        return this;
    }

    public String getUserCardNumber() {
        return userCardNumber;
    }

    public User setUserCardNumber(String userCardNumber) {
        this.userCardNumber = userCardNumber;
        return this;
    }

    public String getUserWechat() {
        return userWechat;
    }

    public User setUserWechat(String userWechat) {
        this.userWechat = userWechat;
        return this;
    }

    public String getUserAlipay() {
        return userAlipay;
    }

    public User setUserAlipay(String userAlipay) {
        this.userAlipay = userAlipay;
        return this;
    }

    public String getUserBlog() {
        return userBlog;
    }

    public User setUserBlog(String userBlog) {
        this.userBlog = userBlog;
        return this;
    }

    public int getUserFlag() {
        return userFlag;
    }

    public User setUserFlag(int userFlag) {
        this.userFlag = userFlag;
        return this;
    }

    public BmobPointer getDogs() {
        return dogs;
    }

    public User setDogs(BmobPointer dogs) {
        this.dogs = dogs;
        return this;
    }

    public BmobPointer getDynamics() {
        return dynamics;
    }

    public User setDynamics(BmobPointer dynamics) {
        this.dynamics = dynamics;
        return this;
    }

    public BmobPointer getComments() {
        return comments;
    }

    public User setComments(BmobPointer comments) {
        this.comments = comments;
        return this;
    }

    public BmobPointer getFeedback() {
        return feedback;
    }

    public User setFeedback(BmobPointer feedback) {
        this.feedback = feedback;
        return this;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserMessage() {
        return userMessage;
    }


    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public int getWorkYear() {
        return workYear;
    }

    public void setWorkYear(int workYear) {
        this.workYear = workYear;
    }
}
