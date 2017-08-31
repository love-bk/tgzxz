package com.tianguo.zxz.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.TaskStateBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/4/28.
 */

public class ZhiFuBaoActivity extends BaseActivity {
    @BindView(R.id.tv_my_yue)
    TextView tvMyYue;
    @BindView(R.id.tv_zhifu_tixian)
    TextView tvZhifuTixian;
    @BindView(R.id.tv_my_ti_xinshou)
    RadioButton tvMyTiXinshou;
    @BindView(R.id.tv_my_ti_30)
    RadioButton tvMyTi30;
    @BindView(R.id.tv_my_ti_50)
    RadioButton tvMyTi50;
    @BindView(R.id.tv_my_ti_80)
    RadioButton tvMyTi80;
    @BindView(R.id.tv_my_ti_money)
    TextView tvMyTiMoney;
    @BindView(R.id.btn_readnews)
    Button btnReadnews;
    @BindView(R.id.btn_invitefriends)
    Button btnInvitefriends;
    int i = 30;
    private double gold;
    private MyApplictation application;
    private Intent intent1;
    private Dialog inputPhoneDialog;
    private Dialog commonDialog;
    private TextView tvContent;
    private boolean isWithDraw;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_alipay;
    }

    @Override
    protected void onDestroy() {
        try{


        application.removeActivity(this);
        }catch (Exception e){

        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        intent1 = new Intent(ZhiFuBaoActivity.this, MainActivity.class);
        createDialog();
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.codetext));
        }
        double golds = getIntent().getIntExtra("gold", 0);
        int wd = getIntent().getIntExtra("wd", 0);
        gold = golds / 100;
        tvMyYue.setText(gold + "");
        if (gold < 1) {
            tvMyTiXinshou.setChecked(false);
            tvMyTiXinshou.setClickable(false);
            tvMyTiXinshou.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
            tvMyTi30.setChecked(false);
            tvMyTi30.setClickable(false);
            tvMyTi30.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
            tvMyTi50.setChecked(false);
            tvMyTi50.setClickable(false);
            tvMyTi50.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
            tvMyTi80.setChecked(false);
            tvMyTi80.setClickable(false);
            tvMyTi80.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
        } else if (gold < 30 && gold >=1.0) {
            tvMyTiXinshou.setChecked(true);
            i = 1;
            tvMyTi30.setChecked(false);
            tvMyTi30.setClickable(false);
            tvMyTi30.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
            tvMyTi50.setChecked(false);
            tvMyTi50.setClickable(false);
            tvMyTi50.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
            tvMyTi80.setChecked(false);
            tvMyTi80.setClickable(false);
            tvMyTi80.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
        } else if (gold < 50 && gold >=30.0) {
            tvMyTiXinshou.setChecked(false);
            tvMyTi30.setChecked(true);
            i=30;
            tvMyTi50.setChecked(false);
            tvMyTi50.setClickable(false);
            tvMyTi50.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
            tvMyTi80.setChecked(false);
            tvMyTi80.setClickable(false);
            tvMyTi80.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
        } else if (gold < 80 && gold > 50.0) {
            tvMyTiXinshou.setChecked(false);
            tvMyTi50.setChecked(true);
            i = 50;
            tvMyTi30.setChecked(false);
            tvMyTi80.setChecked(false);
            tvMyTi80.setClickable(false);
            tvMyTi80.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
        } else {
            tvMyTiXinshou.setChecked(false);
            tvMyTi80.setChecked(true);
            i = 80;
            tvMyTi50.setChecked(false);
            tvMyTi30.setChecked(false);
        }

        if (wd>0){
            tvMyTiXinshou.setChecked(false);
            tvMyTiXinshou.setClickable(false);
            tvMyTiXinshou.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_alipay_checked_false));
        }
    }

    @Override
    protected void initData() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 101) {
            finish();
        }
    }


    @OnClick({R.id.tv_zhifu_back, R.id.tv_my_ti_money, R.id.tv_zhifu_tixian, R.id.tv_my_ti_xinshou, R.id.tv_my_ti_30, R.id.tv_my_ti_50, R.id.tv_my_ti_80, R.id.btn_readnews, R.id.btn_invitefriends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_zhifu_back:
                finish();
                break;
            case R.id.tv_zhifu_tixian://提现记录的点击事件
                startActivity(new Intent(ZhiFuBaoActivity.this, GetMoneyActivity.class));
                break;

            case R.id.tv_my_ti_xinshou:
                i = 1;
                tvMyTi30.setChecked(false);
                tvMyTi50.setChecked(false);
                tvMyTi80.setChecked(false);
                break;
            case R.id.tv_my_ti_30:
                tvMyTiXinshou.setChecked(false);
                tvMyTi50.setChecked(false);
                tvMyTi80.setChecked(false);
                i = 30;
                break;
            case R.id.tv_my_ti_50:
                tvMyTiXinshou.setChecked(false);
                tvMyTi30.setChecked(false);
                tvMyTi80.setChecked(false);
                i = 50;
                break;
            case R.id.tv_my_ti_80:
                tvMyTiXinshou.setChecked(false);
                tvMyTi30.setChecked(false);
                tvMyTi50.setChecked(false);
                i = 80;
                break;
            case R.id.btn_readnews://阅读新闻赚钱的点击事件
                intent1.putExtra("type",1);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_invitefriends://邀请好友赚钱的点击事件
                intent1.putExtra("type",2);
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_my_ti_money:
                if (gold - i < 0||(!tvMyTiXinshou.isChecked()&&!tvMyTi30.isChecked()
                        &&!tvMyTi50.isChecked()&&!tvMyTi80.isChecked())) {
                    ToastUtil.showMessage("当前余额没有达到选项标准");
                    return;
                } else {
                    if(i== 1){
                        getTaskState();
                    }else {
                        skipTiXian(i);
                    }
                }
                break;
        }
    }


    private void skipTiXian(int i){
        Intent intent = new Intent(this, TiXianActivty.class);
        intent.putExtra("money", i);
        startActivityForResult(intent, 101);
    }
    private void getTaskState(){
        setLoadingFlag(false);
        Map<String,Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        final Observable taskState = RetroFactory.getInstance().getTaskState(map);
        taskState.compose(composeFunction).subscribe(new BaseObserver<TaskStateBean>(this, pd) {
            @Override
            protected void onHandleSuccess(TaskStateBean taskStateBean) {
                if (taskStateBean!=null){
                    //先判断是否提现过
                    if (taskStateBean.getIs_withdraw()==0){
                        if (taskStateBean.getIs_done()==0){
                            if (commonDialog!=null && !commonDialog.isShowing()){
                                isWithDraw = true;//能提现
                                commonDialog.show();
                            }
                        }else {
                            skipTiXian(1);
                        }
                    }else {
                        if (commonDialog!=null && !commonDialog.isShowing()){
                            isWithDraw = false;//不能提现
                            tvContent.setText("此金额只供新手提现使用");
                            commonDialog.show();
                        }
                    }

                }
            }

        });
    }


    private void createDialog(){
        View inflate = View.inflate(this, R.layout.common_dialog, null);
        commonDialog = new Dialog(this, R.style.dialog);
        commonDialog.setContentView(inflate);
        TextView tvCancle = (TextView) inflate.findViewById(R.id.tv_cancle);
        TextView tvConfirm = (TextView) inflate.findViewById(R.id.tv_confirm);
        TextView tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
                if (isWithDraw){
                    startActivity(new Intent(ZhiFuBaoActivity.this,RenWuActivity.class));
                    finish();
                }
            }
        });
    }
}
