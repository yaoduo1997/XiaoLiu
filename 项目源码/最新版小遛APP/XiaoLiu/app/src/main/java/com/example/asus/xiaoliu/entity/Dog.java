package com.example.asus.xiaoliu.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Dog extends BmobObject {

    private  String  dogName;
    private String dogType;
    private User dogUser;
    private BmobFile dogImage;
    private  int dogAge;
    private  String dogBadFood;//狗狗不喜欢
    private  String  dogGoodFood;//狗狗喜欢的食物
    private  String  allergen;//过敏原

    public Dog(){}

    public Dog(String dogName, String dogType, BmobFile dogImage, int dogAge) {
        this.dogName = dogName;
        this.dogType = dogType;
        this.dogImage = dogImage;
        this.dogAge = dogAge;
    }

    public Dog(String dogName, String dogType, User dogUser, BmobFile dogImage, int dogAge, String dogBadFood, String dogGoodFood, String allergen) {
        this.dogName = dogName;
        this.dogType = dogType;
        this.dogUser = dogUser;
        this.dogImage = dogImage;
        this.dogAge = dogAge;
        this.dogBadFood = dogBadFood;
        this.dogGoodFood = dogGoodFood;
        this.allergen = allergen;
    }

    public int getDogAge() {
        return dogAge;
    }

    public Dog setDogAge(int dogAge) {
        this.dogAge = dogAge;
        return this;
    }

    public String getAllergen() {
        return allergen;
    }

    public Dog setAllergen(String allergen) {
        this.allergen = allergen;
        return this;
    }

    public String getDogName() {
        return dogName;
    }

    public Dog setDogName(String dogName) {
        this.dogName = dogName;
        return this;
    }

    public String getDogType() {
        return dogType;
    }

    public Dog setDogType(String dogType) {
        this.dogType = dogType;
        return this;
    }

    public User getDogUser() {
        return dogUser;
    }

    public Dog setDogUser(User dogUser) {
        this.dogUser = dogUser;
        return this;
    }

    public BmobFile getDogImage() {
        return dogImage;
    }

    public Dog setDogImage(BmobFile dogImage) {
        this.dogImage = dogImage;
        return this;
    }

    public String getDogBadFood() {
        return dogBadFood;
    }

    public Dog setDogBadFood(String dogBadFood) {
        this.dogBadFood = dogBadFood;
        return this;
    }

    public String getDogGoodFood() {
        return dogGoodFood;
    }

    public Dog setDogGoodFood(String dogGoodFood) {
        this.dogGoodFood = dogGoodFood;
        return this;
    }
}
