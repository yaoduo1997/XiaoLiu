package com.example.asus.xiaoliu.fragment.myChildren;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import static java.lang.System.load;

public class AboutUs extends AppCompatActivity {

    private ImageView ivReturn;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_aboutus);
        ActivityManager.addActivity(this);
//        String myUrl = "http://b.hiphotos.baidu.com/image/h%3D300/sign=92afee66fd36afc3110c39658318eb85/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg";
//        imageView = (ImageView) findViewById(R.id.image);
//        Glide.with(this).load(myUrl).into(imageView);

        ivReturn = (ImageView)findViewById(R.id.btn_zuojiantou);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AboutUs.this, HomeActivity.class);
                intent.putExtra("signMark","5");
                startActivity(intent);
                finish();
            }
        });
    }
}
