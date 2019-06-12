package com.example.asus.xiaoliu.Async.DynamicAsy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class DownloadImgAsy extends AsyncTask {
    private BmobFile bmobFile;
    private ImageView imageView;

    public DownloadImgAsy(BmobFile bmobFile, ImageView imageView) {
        this.bmobFile = bmobFile;
        this.imageView = imageView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Log.e("xiazaidongtaitupian","========================开始下在动态内容图片");
        final File saveFile = new File(Environment.getExternalStorageDirectory(), bmobFile.getFilename());
        bmobFile.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Log.e("downloadIMG","正在下载");
            }

            @Override
            public void done(String savePath, BmobException e) {
                if(e==null){
                    Log.e("downloadIMG","下载完成"+savePath);
                    Bitmap bitmap= BitmapFactory.decodeFile(savePath);
//                    Log.e("bitmap","======================="+bitmap.toString());
                    imageView.setImageBitmap(bitmap);
                }else{
                    Log.e("downloadIMG","正在失败"+e.toString());
                }
            }
            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
        return null;
    }
}
