package com.example.asus.xiaoliu.registrations;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.LoginCache;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class CustomerMessage extends AppCompatActivity {
    private EditText etName;
    private Spinner spinner;
    private String sex;
    private EditText etPwd;
    private EditText etRePwd;
    private EditText etMessage;
    private EditText etWorkYear;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_person);
        ActivityManager.addActivity(this);

        final Intent intent = getIntent();
        final String identity = intent.getStringExtra("identity");

        etName=(EditText)findViewById(R.id.nicheng);
        spinner=(Spinner)findViewById(R.id.xingbie);
        etPwd=(EditText)findViewById(R.id.password);
        etRePwd=(EditText)findViewById(R.id.repassword);
        etMessage=(EditText)findViewById(R.id.qianming);
        etWorkYear=(EditText)findViewById(R.id.yanggoushichang);
        btnSubmit=(Button)findViewById(R.id.btn_submit);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resources resources=getResources();
                String[]sexs=resources.getStringArray(R.array.sex);
                sex=sexs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString()!=null&&!"".equals(etName.getText().toString())) {
                    if (etPwd.getText().toString() != null && !"".equals(etPwd.getText().toString())
                            && etRePwd.getText().toString() != null && !"".equals(etRePwd.getText().toString())) {
                        if (etPwd.getText().toString().equals(etRePwd.getText().toString())) {
                            User user = BmobUser.getCurrentUser(User.class);
                            user.setUsername(etName.getText().toString());
                            user.setUserSex(sex);
                            user.setPassword(etPwd.getText().toString());
                            user.setUserMessage(etMessage.getText().toString());
                            user.setWorkYear(Integer.parseInt(etWorkYear.getText().toString()));
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
//                                        Log.e("Update","数据更新成功");
                                        LoginCache.fetchUserInfo();
                                        Intent intent1 = new Intent();
                                        intent1.setClass(CustomerMessage.this, HomeActivity.class);
                                        intent1.putExtra("identity",identity);
                                        startActivity(intent1);
                                    }else {
                                        Log.e("Update","数据更新失败"+e.toString());
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(CustomerMessage.this, "密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        System.out.println(Toast.makeText(CustomerMessage.this, "密码不能为空", Toast.LENGTH_SHORT));
                    }
                }else {
                    Toast.makeText(CustomerMessage.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
