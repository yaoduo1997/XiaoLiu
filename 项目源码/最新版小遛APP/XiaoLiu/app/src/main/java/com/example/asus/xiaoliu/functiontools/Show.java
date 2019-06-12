package com.example.asus.xiaoliu.functiontools;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Show {
    static public void toast(Context context,String string){
        Toast toast = Toast.makeText(context,string,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
