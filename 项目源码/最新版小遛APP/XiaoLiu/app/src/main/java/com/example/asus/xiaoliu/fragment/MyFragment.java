package com.example.asus.xiaoliu.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.myChildren.AboutUs;
import com.example.asus.xiaoliu.fragment.myChildren.EditPerson;
import com.example.asus.xiaoliu.fragment.myChildren.Identidity;
import com.example.asus.xiaoliu.fragment.myChildren.address.MyAddress;
import com.example.asus.xiaoliu.fragment.myChildren.mydogs.MyDog;
import com.example.asus.xiaoliu.fragment.myChildren.setting.SetingMain;
import com.example.asus.xiaoliu.functiontools.DownLoad;
import com.example.asus.xiaoliu.functiontools.PathGetter;
import com.example.asus.xiaoliu.functiontools.Show;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends Fragment implements View.OnClickListener{

    private Uri uri;
    private File file;
    private ImageView ivIdentity;   //身份
    private ImageView ivMessage;    //设置信息
    private Button btnSetting;      //设置
    private ImageView myImage;      //设置头像
    private TextView tvNiCheng;     //设置昵称
    private TextView tvYear;        //设置年份
    private LinearLayout llIdentity;//身份验证
    private LinearLayout llMyDogs;  //我的狗狗
    private LinearLayout llAddress; //我的地址
    private LinearLayout llAboutUs;//关于我们
    private LinearLayout llCheckUpdate;//检查更新
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_layout,container,false);
        ivIdentity = (ImageView)view.findViewById(R.id.iv_identity);
        //设置
        btnSetting = (Button)view.findViewById(R.id.bt_set);
        btnSetting.setOnClickListener(this);
        //设置信息
        ivMessage = (ImageView)view.findViewById(R.id.xinxi);
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditPerson.class));
            }
        });
        tvNiCheng = (TextView)view.findViewById(R.id.tv_nicheng);//昵称
        tvYear = (TextView)view.findViewById(R.id.tv_year); //年份
        //设置头像
        myImage=(ImageView)view.findViewById(R.id.myinfo);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        //身份认证
        llIdentity = (LinearLayout)view.findViewById(R.id.LL_identidy);
        llIdentity.setOnClickListener(this);
        //我的小狗
        llMyDogs = (LinearLayout)view.findViewById(R.id.ll_dog);
        llMyDogs.setOnClickListener(this);
        //我的地址
        llAddress = (LinearLayout)view.findViewById(R.id.ll_address);
        llAddress.setOnClickListener(this);
        //关于我们
        llAboutUs = (LinearLayout)view.findViewById(R.id.aboutus);
        llAboutUs.setOnClickListener(this);
        //检查更新
        llCheckUpdate = (LinearLayout)view.findViewById(R.id.checkupdate);
        llCheckUpdate.setOnClickListener(this);
        //更新与数据库有关的数据
        selectUser();
        return  view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_set:
                startActivity(new Intent(getActivity(), SetingMain.class));
                break;
            case R.id.LL_identidy:
                startActivity(new Intent(getActivity(), Identidity.class));
                break;
            case R.id.ll_dog:
                startActivity(new Intent(getActivity(), MyDog.class));
                break;
            case R.id.ll_address:
                startActivity(new Intent(getActivity(), MyAddress.class));
                break;
            case R.id.aboutus:
                startActivity(new Intent(getActivity(), AboutUs.class));
                break;
            case R.id.checkupdate:
                Show.toast(getActivity(),"当前版本已经是最新版本了！");
                break;

        }
    }
    public  void selectUser(){
        User user1= BmobUser.getCurrentUser(User.class);
        if(user1!=null) {
            BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.addWhereEqualTo("objectId", user1.getObjectId());
            categoryBmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> object, BmobException e) {
                    if (e == null) {
                        Log.e("Identidity", "查询成功" + object.get(0).getObjectId());
                        BmobFile bmobFile=object.get(0).getUserImage();
                        //如果用户设置了头像就显示数据库中的头像
                        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .circleCrop();
                        if(bmobFile!=null) {
                            Glide.with(getActivity()).load(bmobFile.getUrl()).apply(requestOptions).into(myImage);
                        }else{
                            Glide.with(getActivity()).load(R.drawable.mymoren).apply(requestOptions).into(myImage);
                        }
                        //身份认证后图片改变
                        if (object.get(0).getUserCardNumber() != null && !"".equals(object.get(0).getUserCardNumber())) {
                            ivIdentity.setImageResource(R.drawable.yirenzheng);
                        }
                        //如果用户设置了昵称就显示用户设置的如果用户没有设置昵称就显示自定义的一个昵称
                        if(object.get(0).getUsername() != null && !"".equals(object.get(0).getUsername())){
                            tvNiCheng.setText(object.get(0).getUsername());
                        }
                        //如果用户设置了养狗年份或者遛狗师担任年份就显示用户设置的如果用户没有设置昵称就显示自定义的一个年份
                        if(Integer.toString(object.get(0).getWorkYear()) != null && !"".equals(Integer.toString(object.get(0).getWorkYear()))){
                            tvYear.setText(Integer.toString(object.get(0).getWorkYear()));
                        }
                    } else {
                        Log.e("Identidity", "查询失败" + e.toString());
                    }
                }
            });
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            PathGetter pathGetter=new PathGetter();
            file = new File(pathGetter.getPath(getActivity(), uri));
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        //Toast.makeText(getActivity(), "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                        User user = BmobUser.getCurrentUser(User.class);
                        user.setUserImage(bmobFile);//是继承了BmobObject的一个类
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.d("bmob", "成功");
                                    RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .circleCrop();
                                    Glide.with(getActivity()).load(bmobFile.getUrl()).apply(requestOptions).into(myImage);
                                }else{
                                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }

            });
        }
    }
}
