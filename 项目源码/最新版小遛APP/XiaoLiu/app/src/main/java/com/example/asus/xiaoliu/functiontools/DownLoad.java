package com.example.asus.xiaoliu.functiontools;
//从数据库中下载文件
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class DownLoad {
//    public void downloadFile(BmobFile file, final ImageView imageView){
//        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
//        final File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
//        file.download(saveFile, new DownloadFileListener() {
//
//            @Override
//            public void onStart() {
//                Log.e("MainActivity","正在下载");
//            }
//
//            @Override
//            public void done(String savePath, BmobException e) {
//                if(e==null){
//                    Log.e("MainActivity","下载完成"+savePath);
//                    Bitmap bitmap= BitmapFactory.decodeFile(savePath);
////                    Log.e("bitmap","======================="+bitmap.toString());
//                    imageView.setImageBitmap(bitmap);
//                }else{
//                    Log.e("MainActivity","正在失败"+e.toString());
//                }
//            }
//
//            @Override
//            public void onProgress(Integer value, long newworkSpeed) {
//                Log.i("bmob","下载进度："+value+","+newworkSpeed);
//            }
//
//        });
//    }
}
