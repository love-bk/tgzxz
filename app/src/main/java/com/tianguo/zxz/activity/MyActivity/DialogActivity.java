package com.tianguo.zxz.activity.MyActivity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;


/**
 * Created by lx on 2017/8/8.
 */

public class DialogActivity extends BaseActivity {
    public static final int ACTIVITY_TYPE_MESSAGE = 1;
    public static final int ACTIVITY_TYPE_NL = 2;
    public static final String KEY_ACTIVITY_MESSAGE = "dialog_message";
    public static final String KEY_ACTIVITY_TYPE = "activity_type";
    private Activity b;

    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        View localView = getWindow().getDecorView();
        WindowManager.LayoutParams localLayoutParams = (WindowManager.LayoutParams)localView.getLayoutParams();
        localLayoutParams.y = getResources().getDimensionPixelSize(getResources().getIdentifier("post_media_height", "dimen", getPackageName()));
        localLayoutParams.width = -1;
        getWindowManager().updateViewLayout(localView, localLayoutParams);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.actvity_dialog_show;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Window localWindow = getWindow();
        localWindow.setBackgroundDrawable(new ColorDrawable(0));
        TextView viewById = (TextView) findViewById(R.id.tv_text1);
        viewById.setText("请找到"+" \""+getResources().getString(R.string.app_name)+"\" ,并打开开关");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        if (paramMotionEvent.getAction() == 0)
        {
            finish();
            return true;
        }
        return super.onTouchEvent(paramMotionEvent);
    }
}
