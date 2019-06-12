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
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateAddressPage extends AppCompatActivity {
    private  String id;
    private EditText etAddress;
    private ImageView pre;
    private String content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateaddress);
        ActivityManager.addActivity(this);

        Intent intent=getIntent();
        id=intent.getStringExtra("addressId");
        content=intent.getStringExtra("content");
        etAddress=(EditText)findViewById(R.id.et_address);
        etAddress.setText(content);
        pre=(ImageView)findViewById(R.id.iv_zuojiantou);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent();
                intent2.setClass(UpdateAddressPage.this,MyAddress.class);
                startActivity(intent2);
                finish();
            }
        });
        Button button=(Button)findViewById(R.id.btn_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAddress.getText().toString()!=null&&!"".equals(etAddress.getText().toString())){
                    Address address=new Address();
                    address.setObjectId(id);
                    address.setAddress(etAddress.getText().toString());
                    address.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.e("UpdateAddress","地址更新成功");
                                Intent intent1=new Intent();
                                intent1.setClass(UpdateAddressPage.this,MyAddress.class);
                                startActivity(intent1);
                                finish();
                            }else {
                                Log.e("UpdateAddress","地址更新失败"+e.toString());
                            }
                        }
                    });
                }  else {
                    Toast.makeText(UpdateAddressPage.this,"请输入地址",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(UpdateAddressPage.this,AddAddressPage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
