package com.example.asus.xiaoliu.fragment.signChildFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xiaoliu.Async.SignAsy.SignByStateAsy;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class SignUnFragment extends Fragment {
    private ListView listView;
    private Dog dog;
    private View v;
    private User user;
    private BmobFile bmobFile;
    private View progress;
    private LinearLayout layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.sign_all_view, null);
        listView=(ListView)v.findViewById(R.id.lv_test);
        progress = v.findViewById(R.id.layout_progress);
        layout = v.findViewById(R.id.llD);
        new SignByStateAsy("未开始",listView,getActivity(),progress,layout).executeOnExecutor(Executors.newCachedThreadPool());
        return v;
    }

}
