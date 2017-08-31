package com.tianguo.zxz.uctils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.tianguo.zxz.R;

/**
 */
public class ResendCodeTimer extends CountDownTimer {
    private static final long totalTime = 60 * 1000; //设置总的倒计时时间
    private static final long countDowninterval = 1000; //设置倒计时的间隔时间
    private Context context;
    private boolean bIsVCode;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    private TextView tv;


    public ResendCodeTimer(Context context, boolean bIsVCode) {
        super(totalTime, countDowninterval);
        this.context = context;
        this.bIsVCode = bIsVCode;


    }

    @Override
    public void onTick(long l) {
        if (tv != null) {
            tv.setEnabled(false);
            if (bIsVCode) {
                tv.setTextColor(context.getResources().getColor(R.color.commentbar_bg_black));
                tv.setText(l / 1000 + "秒");
            } else {
                tv.setTextColor(0xffafafaf);
                tv.setText(l / 1000 + "秒");
            }
        }

    }

    @Override
    public void onFinish() {
        setPhone("");
        if(tv != null){
            tv.setEnabled(true);
            int color = 0xff7bcd38;
            if(bIsVCode){
                tv.setTextColor(context.getResources().getColor(R.color.commentbar_bg_black));
                tv.setText("重新获取");
            }else{
                tv.setTextColor(color);
                tv.setText("重新获取");
            }

        }
    }
}
