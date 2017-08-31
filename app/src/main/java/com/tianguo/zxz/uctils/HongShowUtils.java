package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tianguo.zxz.base.BaseActivity;

/**
 * Created by lx on 2017/7/7.
 */

public class HongShowUtils {
    public  static  void show(BaseActivity main, final ImageView iv_tian_hong, final View tvMainTeiliTime){
        final PopViewUtils popupWindow = new PopViewUtils(main);
        Glide.with(main).load("http://www.sweetinfo.cn/upload/sky_red.png")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        iv_tian_hong.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        return false;
                    }
                }).into(iv_tian_hong);
        iv_tian_hong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.show(tvMainTeiliTime,  Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }
}
