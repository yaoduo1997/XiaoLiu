package com.example.asus.xiaoliu.entity;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {
    private User orderClient;        //订单委托人
    private User orderMandatary;     //订单被委托人
    private Dog dog;                  //狗狗信息
    private String orderState;       //订单处理状态
    private String orderEvaluate;    //订单评分
    private String orderStartTime;   //订单开始时间
    private String orderEndTime;     //单次订单结束时间
    private double time;             //单次时间
    private String dateC;            //遛狗的周期
    private String dayStartTime;    //每天遛狗的时间
    private double money;           //订单的金额
    private String orderAddress;    //狗狗主人地址
    private String entityName;

    public Order() { }

    public Order(User orderClient, User orderMandatary, Dog dog, String orderState, String orderEvaluate, String orderStartTime, String orderEndTime, double time, String dateC, double money, String orderAddress) {
        this.orderClient = orderClient;
        this.orderMandatary = orderMandatary;
        this.dog = dog;
        this.orderState = orderState;
        this.orderEvaluate = orderEvaluate;
        this.orderStartTime = orderStartTime;
        this.orderEndTime = orderEndTime;
        this.time = time;
        this.dateC = dateC;
        this.money = money;
        this.orderAddress = orderAddress;
    }

    public Order(String tableName, User orderClient, User orderMandatary, Dog dog, String orderState, String orderEvaluate, String orderStartTime, String orderEndTime, double time, String dateC, double money, String orderAddress) {
        super(tableName);
        this.orderClient = orderClient;
        this.orderMandatary = orderMandatary;
        this.dog = dog;
        this.orderState = orderState;
        this.orderEvaluate = orderEvaluate;
        this.orderStartTime = orderStartTime;
        this.orderEndTime = orderEndTime;
        this.time = time;
        this.dateC = dateC;
        this.money = money;
        this.orderAddress = orderAddress;
    }

    public User getOrderClient() {
        return orderClient;
    }

    public void setOrderClient(User orderClient) {
        this.orderClient = orderClient;
    }

    public User getOrderMandatary() {
        return orderMandatary;
    }

    public void setOrderMandatary(User orderMandatary) {
        this.orderMandatary = orderMandatary;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderEvaluate() {
        return orderEvaluate;
    }

    public void setOrderEvaluate(String orderEvaluate) {
        this.orderEvaluate = orderEvaluate;
    }

    public String getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getDateC() {
        return dateC;
    }

    public void setDateC(String dateC) {
        this.dateC = dateC;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDayStartTime() {
        return dayStartTime;
    }

    public void setDayStartTime(String dayStartTime) {
        this.dayStartTime = dayStartTime;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
