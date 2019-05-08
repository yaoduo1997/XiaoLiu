package com.example.asus.xiaoliu.functiontools;

import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

public class LoginCache {
    //用于缓存登录信息的
    public static void fetchUserInfo() {
        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if (e == null) {
                    Log.e("Login2","更新用户本地缓存成功");
                } else {
                    Log.e("Login2","更新用户本地缓失败"+e.toString());
                }
            }
        });
    }
}
