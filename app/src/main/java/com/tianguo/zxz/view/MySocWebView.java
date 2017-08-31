package com.tianguo.zxz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.tianguo.zxz.uctils.LogUtils;

/**
 * Created by lx on 2017/5/12.
 */

public class MySocWebView extends WebView {
    public MySocWebView(Context context)
    {
        super(context);
    }

    public MySocWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void goBack(boolean isback) {
        if (isback){
            LogUtils.e("back");
            super.goBack();
        }else {
            LogUtils.e("backno");

            goBack();
        }

    }

    @Override
    public void goBack() {
    }


}