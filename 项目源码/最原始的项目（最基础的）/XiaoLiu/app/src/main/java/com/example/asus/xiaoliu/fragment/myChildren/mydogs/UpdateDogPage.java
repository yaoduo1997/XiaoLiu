package com.example.asus.xiaoliu.fragment.myChildren.mydogs;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.DownLoad;
import com.example.asus.xiaoliu.functiontools.PathGetter;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UpdateDogPage extends AppCompatActivity {
    private String position;
    private EditText etName;
    private EditText etAge;
    private EditText etType;
    private EditText etAllergn;
    private EditText etLikeFood;
    private EditText etBadFood;
    private ImageView ivok;
    private Dog dog;
    private ImageView pre;
    private ImageView dogImage;
    private Uri uri;
    private File file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dogchange);
        ActivityManager.addActivity(this);

        pre=(ImageView)findViewById(R.id.iv_zuojiantou);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent();
                intent2.setClass(UpdateDogPage.this,MyDog.class);
                startActivity(intent2);
                finish();
            }
        });
        final Intent intent=getIntent();
        position=intent.getStringExtra("position");
        etName=(EditText)findViewById(R.id.dogname);
        etAge=(EditText)findViewById(R.id.dogage);
        etType=(EditText)findViewById(R.id.dogtype);
        etAllergn=(EditText)findViewById(R.id.et_allergn);
        etLikeFood=(EditText)findViewById(R.id.et_like_food);
        etBadFood=(EditText)findViewById(R.id.et_bad_food);
        ivok=(ImageView)findViewById(R.id.dogsubmit);
        dogImage=(ImageView)findViewById(R.id.dogimage);
        selectDog();

        dogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent1, 1);
            }
        });


        ivok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (etName.getText().toString() != null && !"".equals(etName.getText().toString())) {
                    if (etAge.getText().toString() != null && !"".equals(etAge.getText().toString())) {
                        if (etType.getText().toString() != null && !"".equals(etType.getText().toString())) {
                            dog.setDogName(etName.getText().toString());
                            dog.setDogAge(Integer.parseInt(etAge.getText().toString()));
                            dog.setDogType(etType.getText().toString());
                            if (etAllergn.getText().toString() != null)
                                dog.setAllergen(etAllergn.getText().toString());
                            if (etLikeFood.getText().toString() != null)
                                dog.setDogGoodFood(etLikeFood.getText().toString());
                            if (etBadFood.getText().toString() != null)
                                dog.setDogBadFood(etBadFood.getText().toString());
                            if(uri!=null){
                            PathGetter pathGetter=new PathGetter();
                            file = new File(pathGetter.getPath(UpdateDogPage.this, uri));
                            final BmobFile bmobFile = new BmobFile(file);
                            bmobFile.uploadblock(new UploadFileListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                        Toast.makeText(UpdateDogPage.this, "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();

                                        dog.setDogImage(bmobFile);

                                    }else{
                                        Toast.makeText(UpdateDogPage.this, "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onProgress(Integer value) {
                                    // 返回的上传进度（百分比）
                                }
                            });
                        }dog.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Log.e("UpdateDogPage","狗狗更新成功");
                                        Intent intent1=new Intent();
                                        intent1.setClass(UpdateDogPage.this,MyDog.class);
                                        startActivity(intent1);
                                        finish();
                                    }else {
                                        Log.e("UpdateDogPage","狗狗更新失败");
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(UpdateDogPage.this,"狗狗的类别没有填写", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(UpdateDogPage.this,"狗狗的年龄没有填写", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UpdateDogPage.this,"狗狗的名字没有填写", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            PathGetter pathGetter = new PathGetter();
            pathGetter.getPath(UpdateDogPage.this, uri);
            Bitmap bitmap= BitmapFactory.decodeFile( pathGetter.getPath(UpdateDogPage.this, uri));
            dogImage.setImageBitmap(bitmap);

        }
    }

    private  void  selectDog(){
        BmobQuery<Dog> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("dogUser", BmobUser.getCurrentUser(User.class));
        categoryBmobQuery.findObjects(new FindListener<Dog>() {
            @Override
            public void done(List<Dog> object, BmobException e) {
                if (e == null) {
                    Log.e("MyDog", "查询成功" + object.size());
                    dog=object.get(Integer.parseInt(position));
                    etName.setText(dog.getDogName());
                    etAge.setText(String.valueOf(dog.getDogAge()));
                    etType.setText(dog.getDogType());
                    if(dog.getDogImage()!=null){
                        DownLoad downLoad=new DownLoad();
                        downLoad.downloadFile(dog.getDogImage(),dogImage);
                    }
                    if(dog.getAllergen()!=null&&!"".equals(dog.getAllergen()))etAllergn.setText(dog.getAllergen());
                    if(dog.getDogGoodFood()!=null&&!"".equals(dog.getDogGoodFood())) etLikeFood.setText(dog.getDogGoodFood());
                    if(dog.getDogBadFood()!=null&&!"".equals(dog.getDogBadFood()))etBadFood.setText(dog.getDogBadFood());
                } else {
                    Log.e("MyDog", "查询失败" + e.toString());
                }
            }
        });
    }
}
