package com.example.asus.xiaoliu.functiontools;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.fragment.DynamicFragment;
import com.example.asus.xiaoliu.fragment.IndexFragment;
import com.example.asus.xiaoliu.fragment.MessageFragment;
import com.example.asus.xiaoliu.fragment.MyFragment;
import com.example.asus.xiaoliu.fragment.SignFragment;

public class TabDb {
    public static String[] getTabsTxt(){
        String[] tabs={"首页","商城","广场","订单","我的"};
        return tabs;
    }
    public static int[] getTabsImg(){
        int[] ids={R.drawable.shouye,
        R.drawable.shop,
        R.drawable.dongtai,
        R.drawable.dingdan,
        R.drawable.wo};
        return ids;
    }
    public static int[] getTabsImgLight(){
        int[] ids={R.drawable.shouye1,
        R.drawable.shop1,
        R.drawable.dongtai1,
        R.drawable.dingdan1,
        R.drawable.wo1};
        return ids;
    }
    public static Class[] getFragments(){
        Class[] clz={IndexFragment.class,
                MessageFragment.class,
                DynamicFragment.class,
                SignFragment.class,
                MyFragment.class};
        return clz;
    }
}
