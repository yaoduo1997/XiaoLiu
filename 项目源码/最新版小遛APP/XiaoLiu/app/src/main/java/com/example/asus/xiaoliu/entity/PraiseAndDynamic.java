package com.example.asus.xiaoliu.entity;

public class PraiseAndDynamic {

    private Dynamic dynamic;
    private User user;

    public PraiseAndDynamic(){}

    public PraiseAndDynamic(Dynamic dynamic, User user) {
        this.dynamic = dynamic;
        this.user = user;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
