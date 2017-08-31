package com.tianguo.zxz.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.tianguo.zxz.activity.newsLoginActivity;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    private Activity mContext;
    private ProgressDialog mDialog;
    private Disposable mDisposable;

    public BaseObserver(Activity context, ProgressDialog dialog) {
        mContext = context;
        mDialog = dialog;

        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mDisposable.dispose();
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(BaseEntity<T> value) {
        try {
        Log.e("tiaoshi", "onNext:");
        if (value.getCode() == 0) {
            if (!TextUtils.isEmpty(value.getMsg())){
                ToastUtil.showMessage(value.getMsg());
            }
            if (null == value.getData()) {
                onHandleSuccess(null);
                return;
            }
            Log.e("tiaoshi", value.getTick() + "");
            SharedPreferencesUtil.saveTicketInfo(mContext, value.getTick());
            String s = value.getData().toString();
            char c = s.charAt(0);
            String s1 = String.valueOf(c);
            if (s1.equals("[")) {
                LogUtils.e("ssssssss");
                ArrayList<T> mlist = (ArrayList<T>) value.getData();
                if (mlist.size() == 0) {
                    onHandleSuccess(null);
                    return;
                }
                for (int i = 0; i < mlist.size(); i++) {
                    T t = mlist.get(i);
                    onHandleSuccess(t);
                }
            } else {
                T t = value.getData();
                if (t==null){
                }else {
                    onHandleSuccess(t);
                }

            }

        } else {
            if (value.getCode()==2){
               mContext.startActivity(new Intent(mContext,newsLoginActivity.class));
            }
            onHandleError(0, null);
            if (null != value.getMsg() && !value.getMsg().isEmpty()) {
                ToastUtil.showMessage( value.getMsg());

            }

        }
        }catch (Exception e){
            Log.e("tiaoshi", e.toString());
        }
    }

    @Override
    public void onError(Throwable e) {
        try {


        Log.e("tiaoshi", "error:" + e.toString());
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

            onHandleError(404,"网络连接超时请检查网络");
        }catch (Exception es){

        }
    }

    @Override
    public void onComplete() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            Log.d("tiaoshi", "onComplete");

        }

    }

    protected abstract void onHandleSuccess(T t) ;

    public void onHandleError(int code, String message) {
        LogUtils.e(code+""+message);
        if (message == null) {
            return;
        }
        ToastUtil.showMessage(message);
    }
}
