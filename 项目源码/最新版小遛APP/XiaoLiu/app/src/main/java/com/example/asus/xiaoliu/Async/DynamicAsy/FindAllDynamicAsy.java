package com.example.asus.xiaoliu.Async.DynamicAsy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.xiaoliu.Adapter.Dynamic.DynamicAdapter;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.fragment.dynamicChildren.AllComments;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindAllDynamicAsy extends AsyncTask {
    private Context context;
    private ListView listView;

    public FindAllDynamicAsy(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        BmobQuery<Dynamic> selectDynamic=new BmobQuery<>();
        selectDynamic.order("-createdAt");//按照创建动态的时间降序排列，最新创建的会排在前面
        selectDynamic.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(final List<Dynamic> list, BmobException e) {
                if(e==null){
//                    Toast.makeText(getActivity(),"数据查询成功"+list.size(),Toast.LENGTH_SHORT).show();
                    Log.e("数据查询成功",list.size()+"");
                    DynamicAdapter dynamicAdapter=new DynamicAdapter(context, R.layout.dynamic_items,list);
                    listView.setAdapter(dynamicAdapter);
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Intent intent=new Intent();
//                            Dynamic dynamic=list.get(position);
//                            intent.setClass(context, AllComments.class);
//                            intent.putExtra("dyId",String.valueOf(dynamic.getObjectId()));
//                            context.startActivity(intent);
//                        }
//                    });
                }else {
                    Toast.makeText(context,"数据查询失败"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return null;
    }
}

