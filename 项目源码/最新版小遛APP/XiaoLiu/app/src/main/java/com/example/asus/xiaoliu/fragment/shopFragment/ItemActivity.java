package com.example.asus.xiaoliu.fragment.shopFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xiaoliu.R;

public class ItemActivity extends AppCompatActivity{
    private ImageView ivImg;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvPhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_item);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("data");
        Shops s=(Shops) bundle.get("shops");

        ivImg=findViewById(R.id.iv_Img);
        tvName=findViewById(R.id.tv_Name);
        tvAddress=findViewById(R.id.tv_Address);
        tvPhone=findViewById(R.id.tv_Phone);

        ivImg.setImageResource(s.getPhoto());
        tvName.setText(s.getName());
        tvPhone.setText(s.getPhone());
        tvAddress.setText(s.getAddress());
    }
}
