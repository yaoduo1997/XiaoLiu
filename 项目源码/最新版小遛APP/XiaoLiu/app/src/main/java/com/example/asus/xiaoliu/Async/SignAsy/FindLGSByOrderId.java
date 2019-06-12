package com.example.asus.xiaoliu.Async.SignAsy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class FindLGSByOrderId extends AsyncTask {
    private ImageView imageView;
    private Context context;
    private String orderId;
    private TextView textView;

    public FindLGSByOrderId(ImageView imageView, Context context, String orderId, TextView textView) {
        this.imageView = imageView;
        this.context = context;
        this.orderId = orderId;
        this.textView = textView;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param objects The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        BmobQuery<Order> bmobQuery = new BmobQuery<Order>();
        bmobQuery.getObject(orderId, new QueryListener<Order>() {
            @Override
            public void done(Order object,BmobException e) {
                if(e==null){
                    String uid=object.getOrderMandatary().getObjectId();
                    BmobQuery<User>bmobQuery1=new BmobQuery<User>();
                    bmobQuery1.getObject(uid, new QueryListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            String url=user.getUserImage().getUrl();
                            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .circleCrop();
                            Glide.with(context).load(url).apply(requestOptions).into(imageView);
                            textView.setText(user.getUsername());
                        }
                    });
                }else{
                }
            }
        });
        return null;
    }
}
