package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Address;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class ShouTT extends AppCompatActivity {
    private ImageView ivPre = null;
    private ImageView ivNext = null;
    private Spinner spinner = null;
    private Spinner spinnerone = null;
    private String selectDog;
    private String selectAdddress;
    private Dog dog;
    private Address address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouoo);
        ActivityManager.addActivity(this);

        myAdapterSelect();

        Intent intent = getIntent();
        final String dateCir = intent.getStringExtra("dateC");

        spinner = (Spinner)findViewById(R.id.spn_1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view.findViewById(R.id.tv_test);
                String txt1=textView.getText().toString();
                selectDog=txt1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ShouTT.this,"未选中狗狗",Toast.LENGTH_SHORT).show();;
            }
        });

        spinnerone = (Spinner)findViewById(R.id.spn_2);
        spinnerone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view.findViewById(R.id.tv_test1);
                String txt2=textView.getText().toString();
                selectAdddress=txt2;
                Log.e("选中的地址",selectAdddress);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ShouTT.this,"未选中地址",Toast.LENGTH_SHORT).show();;
            }
        });

        ivPre = (ImageView)findViewById(R.id.iv_pre);
        ivNext = (ImageView)findViewById(R.id.iv_next);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("dateC",dateCir);
                intent1.setClass(ShouTT.this, ShouTO.class);
                startActivity(intent1);
                finish();
            }
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("dateC",dateCir);
                intent1.putExtra("selectDog",selectDog);
                intent1.putExtra("selectAddress",selectAdddress);
                intent1.setClass(ShouTT.this,ShouTTh.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(ShouTT.this).toBundle());
            }
        });
    }
    public  void  myAdapterSelect(){

        BmobQuery<Dog> categoryBmobQuery1 = new BmobQuery<Dog>();
        categoryBmobQuery1.addWhereEqualTo("dogUser", BmobUser.getCurrentUser(User.class));
        categoryBmobQuery1.findObjects(new FindListener<Dog>() {
            @Override
            public void done(List<Dog> object, BmobException e) {
                if (e == null) {
                    List<Dog>dogs=new ArrayList<Dog>();
                    for(int i=0;i<object.size();++i){
                        dog =new Dog();
                        dog=object.get(i);
                        dogs.add(dog);
                    }
                    MyAdapterDog myAdapterDog=new MyAdapterDog(dogs,ShouTT.this);
                    spinner.setAdapter(myAdapterDog);
                } else {
                    Log.e("LGSlIST","查询失败"+e.toString());
                }
            }
        });

        BmobQuery<Address> categoryBmobQuery2 = new BmobQuery<Address>();
        categoryBmobQuery2.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
        categoryBmobQuery2.findObjects(new FindListener<Address>() {
            @Override
            public void done(List<Address> object, BmobException e) {
                if (e == null) {
                    List<Address>addresses=new ArrayList<Address>();
                    for(int i=0;i<object.size();++i){
                        address =new Address();
                        address=object.get(i);
                        addresses.add(address);
                        //Log.e("地址：",address.getAddress());
                    }
                    MyAdapterAddress myAdapterAddress=new MyAdapterAddress(addresses,ShouTT.this);
                    spinnerone.setAdapter(myAdapterAddress);
                    Log.e("LGSlIST_ADDRESS","查询成功！"+addresses.size());
                } else {
                    Log.e("LGSlIST","查询失败"+e.toString());
                }
            }
        });

    }
}

