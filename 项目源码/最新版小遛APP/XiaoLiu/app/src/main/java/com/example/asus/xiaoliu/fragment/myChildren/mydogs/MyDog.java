package com.example.asus.xiaoliu.fragment.myChildren.mydogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.MainActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyDog extends AppCompatActivity {
    private ListView lvDog;
    private ImageView ivAddDog;
    private ImageView ivDelete;
    private ImageView ivUpdate;
    private ImageView pre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dog);
        ActivityManager.addActivity(this);

        lvDog=(ListView)findViewById(R.id.lv_dog);
        initDogList();
        pre =(ImageView)findViewById(R.id.iv_zuojiantou);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MyDog.this,HomeActivity.class);
                intent.putExtra("signMark","5");
                startActivity(intent);
                finish();
            }
        });

        lvDog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ivDelete=(ImageView)view.findViewById(R.id.iv_delete);
                //删除狗狗
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BmobQuery<Dog> categoryBmobQuery = new BmobQuery<>();
                        categoryBmobQuery.addWhereEqualTo("dogUser", BmobUser.getCurrentUser(User.class));
                        categoryBmobQuery.findObjects(new FindListener<Dog>() {
                            @Override
                            public void done(List<Dog> object, BmobException e) {
                                if (e == null) {
                                    Log.e("MyDog", "查询成功" + object.size());
                                    Dog dog=object.get(position);
                                    dog.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                Log.e("MyDog","狗狗删除成功");
                                                Intent intent=new Intent();
                                                intent.setClass(MyDog.this,MyDog.class);
                                                startActivity(intent);
                                            }else {
                                                Log.e("MyDog","狗狗删除失败"+e.toString());
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("MyDog", "查询失败" + e.toString());
                                }
                            }
                        });
                    }
                });

                //修改狗狗信息
                ivUpdate=(ImageView)view.findViewById(R.id.xiugai);
                ivUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2=new Intent();
                        intent2.putExtra("position", String.valueOf(position));
                        intent2.setClass(MyDog.this,UpdateDogPage.class);
                        startActivity(intent2);
                    }
                });

            }
        });

        ivAddDog=(ImageView)findViewById(R.id.iv_add_dog);
        ivAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MyDog.this,AddDogPage.class);
                startActivity(intent);
            }
        });
    }

    private  void initDogList(){
        if(BmobUser.getCurrentUser(User.class)!=null) {
            BmobQuery<Dog> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.addWhereEqualTo("dogUser", BmobUser.getCurrentUser(User.class));
            categoryBmobQuery.findObjects(new FindListener<Dog>() {
                @Override
                public void done(List<Dog> object, BmobException e) {
                    if (e == null) {
                        Log.e("MyDog", "查询成功" + object.size());
                        AddDogAdapter addressAdapter = new AddDogAdapter(MyDog.this, R.layout.activity_my_dog_items, object);
                        lvDog.setAdapter(addressAdapter);
                    } else {
                        Log.e("MyDog", "查询失败" + e.toString());
                    }
                }
            });
        }else {
            //以后跳转到登录界面
            Toast.makeText(MyDog.this,"请先登录,以后记得跳转到登录界面", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent();
            intent1.setClass(MyDog.this,MainActivity.class);
            startActivity(intent1);
        }

    }


    private  class AddDogAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<Dog> datalist;
        public AddDogAdapter(Context context, int itemLayoutID, List<Dog> datalist)
        {
            this.context=context;
            this.itemLayoutID=itemLayoutID;
            this.datalist=datalist;
        }
        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int position) {
            return   datalist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(context);
            View view=inflater.inflate(itemLayoutID,null);
            Dog dog=datalist.get(position);
            TextView tvName=(TextView)view.findViewById(R.id.dogname);
            TextView tvAge=(TextView)view.findViewById(R.id.dogage);
            TextView tvType=(TextView)view.findViewById(R.id.dogtype);
            TextView tvAllergn=(TextView)view.findViewById(R.id.tv_allergn);
            TextView tvLikeFood=(TextView)view.findViewById(R.id.tv_like_food);
            TextView tvBadFood=(TextView)view.findViewById(R.id.tv_bad_food);
            ImageView imageView=(ImageView)view.findViewById(R.id.dogimage);

            BmobFile bmobFile=dog.getDogImage();
            if(bmobFile!=null) {
                RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(context).load(bmobFile.getUrl()).apply(requestOptions).into(imageView);
            }
            tvName.setText(dog.getDogName());
            tvAge.setText(String.valueOf(dog.getDogAge()));
            tvType.setText(String.valueOf(dog.getDogType()));
            if(dog.getAllergen()!=null&&!"".equals(dog.getAllergen()))tvAllergn.setText(dog.getAllergen());
            if(dog.getDogGoodFood()!=null&&!"".equals(dog.getDogGoodFood()))tvLikeFood.setText(dog.getDogGoodFood());
            if(dog.getDogBadFood()!=null&&!"".equals(dog.getDogBadFood()))tvBadFood.setText(dog.getDogBadFood());

            return view;
        }

    }
}
