package com.example.asus.xiaoliu.fragment.myChildren.address;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Address;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.MyFragment;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyAddress extends AppCompatActivity {
    private  ListView listView;
    private ImageView ivUpdate;
    private  ImageView ivDelete;
    private  ImageView pre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddress);
        ActivityManager.addActivity(this);

        listView = (ListView)findViewById(R.id.lv_address_list);
        initAddressList();
        pre=(ImageView)findViewById(R.id.iv_zuojiantou);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MyAddress.this,HomeActivity.class);
                intent.putExtra("signMark","5");
                startActivity(intent);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                final TextView textView=(TextView)view.findViewById(R.id.tv_address_id);
                final TextView textView1=(TextView)view.findViewById(R.id.tv_address);
                ivUpdate=(ImageView) view.findViewById(R.id.iv_update);
                ivUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.putExtra("addressId",textView.getText().toString());
                        intent.putExtra("content",textView1.getText().toString());
                        intent.setClass(MyAddress.this,UpdateAddressPage.class);
                        startActivity(intent);
                        finish();
                    }
                });
                ivDelete=(ImageView)view.findViewById(R.id.iv_delete);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Address address=new Address();
                        address.setObjectId(textView.getText().toString());
                        address.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.e("DeleteAddress","地址删除成功");
                                    Intent intent=new Intent();
                                    intent.setClass(MyAddress.this,MyAddress.class);
                                    startActivity(intent);
                                }else {
                                    Log.e("DeleteAddress","地址删除失败"+e.toString());
                                    Intent intent=new Intent();
                                    intent.setClass(MyAddress.this,MyAddress.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        });

        Button btnAddress=(Button)findViewById(R.id.btn_add_address);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MyAddress.this,AddAddressPage.class);
                startActivity(intent);
            }
        });
    }
    private  void initAddressList(){
        if(BmobUser.getCurrentUser(User.class)!=null) {
            BmobQuery<Address> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
            categoryBmobQuery.findObjects(new FindListener<Address>() {
                @Override
                public void done(List<Address> object, BmobException e) {
                    if (e == null) {
                        Log.e("MyAddress", "查询成功" + object.size());
                        AddressAdapter addressAdapter = new AddressAdapter(MyAddress.this, R.layout.addressitems, object);

                        listView.setAdapter(addressAdapter);
                    } else {
                        Log.e("MyAddress", "查询失败" + e.toString());
                    }
                }
            });
        }else {
            //以后跳转到登录界面
            Toast.makeText(MyAddress.this,"请先登录,以后记得跳转到登录界面",Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent();
            intent1.setClass(MyAddress.this,MyFragment.class);
            startActivity(intent1);
        }

    }


    private  class AddressAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<Address> datalist;
        public AddressAdapter(Context context, int itemLayoutID, List<Address>datalist)
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
            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(itemLayoutID,null);
            Address address=datalist.get(position);
            TextView tvName=(TextView)view.findViewById(R.id.tv_name);
            TextView tvAddress=(TextView)view.findViewById(R.id.tv_address);
            TextView tvAddressId=(TextView)view.findViewById(R.id.tv_address_id);
            tvName.setText(BmobUser.getCurrentUser(User.class).getUsername());
            tvAddress.setText(address.getAddress());
            tvAddressId.setText(address.getObjectId());
            return  view;
        }
    }

}
