package com.example.asus.xiaoliu.entity;

import cn.bmob.v3.BmobObject;

public class FeedBack extends BmobObject {

    private User user;
    private String content;
    private String time;

    public User getUser() {
        return user;
    }

    public FeedBack setUser(User user) {
        this.user = user;
        return this;
    }

    public String getContent() {
        return content;
    }

    public FeedBack setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTime() {
        return time;
    }

    public FeedBack setTime(String time) {
        this.time = time;
        return this;
    }
}
