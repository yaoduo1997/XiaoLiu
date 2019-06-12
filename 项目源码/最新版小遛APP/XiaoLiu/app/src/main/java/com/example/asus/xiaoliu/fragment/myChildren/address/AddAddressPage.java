package com.example.asus.xiaoliu.fragment.myChildren.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Address;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddAddressPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaddress);
        ActivityManager.addActivity(this);

        final ImageView imageView=(ImageView)findViewById(R.id.iv_zuojiantou);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(AddAddressPage.this,MyAddress.class);
                startActivity(intent);
                finish();
            }
        });
        final EditText etAddress=(EditText)findViewById(R.id.et_address);
        Button btnOk=(Button)findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAddress.getText().toString()!=null&&!"".equals(etAddress.getText().toString())) {

                    Address address = new Address();
                    address.setAddress(etAddress.getText().toString());
                    address.setUser(BmobUser.getCurrentUser(User.class));
                    address.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.e("AddAddressPage","新增地址成功"+s);
                                Intent intent=new Intent();
                                intent.setClass(AddAddressPage.this,MyAddress.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.e("AddAddressPage","新增地址失败"+e.toString());
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(AddAddressPage.this,"请输入地址",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(AddAddressPage.this,AddAddressPage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
