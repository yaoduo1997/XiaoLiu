package com.example.asus.xiaoliu.fragment.dynamicChildren;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.PathGetter;
import com.example.asus.xiaoliu.functiontools.Show;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ReleaseDynamic extends AppCompatActivity {
    private TextView tvCancel;
    private Button btnOk;
    private ImageView img1;
    private ImageView img2;
    private Uri uri;
    private  File file;
    private EditText etContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_dynamic);
        ActivityManager.addActivity(this);

        tvCancel=findViewById(R.id.tv_cancel);
        btnOk=findViewById(R.id.release);
        img1=findViewById(R.id.iv_image);
        img2=findViewById(R.id.iv_image2);
        etContent=findViewById(R.id.et_content);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("signMark",String.valueOf(3));
                intent.setClass(ReleaseDynamic.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                final Dynamic dynamic=new Dynamic();
                PathGetter pathGetter = new PathGetter();
                if(pathGetter.getPath(ReleaseDynamic.this, uri)!=null) {
                    file = new File(pathGetter.getPath(ReleaseDynamic.this, uri));
                    final BmobFile bmobFile = new BmobFile(file);
                    bmobFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
//                            Toast.makeText(ReleaseDynamic.this, "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                                Log.e("上传文件成功：", bmobFile.getFileUrl());
                                if (bmobFile != null) dynamic.setDynamicImage(bmobFile);
                                dynamic.setDynamicContent(etContent.getText().toString());
                                dynamic.setDynamicUser(BmobUser.getCurrentUser(User.class));
                                dynamic.setPlNumber(0);
                                dynamic.setZanNumber(0);
                                dynamic.setZfNumber(0);
                                dynamic.setDevice(Build.MODEL);
                                dynamic.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Show.toast(ReleaseDynamic.this, "动态发布成功");
                                            Intent intent = new Intent();
                                            intent.putExtra("signMark", String.valueOf(3));
                                            intent.setClass(ReleaseDynamic.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Show.toast(ReleaseDynamic.this, "动态发布失败");
                                        }
                                    }
                                });
                            } else {
                                Show.toast(ReleaseDynamic.this, "上传文件失败：");
                            }
                        }
                    });
                }else {
                    dynamic.setDynamicContent(etContent.getText().toString());
                    dynamic.setDynamicUser(BmobUser.getCurrentUser(User.class));
                    dynamic.setPlNumber(0);
                    dynamic.setZanNumber(0);
                    dynamic.setZfNumber(0);
                    dynamic.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Show.toast(ReleaseDynamic.this, "动态发布成功");
                                Intent intent = new Intent();
                                intent.putExtra("signMark", String.valueOf(3));
                                intent.setClass(ReleaseDynamic.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Show.toast(ReleaseDynamic.this, "动态发布失败");
                            }
                        }
                    });

                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            PathGetter pathGetter = new PathGetter();
            pathGetter.getPath(ReleaseDynamic.this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(pathGetter.getPath(ReleaseDynamic.this, uri));
            img1.setImageBitmap(bitmap);
            img2.setVisibility(View.VISIBLE);
        }
    }


}
