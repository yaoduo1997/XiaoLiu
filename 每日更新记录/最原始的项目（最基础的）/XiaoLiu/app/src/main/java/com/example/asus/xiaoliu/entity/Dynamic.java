package com.example.asus.xiaoliu.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

public class Dynamic extends BmobObject {

    private User dynamicUser;//动态的发起人
    private  String dynamicTime;//动态的发布时间
    private  String  dynamicContent;//动态的文字内容
    private BmobFile dynamicImage;//动态的图片
    private String device;//手机的型号
    private BmobPointer comments;//一对多的评论
    private BmobPointer Zans;//一对多的赞
    private BmobRelation zanUser;
    private BmobRelation zfUser;

    public Dynamic() {
    }

    public BmobRelation getZfUser() {
        return zfUser;
    }

    public Dynamic setZfUser(BmobRelation zfUser) {
        this.zfUser = zfUser;
        return this;
    }

    public BmobRelation getZanUser() {
        return zanUser;
    }

    public Dynamic setZanUser(BmobRelation zanUser) {
        this.zanUser = zanUser;
        return this;
    }

    private  int  zanNumber;
    private  int  plNumber;
    private  int  zfNumber;

    public BmobPointer getComments() {
        return comments;
    }

    public Dynamic setComments(BmobPointer comments) {
        this.comments = comments;
        return this;
    }

    public User getDynamicUser() {
        return dynamicUser;
    }

    public Dynamic setDynamicUser(User dynamicUser) {
        this.dynamicUser = dynamicUser;
        return this;
    }

    public String getDynamicTime() {
        return dynamicTime;
    }

    public Dynamic setDynamicTime(String dynamicTime) {
        this.dynamicTime = dynamicTime;
        return this;
    }

    public String getDynamicContent() {
        return dynamicContent;
    }

    public Dynamic setDynamicContent(String dynamicContent) {
        this.dynamicContent = dynamicContent;
        return this;
    }

    public BmobFile getDynamicImage() {
        return dynamicImage;
    }

    public Dynamic setDynamicImage(BmobFile dynamicImage) {
        this.dynamicImage = dynamicImage;
        return this;
    }

    public int getZanNumber() {
        return zanNumber;
    }

    public Dynamic setZanNumber(int zanNumber) {
        this.zanNumber = zanNumber;
        return this;
    }

    public int getPlNumber() {
        return plNumber;
    }

    public Dynamic setPlNumber(int plNumber) {
        this.plNumber = plNumber;
        return this;
    }

    public int getZfNumber() {
        return zfNumber;
    }

    public Dynamic setZfNumber(int zfNumber) {
        this.zfNumber = zfNumber;
        return this;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public BmobPointer getZans() {
        return Zans;
    }

    public void setZans(BmobPointer zans) {
        Zans = zans;
    }
}
