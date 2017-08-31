package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.HeilCentBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/5/16.
 */

public class HelpCentnetActivity extends BaseActivity {
    @BindView(R.id.tv_back)
    TextView tvHelpBack;
    @BindView(R.id.vv_jiangyou)
    View jiangyou;
    @BindView(R.id.tv_help_cent_tiele)
    TextView tvHelpCentTiele;
    @BindView(R.id.tv_help_cent_text)
    WebView tvHelpCentText;
    @BindView(R.id.tv_cent_fk_yes)
    RadioButton tvCentFkYes;
    @BindView(R.id.tv_cent_fk_on)
    RadioButton tvCentFkOn;
    @BindView(R.id.ll_cent_fankui)
    RadioGroup llCentFankui;
    @BindView(R.id.tv_kefu_mm)
    TextView tvKefuMm;
    @BindView(R.id.ll_web_isShow)
    LinearLayout llWebIsShow;
    @BindView(R.id.ll_cent_isjiejue)
    LinearLayout llcentJiejue;
    private StringBuffer sb;
    private MyApplictation application;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_help_centex;
    }

    @Override
    protected void onDestroy() {
        try{


            application.removeActivity(this);
        }catch (Exception e){

        }
        super.onDestroy();

    }
    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        setLoadingFlag(false);
        sb = new StringBuffer();
        if (getIntent().getBooleanExtra("isHelp", true)) {
            String type = getIntent().getStringExtra(Constant.TYPE);
            if (type!=null && type.equals(Constant.ABOUTUS)){
                tvHelpBack.setText(Constant.ABOUTUS);
            }else {
                tvHelpBack.setText(getIntent().getStringExtra("centent"));
            }
            getHelp();
        } else {
            tvHelpBack.setText("攻略");
            getGongLue();
            llWebIsShow.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    public void getHelp() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        map.put("id", getIntent().getIntExtra("id", 0));
        Observable logins = RetroFactory.getInstance().getHelpCent(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<HeilCentBean>(this, pd) {
            @Override
            protected void onHandleSuccess(HeilCentBean bean) {
                tvHelpCentTiele.setText(bean.getTeile());
                //拼接一段HTML代码
                sb.append("<html>");
                sb.append("<head>");
                sb.append("<title>");
                sb.append(bean.getTeile());
                sb.append("</title>");
                sb.append("</head>");
                sb.append("<body>");
                sb.append(bean.getContent());
                sb.append("</body>");
                sb.append("</html>");
                tvHelpCentText.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
                tvHelpCentText.setWebViewClient(new WebViewClient() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        llWebIsShow.setVisibility(View.GONE);
                        super.onPageStarted(view, url, favicon);
                    }

                    @SuppressLint("WrongConstant")
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        llWebIsShow.setVisibility(View.VISIBLE);
                        super.onPageFinished(view, url);
                    }

                });

            }
        });

    }

    public void getGongLue() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("id", getIntent().getIntExtra("id", 0));
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getFanKuiCent(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<HeilCentBean>(this, pd) {
            @Override
            protected void onHandleSuccess(HeilCentBean bean) {
                tvHelpCentTiele.setText(bean.getTeile());
                //拼接一段HTML代码
                sb.append("<html>");
                sb.append("<head>");
                sb.append("<title>");
                sb.append(bean.getTeile());
                sb.append("</title>");
                sb.append("</head>");
                sb.append("<body>");
                sb.append(bean.getContent());
                sb.append("</body>");
                sb.append("</html>");
                tvHelpCentText.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
                tvHelpCentText.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                    }

                });

            }
        });

    }

    public void getFankui(final String YesorNo, final String onCline) {
        @SuppressLint("WrongConstant") TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String mtyb = android.os.Build.BRAND;// 手机品牌  
        String mtype = android.os.Build.MODEL;// 手机型号  
        String numer = tm.getLine1Number();// 手机号码  
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("pageId", getIntent().getIntExtra("id", 0));
        if (TextUtils.isEmpty(numer) || numer.equals("null")) {
            map.put("phone", "未知");
        } else {
            map.put("phone", numer);
        }
        map.put("model", mtyb + mtype);
        map.put("version", UpdateAppUtil.getAPPLocalVersion(HelpCentnetActivity.this));
        map.put("solve", YesorNo);
        map.put("service", onCline);
        map.put("devid",SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getFankui(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<HeilCentBean>(this, pd) {

            @Override
            protected void onHandleSuccess(HeilCentBean heilCentBean) {
                if (YesorNo.equals("已解决")) {
                    finish();
                } else if (onCline.equals("已点击")) {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2113988656&version=1";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        ToastUtil.showMessage("您没有装载qq或您的临时");
                    }
                }
            }
        });

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.tv_cent_fk_yes, R.id.tv_cent_fk_on, R.id.tv_kefu_mm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cent_fk_yes:
                llcentJiejue.setVisibility(View.GONE);
                jiangyou.setFocusable(true);
                jiangyou.setFocusableInTouchMode(true);
                jiangyou.requestFocus();
                jiangyou.requestFocusFromTouch();
                getFankui("已解决", "未点击");
                break;
            case R.id.tv_cent_fk_on:
                llcentJiejue.setVisibility(View.VISIBLE);
                llcentJiejue.setFocusable(true);
                llcentJiejue.setFocusableInTouchMode(true);
                llcentJiejue.requestFocus();
                llcentJiejue.requestFocusFromTouch();
                getFankui("未解决", "未点击");
                break;
            case R.id.tv_kefu_mm:
                getFankui("未解决", "已点击");

                break;
        }
    }
}
