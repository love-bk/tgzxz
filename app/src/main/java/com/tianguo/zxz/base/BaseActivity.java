package com.tianguo.zxz.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.tianguo.zxz.R;
import com.tianguo.zxz.bean.YYListBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.NetworkUtil;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.PermissionsUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity extends FragmentActivity {
    public static  ProgressDialog pd;

    /**
     * composeFunction 构成功能
     * showloding 显示loading
     * RETRY_TIMES 重试时间
     */

    public static Function<Observable, ObservableSource> composeFunction;
    private final long RETRY_TIMES = 1;
    private boolean showLoading = true;

    public int getJianglinum() {
        return jianglinum;
    }

    private int jianglinum;

    //布局ID
    protected abstract int getContentViewId();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT>=23) {
            if (!PermissionsUtils.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                PermissionsUtils.requestPermission(this, 0, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.codetext));
        }
        super.onCreate(savedInstanceState);
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initView();
                initData();
            }
        },200);

    }
    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
//        if ()
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        try{
            return super.dispatchTouchEvent(event);
        }catch (Exception e){
return true;
        }

    }
    OnJiangLinumListner listner;
    public  interface  OnJiangLinumListner{
        void  jiangli(int num);
    }

    public void getYaolingnum(final OnJiangLinumListner listner) {
        this.listner =listner;
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getYaoNum(map);
        logins.compose(this.composeFunction).subscribe(new BaseObserver<YYListBean>(this,pd) {
            @Override
            protected void onHandleSuccess(YYListBean loginBean) {
                if (listner!=null){
                    listner.jiangli(loginBean.getNum());
                }
//              SharedPreferencesUtil.saveID(BaseActivity.this,loginBean.getInviteCode());
            }
        });

    }

    private void init() {
        pd = new ProgressDialog(this);
        try {
            composeFunction = new Function<Observable, ObservableSource>() {
                @Override
                public ObservableSource apply(Observable observable) {
                    return observable.retry(RETRY_TIMES)
                            .subscribeOn(Schedulers.io())
                            .doOnSubscribe(new Consumer<Disposable>() {
                                @Override
                                public void accept(Disposable disposable) {
                                    if (NetworkUtil.isNetworkAvailable(BaseActivity.this)) {
                                        if (showLoading) {
                                            if (pd != null && !pd.isShowing()) {
                                                try {
                                                    pd.show();

                                                }catch (Exception e){

                                                }
                                            }
                                        }
                                    } else {
                                        ToastUtil.showMessage("网络连接异常，请检查网络");
                                    }
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        } catch (Exception e) {
            Log.e("tiaoshi", e.toString());
        }

    }

    public  void hideSystemKeyBoard( View v) {
        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) (BaseActivity.this)
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    /**
     * 设置是否显示加载提示
     *
     * @param show
     */
    public void setLoadingFlag(boolean show) {
        showLoading = show;
    }
    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        }catch (Exception e){

        }

    }

    public void setJianglinum(int jianglinum) {
        this.jianglinum = jianglinum;
    }


}
