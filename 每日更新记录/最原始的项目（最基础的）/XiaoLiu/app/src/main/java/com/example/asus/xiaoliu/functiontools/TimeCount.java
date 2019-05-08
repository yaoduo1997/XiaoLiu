package com.example.asus.xiaoliu.functiontools;
//获取验证码之后的倒计时功能
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

import com.example.asus.xiaoliu.R;

public class TimeCount extends CountDownTimer {
    private Button btn;

    public TimeCount(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn.setBackgroundResource(R.drawable.shape4);
        btn.setClickable(false);
        btn.setText("("+millisUntilFinished / 1000 +")秒后重试");
    }

    @Override
    public void onFinish() {
        btn.setText("重获验证码");
        btn.setClickable(true);
        btn.setBackgroundColor(Color.parseColor("#ef3d43"));//白色字体

    }
}