package com.tianguo.zxz.activity.MyActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.StudentListBean;
import com.tianguo.zxz.bean.VersionInfo;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.DataCleanManager;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.PermissionsUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

public class SettingActivity extends BaseActivity {

    private final int CLEAN_SUC = 1001;
    private final int CLEAN_FAIL = 1002;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_modify_password)
    TextView tvModifyPassword;
    @BindView(R.id.tv_replace_phone)
    TextView tvReplacePhone;
    @BindView(R.id.tv_perfect_data)
    TextView tvPerfectData;
    @BindView(R.id.tv_total_cache)
    TextView tvTotalCache;
    @BindView(R.id.tv_privacy_agreement)
    TextView tvPrivacyAgreement;
    @BindView(R.id.tv_check_update)
    TextView tvCheckUpdate;
    @BindView(R.id.tv_qq)
    TextView tvQQ;
    private StudentListBean infoBean;
    private Intent intent;
    HashMap<String, String> map = new HashMap<>();
    private Dialog dialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        tvBack.setText(R.string.setting);
        getInfo();
        try {
            tvTotalCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent = new Intent(this, SetPassWordActivity.class);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.ll_business, R.id.tv_perfect_data, R.id.ll_clear_cache, R.id.tv_privacy_agreement, R.id.tv_check_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_perfect_data:
                Intent intent = new Intent(this, MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_clear_cache:
                if ("0KB".equals(tvTotalCache.getText().toString())) {
                    ToastUtil.showMessage("缓存为0，不需要清理哦");
                } else {
                    clearAppCache();
                }
                break;
            case R.id.tv_privacy_agreement:
                Intent privacyIntent = new Intent(this, SoWebActivity.class);
                privacyIntent.putExtra(Constant.TITLE, "隐私协议");
                privacyIntent.putExtra("url", "http://www.tianinfo.cn/tgagreement.html");
                startActivity(privacyIntent);
                break;
            case R.id.tv_check_update:
                getGengxin();
                break;
            case R.id.ll_business:
                String qqstr = tvQQ.getText().toString();
                if (!TextUtils.isEmpty(qqstr)) {
                    if (!PermissionsUtils.hasPermission(this, Manifest.permission.CALL_PHONE)) {
                        PermissionsUtils.requestPermission(this, 0, Manifest.permission.CALL_PHONE);
                        return;
                    }
                    if (qqstr.contains(":")) {
                        String qq = qqstr.substring(qqstr.indexOf(":")+1,qqstr.length());
                        String url="mqqwpa://im/chat?chat_type=wpa&uin="+ qq;
                        if (isQQClientAvailable(this))
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        else {
                            ToastUtil.showMessage("请先安装QQ");
                        }
                    }

                }
                break;
        }
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
//
//    private void onClickCleanCache() {
//        if (dialog == null) {
//            dialog = new Dialog(this, R.style.dialog);
//            View view = View.inflate(this, R.layout.clear_cache_dialog, null);
//            view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clearAppCache();
//                    dialog.dismiss();
//                }
//            });
//            dialog.setContentView(view);
//        }
//        if (!dialog.isShowing()){
//            dialog.show();
//        }
//    }


    /**
     * 获取友盟渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Activity ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                @SuppressLint("WrongConstant") ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        //此处这样写的目的是为了在debug模式下也能获取到渠道号，如果用getString的话只能在Release下获取到。
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL") + "";
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    public void getGengxin() {
        final HashMap<String, Object> maps = new HashMap<>();
        maps.put("sso", SharedPreferencesUtil.getSSo(this));
        if (getChannelName(this) != null) {
            maps.put("channel", getChannelName(this));
        }
        Observable observable = RetroFactory.getInstance().getGengxin(maps);
        observable.compose(this.composeFunction).subscribe(new BaseObserver<VersionInfo>(this, this.pd) {
            @Override
            protected void onHandleSuccess(VersionInfo bean) {
                String[] updata = bean.getVer().split("[.]");
                String[] bendi = UpdateAppUtil.getAPPLocalVersion(SettingActivity.this).split("[.]");
                StringBuffer one = new StringBuffer();
                StringBuffer two = new StringBuffer();
                for (int i = 0; i < bendi.length; i++) {
                    one.append(updata[i]);
                    two.append(bendi[i]);
                }
                String bendis = two.toString().substring(0, 4);
                LogUtils.e(one.toString() + "" + two.toString().substring(0, 4));
                if (Integer.parseInt(one.toString()) <= Integer.parseInt(bendis)) {
                    ToastUtil.showMessage("已经是最新版本");
                    return;
                }
                map.put("tab", "检查更新");
                MobclickAgent.onEvent(SettingActivity.this, "click_Mywallet", map);
                UpdateAppUtil.updateApp(SettingActivity.this, bean);
            }

            @Override
            public void onHandleError(int code, String message) {
                ToastUtil.showMessage("已经是最新版本");
            }
        });
    }


    private void getInfo() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getMyInfo(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<StudentListBean>(this, pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(final StudentListBean bean) {
                infoBean = bean;
                if (bean != null) {
                    if (infoBean.getPwd() != -1) {
                        tvModifyPassword.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LogUtils.e("新密码" + infoBean.getPwd(), "gjj");
                                intent.putExtra("pwd", infoBean.getPwd());
                                startActivityForResult(intent, 102);

                            }
                        });
                    }

                }

            }

            @Override
            public void onHandleError(int code, String message) {
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 102) {
            infoBean.setPwd(1);
        }
    }


    /**
     * 清除app缓存
     *
     * @param
     */
    public void clearAppCache() {

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    //清除缓存
                    DataCleanManager.clearAllCache(SettingActivity.this);
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    ToastUtil.showMessage("清除失败");
                    break;
                case CLEAN_SUC:
                    ToastUtil.showMessage("清除成功");
                    tvTotalCache.setText("0KB");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
