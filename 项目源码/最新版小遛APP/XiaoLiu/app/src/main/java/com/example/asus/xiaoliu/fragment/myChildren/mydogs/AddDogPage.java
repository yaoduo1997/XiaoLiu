package com.example.asus.xiaoliu.fragment.myChildren.mydogs;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.PathGetter;
import com.example.asus.xiaoliu.functiontools.ProcessOn;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


public class AddDogPage extends AppCompatActivity {
    private Button btnSave;
    private EditText etName;
    private EditText etAge;
    private EditText etType;
    private EditText etAllergn;
    private EditText etLikeFood;
    private EditText etBadFood;
    private ImageView pre;
    private ImageView dogImage;
    private Uri uri;
    private File file;
    private Dog dog;
    private View progress;
    private LinearLayout layout;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adddog);
        ActivityManager.addActivity(this);

        dog=new Dog();
        etName=(EditText)findViewById(R.id.dogname);
        etAge=(EditText)findViewById(R.id.dogage);
        etType=(EditText)findViewById(R.id.dogtype);
        etAllergn=(EditText)findViewById(R.id.et_allergn);
        etLikeFood=(EditText)findViewById(R.id.et_like_food);
        etBadFood= (EditText)findViewById(R.id.et_bad_food);
        dogImage=(ImageView)findViewById(R.id.dogimage);
        progress = findViewById(R.id.layout_progresss);
        layout = findViewById(R.id.llC);
        dogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        pre=(ImageView)findViewById(R.id.iv_zuojiantou);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(AddDogPage.this,MyDog.class);
                startActivity(intent);
                finish();
            }
        });

        btnSave=(Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                new ProcessOn(progress,layout).RouteProcess();
                if (uri != null) {
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
                                dog.setDogUser(BmobUser.getCurrentUser(User.class));
                                PathGetter pathGetter = new PathGetter();
                                file = new File(pathGetter.getPath(AddDogPage.this, uri));
                                final BmobFile bmobFile = new BmobFile(file);

                                bmobFile.uploadblock(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                            Toast.makeText(AddDogPage.this, "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();

                                            if (bmobFile != null) dog.setDogImage(bmobFile);
                                            dog.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if (e == null) {
                                                        Log.e("AddDogPage", "狗狗添加成功" + s);
                                                        Intent intent1 = new Intent();
                                                        intent1.setClass(AddDogPage.this, MyDog.class);
                                                        startActivity(intent1);
                                                        finish();
                                                    } else {
                                                        Log.e("AddDogPage", "狗狗添加失败" + e.toString());
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(AddDogPage.this, "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onProgress(Integer value) {
                                        // 返回的上传进度（百分比）
                                    }
                                });
                            } else {
                                Toast.makeText(AddDogPage.this, "狗狗的类别没有填写", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddDogPage.this, "狗狗的年龄没有填写", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddDogPage.this, "狗狗的名字没有填写", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddDogPage.this,"请给狗狗添加图片", Toast.LENGTH_SHORT).show();
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
            pathGetter.getPath(AddDogPage.this, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(pathGetter.getPath(AddDogPage.this, uri));
            dogImage.setImageBitmap(bitmap);
        }
    }
}
