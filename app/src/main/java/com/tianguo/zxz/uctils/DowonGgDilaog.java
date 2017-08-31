package com.tianguo.zxz.uctils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tianguo.zxz.R;

/**
 * Created by lx on 2017/6/22.
 */

public class DowonGgDilaog extends Dialog {
    OnDownLoadListner listner;
    private final TextView viewById;

    public  interface OnDownLoadListner{
        void istrue();
        void isflas();

    }
    public DowonGgDilaog(@NonNull Context context, int themeResId, final OnDownLoadListner listner) {
        super(context, themeResId);
        this.listner = listner;
        View inflate = View.inflate(context, R.layout.common_dialog, null);
        this.setContentView(inflate);
         /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        Window window = this.getWindow() ;
        WindowManager m = this.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
        ((TextView) inflate.findViewById(R.id.tv_content)).setText("即将开始免流量下载");
        ((TextView) inflate.findViewById(R.id.tv_title)).setText("下载提示");
        inflate.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.isflas();
            }
        });
        viewById = (TextView) inflate.findViewById(R.id.tv_confirm);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessage("开始下载");
                listner.istrue();

            }
        });
    }

    @Override
    public void show() {
//        viewById.setChecked(true);
        super.show();
    }
}
