package com.example.asus.xiaoliu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.fragment.signChildFragment.SignAllFragment;
import com.example.asus.xiaoliu.fragment.signChildFragment.SignFinishFragment;
import com.example.asus.xiaoliu.fragment.signChildFragment.SignIngFragment;
import com.example.asus.xiaoliu.fragment.signChildFragment.SignUnFragment;

import java.util.ArrayList;
import java.util.List;


public class SignFragment extends Fragment implements View.OnClickListener{
    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment>mFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_sign_layout,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }
    //初始化数据
    private void initData(){
        mTitle=new ArrayList<>();
        mTitle.add(getString(R.string.text_all));
        mTitle.add(getString(R.string.text_unstart));
        mTitle.add(getString(R.string.text_ing));
        mTitle.add(getString(R.string.text_finish));

        mFragment=new ArrayList<>();
        mFragment.add(new SignAllFragment());
        mFragment.add(new SignUnFragment());
        mFragment.add(new SignIngFragment());
        mFragment.add(new SignFinishFragment());
    }
    //初始化view
    private void initView(){
        mTabLayout=(TabLayout)getActivity().findViewById(R.id.mTabLayout);
        mViewPager=(ViewPager)getActivity().findViewById(R.id.mViewPager);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }
            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public void onClick(View v) {

    }
}
