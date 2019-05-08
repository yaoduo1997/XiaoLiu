package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ShouOF extends AppCompatActivity {

    private  TextView tvDog;
    private  TextView tvTime;
    private  TextView tvDuration;
    private  TextView tvMoney;
    private TextView tvAddress;
    private ImageView imgPre;
    private  int hour;
    private  int min;
    private String sh;
    private String sm;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouof);
        ActivityManager.addActivity(this);

        LinearLayout ll = (LinearLayout)findViewById(R.id.date_smallwalker);
        ll.setVisibility(View.GONE);//不显示也不占空间

        tvDog=(TextView)findViewById(R.id.tv_dog);
        tvDuration=(TextView)findViewById(R.id.tv_duration);
        tvMoney=(TextView)findViewById(R.id.tv_money);
        tvTime=(TextView)findViewById(R.id.tv_time);
        tvAddress=(TextView)findViewById(R.id.tv_address);

        Intent intent=getIntent();
        final String selectDog=intent.getStringExtra("selectDog");
        tvDog.setText(selectDog);
        final String startTime=intent.getStringExtra("startTime");
        tvTime.setText(startTime);
        final String duration=intent.getStringExtra("duration");
        tvDuration.setText(duration);

        final String  []timeArr=startTime.split(" ");
        hour=Integer.parseInt(timeArr[1].split(":")[0]);
        min=Integer.parseInt(timeArr[1].split(":")[1]);
        min=min+Integer.parseInt(duration);
        if(min>=60){
            hour+=min/60;
            min=min%60;
        }
        if(min<10)sm="0"+min;
        else sm=String.valueOf(min);
        if(hour<10)sh="0"+hour;
        else sh=String.valueOf(hour);
        final String selectAddress = intent.getStringExtra("selectAddress");
        tvAddress.setText(selectAddress);
        //计算价格
        Double money = 0.0;
        if(Integer.parseInt(duration)==20){
            money=25.0;
        }else if(Integer.parseInt(duration)==30){
            money=38.0;
        }else if(Integer.parseInt(duration)==60){
            money=50.0;
        }
        tvMoney.setText(money.toString());

        imgPre=(ImageView)findViewById(R.id.iv_pre);
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("selectDog",selectDog);
                intent1.putExtra("selectAddress",selectAddress);
                Log.e("选中的地址传过去：",selectAddress);
                intent1.setClass(ShouOF.this,ShouOT.class);
                startActivity(intent1);
                finish();
            }
        });

        ImageView imgNext=(ImageView)findViewById(R.id.iv_ok);
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Dog> categoryBmobQuery = new BmobQuery<>();
                categoryBmobQuery.addWhereEqualTo("dogName", tvDog.getText().toString());
                categoryBmobQuery.addWhereEqualTo("dogUser", BmobUser.getCurrentUser(User.class));
                categoryBmobQuery.findObjects(new FindListener<Dog>() {
                    @Override
                    public void done(List<Dog> object, BmobException e) {
                        if (e == null) {
                            Log.e("LGSlIST","查询成功"+object.size());
                            Order order=new Order();
                            order.setDog(object.get(0));
                            order.setMoney(Double.parseDouble((tvMoney.getText().toString())));
                            //这个登录后一定要取消注释，绑定当前用户
                            order.setOrderClient(BmobUser.getCurrentUser(User.class));
                            order.setOrderState("未开始");
                            order.setOrderEndTime(timeArr[0]+" "+sh+":"+sm);
                            order.setOrderStartTime(tvTime.getText().toString());
                            order.setOrderAddress(tvAddress.getText().toString());
                            order.setTime(Integer.parseInt(tvDuration.getText().toString()));
                            order.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Log.e("ShouTFI","订单添加成功:->"+s);
                                        Intent intent1=new Intent();
                                        intent1.putExtra("orderId",s);
                                        intent1.setClass(ShouOF.this,TipSuccess.class);
                                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                                        startActivity(intent1);
                                    } else {
                                        Log.e("ShouTFI","订单添加失败："+e.toString());
                                    }
                                }
                            });
                        } else {
                            Log.e("BMOB", e.toString());
                            Log.e("LGSlIST","查询失败"+e.toString());
                        }
                    }
                });
            }
        });

        ImageView imgCancel=(ImageView)findViewById(R.id.iv_cancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        ImageView imgQuestion=(ImageView)findViewById(R.id.iv_question);
        imgQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("selectDog",selectDog);
                intent1.putExtra("startTime",startTime);
                intent1.putExtra("duration",duration);
                intent1.putExtra("selectAddress",selectAddress);
                intent1.setClass(ShouOF.this,FeeScale.class);
                startActivity(intent1);
            }
        });
    }

    private  void  showAlertDialog(){
        ImageView imgCancel=(ImageView)findViewById(R.id.iv_cancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ShouOF.this);
                View  view=getLayoutInflater().inflate(R.layout.dialog_view,null);
                builder.setTitle("小遛提示您:");
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.setClass(ShouOF.this,HomeActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
            }
        });
    }

}
