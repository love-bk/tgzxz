package com.tianguo.zxz.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tianguo.zxz.R;

/**
 * Created by admin on 2017/8/10.
 */

public class CommonDialog {

    interface OnConfirmListener{
        void onConfirmClick();
    }
    public CommonDialog(Context context, String title, String content, final OnConfirmListener onConfirmListener) {

        View inflate = View.inflate(context, R.layout.common_dialog, null);
        final Dialog commonDialog = new Dialog(context, R.style.dialog);
        commonDialog.setContentView(inflate);
        TextView tvCancle = (TextView) inflate.findViewById(R.id.tv_cancle);
        TextView tvConfirm = (TextView) inflate.findViewById(R.id.tv_confirm);
        TextView tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        tvTitle.setText(title);
        tvContent.setText(content);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmListener!=null){
                    onConfirmListener.onConfirmClick();
                }
            }
        });
    }
}
