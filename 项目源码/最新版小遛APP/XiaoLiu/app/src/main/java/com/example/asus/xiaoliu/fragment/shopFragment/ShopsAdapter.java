package com.example.asus.xiaoliu.fragment.shopFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xiaoliu.R;

import java.util.List;

public class ShopsAdapter extends BaseAdapter{
    private List<Shops> shopsList;
    private Context context;

    public ShopsAdapter(){

    }

    public ShopsAdapter(List<Shops> shopsList, Context context) {
        this.shopsList = shopsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shopsList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        //1. 获取item子布局
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.layout_list, null);
            vh = new ViewHolder();
            vh.ivImg=convertView.findViewById(R.id.iv_Img);
            vh.tvName = convertView.findViewById(R.id.tv_Name);
            //缓存ViewHolder对象
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //2. 填充数据
        Shops s = shopsList.get(position);
        vh.ivImg.setImageResource(s.getPhoto());
        vh.tvName.setText(s.getName());
        return convertView;
    }
    class ViewHolder{
        private ImageView ivImg;
        private TextView tvName;
    }
}
