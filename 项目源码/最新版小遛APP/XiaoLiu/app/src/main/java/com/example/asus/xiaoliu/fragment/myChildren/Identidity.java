package com.example.asus.xiaoliu.fragment.myChildren;

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

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.MainActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Identidity extends AppCompatActivity {
    private EditText tvIdentNum;
    private Button btnIdentidy;
    private ImageView ivPre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identityauth);
        ActivityManager.addActivity(this);

        tvIdentNum=(EditText)findViewById(R.id.et_identnum);
        btnIdentidy=(Button)findViewById(R.id.btn_identity);
        ivPre=(ImageView)findViewById(R.id.iv_zuojiantou);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(Identidity.this,HomeActivity.class);
                intent.putExtra("signMark","5");
                startActivity(intent);
                finish();
            }
        });
        selectUser();
        btnIdentidy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvIdentNum.getText().toString().length()==18) {
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setUserCardNumber(tvIdentNum.getText().toString());
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Log.e("identidity", "更新ok");
                            Intent intent = new Intent();
                            intent.setClass(Identidity.this,HomeActivity.class);
                            intent.putExtra("signMark","5");
                            startActivity(intent);
                        }
                    });
                }
                else
                {
                    Toast.makeText(Identidity.this,"身份证长度有错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void selectUser() {
        if (BmobUser.getCurrentUser(User.class) != null) {
            User user1 = BmobUser.getCurrentUser(User.class);
            BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.addWhereEqualTo("objectId", user1.getObjectId());
            categoryBmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> object, BmobException e) {
                    if (e == null) {
                        Log.e("Identidity", "查询成功" + object.get(0).getObjectId());
                        tvIdentNum.setText(object.get(0).getUserCardNumber());
                    } else {
                        Log.e("Identidity", "查询失败" + e.toString());
                    }
                }
            });
        }else {
            //以后跳转到的登录界面
            Toast.makeText(Identidity.this,"请登录-以后请调到登录界面，现在额000", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            intent.setClass(Identidity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
