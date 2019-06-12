package com.example.asus.xiaoliu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.fragment.shopFragment.ItemActivity;
import com.example.asus.xiaoliu.fragment.shopFragment.Shops;
import com.example.asus.xiaoliu.fragment.shopFragment.ShopsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageFragment extends Fragment {
    private ViewPager viewPager;
    private List<ImageView> imgList;
    private View view;
    private ListView lvShop;
    private ShopsAdapter adapter1;
    private List<Shops> shops;
    private int currentItem=0;
    //获取轮播图片
    private Integer[] images = {R.drawable.pic0,R.drawable.pic1,R.drawable.pic2};
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_shop_layout,container,false);
        initView();
        lvShop=view.findViewById(R.id.lv_shop);
        shops=initData();
        adapter1=new ShopsAdapter(shops,getActivity());
        lvShop.setAdapter(adapter1);

        lvShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shops shop=shops.get(position);
                Intent intent=new Intent();
                intent.setClass(getActivity(),ItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shops",shop);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });

        return view;

    }
    //当View创建完成
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private List<Shops> initData() {
        List<Shops> shops=new ArrayList<>();
        Shops s1=new Shops();
        s1.setPhoto(R.drawable.xp);
        s1.setName("新派萌宠生活馆");
        s1.setPhone("0311-8677-0538");
        s1.setAddress("石家庄裕华区体育南大街379号");
        shops.add(s1);

        Shops s2=new Shops();
        s2.setPhoto(R.drawable.cdd);
        s2.setName("宠多多宠物");
        s2.setPhone("150-8186-2533");
        s2.setAddress("石家庄裕华区塔南路79号");
        shops.add(s2);

        Shops s3=new Shops();
        s3.setPhoto(R.drawable.ab);
        s3.setName("艾比宠物");
        s3.setPhone("155-3394-9786");
        s3.setAddress("石家庄裕华区体育南大街与方北路交口379号");
        shops.add(s3);

        Shops s4=new Shops();
        s4.setPhoto(R.drawable.to);
        s4.setName("TOMMY宠物");
        s4.setPhone("0311-6668-9987");
        s4.setAddress("石家庄裕华区新石北路中华大街");
        shops.add(s4);

        Shops s5=new Shops();
        s5.setPhoto(R.drawable.gz);
        s5.setName("贵族宠物");
        s5.setPhone("0311-6668-9987");
        s5.setAddress("石家庄裕华区新石北路中华大街");
        shops.add(s5);

        Shops s6=new Shops();
        s6.setPhoto(R.drawable.mdg);
        s6.setName("麦多格宠物");
        s6.setPhone("132-9283-9001");
        s6.setAddress("石家庄中山路与金山街交口北行50米");
        shops.add(s6);

        Shops s7=new Shops();
        s7.setPhoto(R.drawable.tg);
        s7.setName("糖果宠物SPA会馆");
        s7.setPhone("0311-6655-7999");
        s7.setAddress("石家庄桥西区槐安西路与西里街交叉口");
        shops.add(s7);

        Shops s8=new Shops();
        s8.setPhoto(R.drawable.pb);
        s8.setName("帕比宠物");
        s8.setPhone("0311-6873-7429");
        s8.setAddress("石家庄桥西区槐安路月儿湾小区");
        shops.add(s8);

        return shops;
    }

    public void initView(){
        viewPager = view.findViewById(R.id.viewPager);
        imgList = new ArrayList<ImageView>();
        //将图片放到集合中
        for(int i = 0; i<images.length;i++){
            ImageView img = new ImageView(getActivity());
            img.setBackgroundResource(images[i]);
            imgList.add(img);
        }
        //设置轮播图
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    //定义的轮播图的适配器
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(imgList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(imgList.get(position));
            System.out.println(position);
            return imgList.get(position);
        }

    }

    //利用线程池定时执行动画轮播
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                3,
                TimeUnit.SECONDS);
    }

    //图片轮播任务
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % images.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    //接收子线程传递过来的数据
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

}
