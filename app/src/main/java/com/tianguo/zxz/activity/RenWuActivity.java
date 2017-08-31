package com.tianguo.zxz.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianguo.zxz.Flat;
import com.tianguo.zxz.MyApplictation;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.MyActivity.IntegralActivity;
import com.tianguo.zxz.activity.MyActivity.MyInfoActivity;
import com.tianguo.zxz.activity.MyActivity.SoWebActivity;
import com.tianguo.zxz.adapter.TaskListViewAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.JiangLiBean;
import com.tianguo.zxz.bean.QiandaoBean;
import com.tianguo.zxz.bean.RecodBean;
import com.tianguo.zxz.bean.SignBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.AppInfoUtil;
import com.tianguo.zxz.uctils.Constant;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.MD5;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.view.DataSetAdapter;
import com.tianguo.zxz.view.MyListView;
import com.tianguo.zxz.view.VerticalRollingTextView;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/7/4.
 */

public class RenWuActivity extends BaseActivity implements TaskListViewAdapter.TaskClickListener {
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_qiandao)
    TextView tvQiandao;
    @BindView(R.id.tv_qiandao_day1)
    TextView tvQiandaoDay1;
    @BindView(R.id.tv_qiandao_day2)
    TextView tvQiandaoDay2;
    @BindView(R.id.tv_qiandao_day3)
    TextView tvQiandaoDay3;
    @BindView(R.id.tv_qiandao_day4)
    TextView tvQiandaoDay4;
    @BindView(R.id.tv_qiandao_day5)
    TextView tvQiandaoDay5;
    @BindView(R.id.tv_qiandao_day6)
    TextView tvQiandaoDay6;
    @BindView(R.id.tv_qiandao_day7)
    TextView tvQiandaoDay7;
    @BindView(R.id.tv_qiandao_iv_day1)
    TextView tvQiandaoIvDay1;
    @BindView(R.id.tv_qiandao_iv_day2)
    TextView tvQiandaoIvDay2;
    @BindView(R.id.tv_qiandao_iv_day3)
    TextView tvQiandaoIvDay3;
    @BindView(R.id.tv_qiandao_iv_day4)
    TextView tvQiandaoIvDay4;
    @BindView(R.id.tv_qiandao_iv_day5)
    TextView tvQiandaoIvDay5;
    @BindView(R.id.tv_qiandao_iv_day6)
    TextView tvQiandaoIvDay6;
    @BindView(R.id.tv_qiandao_iv_day7)
    TextView tvQiandaoIvDay7;
    @BindView(R.id.tv_recorde)
    VerticalRollingTextView tvRecorde;
    @BindView(R.id.btn_money)
    Button btnMoney;
    @BindView(R.id.ll_task_market)
    LinearLayout llRwsj;
    @BindView(R.id.listView_rw)
    MyListView listViewRw;
    @BindView(R.id.re_qiandao)
    LinearLayout reQiandao;
    @BindView(R.id.tv_reward)
    TextView tvReward;
    @BindView(R.id.tv_gold1)
    TextView tvGold1;
    @BindView(R.id.tv_gold2)
    TextView tvGold2;
    @BindView(R.id.tv_gold3)
    TextView tvGold3;
    @BindView(R.id.tv_gold4)
    TextView tvGold4;
    @BindView(R.id.tv_gold5)
    TextView tvGold5;
    @BindView(R.id.tv_gold6)
    TextView tvGold6;
    @BindView(R.id.tv_gold7)
    TextView tvGold7;
    @BindView(R.id.ll_record)
    RelativeLayout llRecord;
    private Drawable nav_up;
    private Drawable signin;
    private List<QiandaoBean.TaskListBean> datas;
    private TaskListViewAdapter taskListViewAdapter;
    private QiandaoBean.TaskListBean taskBean;
    private int p;
    private HashMap<String, String> map = new HashMap<>();
    private String reward;
    private List<String> recordList;
    private Dialog dialog;
    private QiandaoBean signBean;
    private TextView promptTv;
    private Drawable lineImage;
    private DataSetAdapter<String> dataSetAdapter;
    private TextView tvTitle;
    private Dialog signDialog;
    private TextView signTv;
    private MyApplictation application;
    private Drawable defaultLineImage;
    private boolean weChatShareFriends = true;
    private boolean weChatShareCircle = true;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_renwu;
    }

    @Override
    protected void initView() {
        tvBack.setText(R.string.task);
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        nav_up = getResources().getDrawable(R.mipmap.jinbi_duihao);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        signin = getResources().getDrawable(R.mipmap.btn_signin);
        signin.setBounds(0, 0, signin.getMinimumWidth(), signin.getMinimumHeight());
        lineImage = getResources().getDrawable(R.mipmap.signin_line);
        lineImage.setBounds(0, 0, 40, 3);
        defaultLineImage = getResources().getDrawable(R.mipmap.sign_line);
        defaultLineImage.setBounds(0, 0, 40, 3);

        taskListViewAdapter = new TaskListViewAdapter(datas, this);
        taskListViewAdapter.setListener(this);
        listViewRw.setAdapter(taskListViewAdapter);
        dataSetAdapter = new DataSetAdapter<String>(recordList) {

            @Override
            protected String text(String s) {
                return s;
            }
        };
        tvRecorde.setDataSetAdapter(dataSetAdapter);

    }

    @Override
    protected void initData() {
        getQianDao();
        getRecords();
    }

    private void getRecords() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        final Observable records = RetroFactory.getInstance().getRecords(map);
        records.compose(composeFunction).subscribe(new BaseObserver<RecodBean>(this, pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(RecodBean recodBean) {
                if (recodBean != null && recodBean.getWithdrawList() != null && recodBean.getWithdrawList().size() > 0) {
                    recordList = recodBean.getWithdrawList();
                    if (dataSetAdapter != null) {
                        dataSetAdapter.setData(recordList);
                    }
                    llRecord.setVisibility(View.VISIBLE);
                    tvRecorde.run();

                } else {
                    llRecord.setVisibility(View.GONE);
                }

            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.tv_qiandao, R.id.ll_task_market, R.id.btn_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_qiandao:
                setQIanDao();
                break;
            case R.id.btn_money:
                if (getTaskState()) {
                    Intent intent = new Intent(this, TiXianActivty.class);
                    intent.putExtra("money", 1);
                    intent.putExtra(Constant.SKIP_MARK,Constant.TASK);
                    startActivityForResult(intent,RESULT_OK);
                } else {
                    ToastUtil.showMessage("还有任务未完成哦");
                }

                break;
            case R.id.ll_task_market:
                startActivity(new Intent(this, IntegralActivity.class));
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.getBooleanExtra(Constant.WITHDRAWAL,false)) {
            btnMoney.setVisibility(View.GONE);
        }
    }

    private boolean getTaskState() {
        boolean flag = true;
        if (taskListViewAdapter != null) {
            List<QiandaoBean.TaskListBean> taskLists = taskListViewAdapter.getLists();
            if (taskLists != null && taskLists.size() > 0) {
                for (int i = 0; i < taskLists.size(); i++) {
                    if (taskLists.get(i).getIs() == 0) {
                        flag = false;
                        break;
                    }
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public void getQianDao() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        Observable logins = RetroFactory.getInstance().getQianDao(map);
        logins.compose(this.composeFunction).subscribe(new BaseObserver<QiandaoBean>(this, this.pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(QiandaoBean bean) {
                if (bean != null) {
                reQiandao.setVisibility(View.VISIBLE);
                tvQiandao.setVisibility(View.VISIBLE);
                signBean = bean;
                datas = bean.getTaskList();
                if (taskListViewAdapter != null) {
                    taskListViewAdapter.addAll(datas);
                }
                refreshBtn();
                    if (bean.getSignNums() != 0) {
                        switch (bean.getSignStatus()) {
                            case 0:
                                tvQiandao.setClickable(true);
                                break;
                            case 1:
                                tvQiandao.setClickable(false);
                                tvQiandao.setCompoundDrawables(null, signin, null, null);
                                break;
                        }
                        regreshDayWidget(bean.getSignNums(), false, 0);

                    }
                    if (bean.getSignAward() != null && bean.getSignAward().size() > 6) {
                        tvQiandaoDay1.setText("+" + bean.getSignAward().get(0) + "金币");
                        tvQiandaoDay2.setText("+" + bean.getSignAward().get(1) + "金币");
                        tvQiandaoDay3.setText("+" + bean.getSignAward().get(2) + "金币");
                        tvQiandaoDay4.setText("+" + bean.getSignAward().get(3) + "金币");
                        tvQiandaoDay5.setText("+" + bean.getSignAward().get(4) + "金币");
                        tvQiandaoDay6.setText("+" + bean.getSignAward().get(5) + "金币");
                        tvQiandaoDay7.setText("+" + bean.getSignAward().get(6) + "金币");
                    }
                    llRwsj.setVisibility(View.VISIBLE);
                    if (bean.getIsNewbie()==0){
                        btnMoney.setVisibility(View.GONE);
                    }else {
                        btnMoney.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onHandleError(int code, String message) {
            }
        });
    }

    /**
     * 将分转换为元
     *
     * @param value
     * @return
     */
    public String getMoney(int value) {
        return BigDecimal.valueOf(value).divide(new BigDecimal(100)).toString();
    }

    /**
     * 点击签到，刷新签到天数
     *
     * @param signNums
     */
    private void regreshDayWidget(int signNums, boolean flag, int m) {
        if (flag) {
            tvQiandao.setClickable(false);
            tvQiandao.setCompoundDrawables(null, signin, null, null);
            popupWindow(signNums, m, Constant.SIGN);
        }
        switch (signNums) {
            case 1:
                tvQiandaoDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvGold1.setCompoundDrawables(nav_up, null, defaultLineImage, null);
                break;
            case 2:
                tvQiandaoDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvGold1.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvGold2.setCompoundDrawables(nav_up, null, lineImage, null);
                break;
            case 3:
                tvQiandaoDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvGold1.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvGold2.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvGold3.setCompoundDrawables(nav_up, null, lineImage, null);
                break;
            case 4:
                tvQiandaoDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvGold1.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvGold2.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvGold3.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvGold4.setCompoundDrawables(nav_up, null, lineImage, null);
                break;
            case 5:
                tvQiandaoDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvGold1.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvGold2.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvGold3.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvGold4.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay5.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay5.setTextColor(getResources().getColor(R.color.codetext));
                tvGold5.setCompoundDrawables(nav_up, null, defaultLineImage, null);
                break;
            case 6:
                tvQiandaoDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvGold1.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvGold2.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvGold3.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvGold4.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay5.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay5.setTextColor(getResources().getColor(R.color.codetext));
                tvGold5.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay6.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay6.setTextColor(getResources().getColor(R.color.codetext));
                tvGold6.setCompoundDrawables(nav_up, null, defaultLineImage, null);
                break;
            case 7:
                tvQiandaoDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay1.setTextColor(getResources().getColor(R.color.codetext));
                tvGold1.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay2.setTextColor(getResources().getColor(R.color.codetext));
                tvGold2.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay3.setTextColor(getResources().getColor(R.color.codetext));
                tvGold3.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay4.setTextColor(getResources().getColor(R.color.codetext));
                tvGold4.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay5.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay5.setTextColor(getResources().getColor(R.color.codetext));
                tvGold5.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay6.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay6.setTextColor(getResources().getColor(R.color.codetext));
                tvGold6.setCompoundDrawables(nav_up, null, lineImage, null);
                tvQiandaoDay7.setTextColor(getResources().getColor(R.color.codetext));
                tvQiandaoIvDay7.setTextColor(getResources().getColor(R.color.codetext));
                tvGold7.setCompoundDrawables(nav_up, null, null, null);
                break;
        }
    }

    /**
     * 刷新领取1元现金的按钮
     */
    private void refreshBtn(){
        if (getTaskState()){
            btnMoney.setBackground(getResources().getDrawable(R.mipmap.button_hong));
        }else {
            btnMoney.setBackground(getResources().getDrawable(R.mipmap.button_hui));
        }
    }
    /**
     * 做完任务的接口
     */
    private void taskDone() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("m", new MD5().getMD5ofStr("ssd@#$%^&*!" + SharedPreferencesUtil.getID(this) + taskBean.getI()));
        map.put("id", taskBean.getI());
        Observable doneTask = RetroFactory.getInstance().doneTask(map);
        doneTask.compose(composeFunction).subscribe(new BaseObserver<JiangLiBean>(this, pd) {
            @Override
            protected void onHandleSuccess(JiangLiBean jiangLiBean) {
                if (taskListViewAdapter != null) {
                    taskListViewAdapter.update(p, 1);
                    refreshBtn();
                }
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });
    }

    /**
     * 点击领奖励调的接口
     */
    private void getReward() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("m", new MD5().getMD5ofStr("ssd@#%^&*!$" + SharedPreferencesUtil.getID(this) + taskBean.getI()));
        map.put("id", taskBean.getI());
        Observable rewardObservable = RetroFactory.getInstance().getReward(map);
        rewardObservable.compose(composeFunction).subscribe(new BaseObserver<JiangLiBean>(this, pd) {


            @Override
            protected void onHandleSuccess(JiangLiBean jiangLiBean) {
                //2代表完成任务
                //弹出窗口
                reward = getMoney(jiangLiBean.getM());
                popupWindow(0, 0, Constant.RECEIVEREWARD);
                if (taskListViewAdapter != null) {
                    taskListViewAdapter.update(p, 2);
                    refreshBtn();
                }
            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });
    }

    public void setQIanDao() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        Observable logins = RetroFactory.getInstance().setQianDao(map);
        logins.compose(this.composeFunction).subscribe(new BaseObserver<SignBean>(this, this.pd) {
            @Override
            protected void onHandleSuccess(SignBean signBean) {
                if (signBean != null) {
                    regreshDayWidget(signBean.getSignDays(), true, signBean.getM());
                }
            }

            @Override
            public void onHandleError(int code, String message) {
            }
        });
    }

    /**
     * 任务列表中按钮的点击事件
     *
     * @param taskListBean
     */
    @Override
    public void taskOnClick(QiandaoBean.TaskListBean taskListBean, int position) {
        taskBean = taskListBean;
        p = position;

        int state = taskListBean.getIs();
        if (state == 0 || state == 2) {
            switch (taskListBean.getI()) {
                case 1:
                    Intent intent1 = new Intent(this, GGWed.class);
                    intent1.putExtra("url", "http://news.tianh5.cn/news/get_money.html");
                    intent1.putExtra(Constant.TITLE,getString(R.string.make_money_tutorial));
                    if (state == 0) {
                        intent1.putExtra("id", taskBean.getI());
                        startActivityForResult(intent1, 1);
                    } else {
                        startActivity(intent1);
                    }
                    break;
                case 2:
                    Intent intent = new Intent(this, MyInfoActivity.class);
                    if (state == 0) {
                        intent.putExtra("id", taskBean.getI());
                        startActivityForResult(intent, 2);
                    } else {
                        startActivity(intent);
                    }
                    break;
                case 3:
//                    startActivityForResult(new Intent(this, YYXZActivity.class), 3);
//                    finish();
                    Intent intent3 = new Intent(this, IntegralActivity.class);
                    if (state == 0) {
                        intent3.putExtra(Constant.SKIP_MARK, Constant.TASK_DOWNLOAD);
                        startActivityForResult(intent3, 3);
                    } else {
                        startActivity(intent3);
                    }

                    break;
                case 4:
                    if (weChatShareFriends){
                        weChatShareFriends(state);
                    }
                    weChatShareFriends= false;
                    break;
                case 5:
                    if (weChatShareCircle){
                        weChatShareCircle(state);
                    }
                    weChatShareCircle = false;
                    break;
            }
        } else {
            //调用领取奖励的接口
            getReward();
        }
    }

    private void weChatShareCircle(final int state) {
        map.put("type", "朋友圈");
        WechatMoments.ShareParams weibo = new WechatMoments.ShareParams();
        weibo.setShareType(Platform.SHARE_WEBPAGE);
        weibo.title = getResources().getString(R.string.app_name);
        weibo.setComment("送你2元现金，想领取点此下载，填写邀请码" + SharedPreferencesUtil.getID(this) + "，红包会更多");
        weibo.text = "看资讯拿红包，想领取就点此下载，邀请码" + SharedPreferencesUtil.getID(this);
        weibo.imageUrl = getResources().getString(R.string.shareimage_toutiao);
        weibo.url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363881185053";
        Platform weibos = ShareSDK.getPlatform(WechatMoments.NAME);
        weibos.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                MobclickAgent.onEvent(RenWuActivity.this, "click_invite", map);
                //分享成功的回调
                if (state == 0) {
                    taskDone();//分享成功之后通知服务器完成
                }
                weChatShareCircle = true;
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                weChatShareCircle = true;

            }

            @Override
            public void onCancel(Platform platform, int i) {
                weChatShareCircle = true;
            }
        }); // 设置分享事件回调
        weibos.share(weibo);
    }

    //微信分享
    private void weChatShareFriends(final int state) {
        map.put("feed", "微信");
        Wechat.ShareParams wexin = new Wechat.ShareParams();
        wexin.setShareType(Platform.SHARE_WEBPAGE);
        wexin.title = getResources().getString(R.string.app_name);
        wexin.setComment("送你2元现金，想领取点此下载，填写邀请码" + SharedPreferencesUtil.getID(this) + "，红包会更多");
        wexin.text = "一次玩这货瞬间到手2元！这个APP真牛X，记得填写我的邀请码" + SharedPreferencesUtil.getID(this) + "，奖励瞬间到账。";
        wexin.imageUrl = getResources().getString(R.string.shareimage_toutiao);
        wexin.url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.tianguo.zxz&ckey=CK1363789133793";
        Platform Weixin = ShareSDK.getPlatform(Wechat.NAME);
        Weixin.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                weChatShareFriends= true;
            }

            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                MobclickAgent.onEvent(RenWuActivity.this, "click_invite", map);
                LogUtils.e("朋友分享成功了");
                if (state == 0) {
                    taskDone();
                }
                weChatShareFriends= true;
            }

            public void onCancel(Platform arg0, int arg1) {
                LogUtils.e(arg0 + "" + arg1 + "" + "www3ww");
                //取消分享的回调
                weChatShareFriends= true;
            }
        });
        Weixin.share(wexin);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (resultCode) {
                case 1:
                    boolean flag = data.getBooleanExtra(Flat.REQUESTFLAG, false);
                    if (flag) {
                        LogUtils.e("如何赚钱回来了   哈哈哈 ");
                        if (taskListViewAdapter != null) {
                            taskListViewAdapter.update(p, 1);
                            refreshBtn();
                        }
                    }
                    LogUtils.e("如何赚钱回来了    ");
                    break;
                case 2:
                    LogUtils.e("完善个人信息回来了    ");
                    if (taskListViewAdapter != null) {
                        taskListViewAdapter.update(p, data.getIntExtra(Flat.TASK_FLAG, 0));
                        refreshBtn();
                    }
                    break;
                case 3://第三个任务和任务集市
                    LogUtils.e("完成0.6元下载任务    ");
                    getQianDao();
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            setDialogNull(dialog);
            setDialogNull(signDialog);
            application.removeActivity(this);
        } catch (Exception e) {

        }

    }

    private void setDialogNull(Dialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }


    private void popupWindow(int signNums, int m, int flag) {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog);
            View view = View.inflate(this, R.layout.sign_prompt_box, null);
            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            promptTv = (TextView) view.findViewById(R.id.tv_content);
            tvTitle = (TextView) view.findViewById(R.id.tv_sign);
            dialog.setContentView(view);
        }
        if (signDialog == null) {
            signDialog = new Dialog(this, R.style.dialog);
            View view = View.inflate(this, R.layout.sign_box, null);
            view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signDialog.dismiss();
                }
            });
            view.findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signDialog.dismiss();
                    //跳转到变现猫
                    String url = "http://m.bianxianmao.com?appKey=f43d279cc93549888ad308098db31d2a&appType=app&appEntrance=1&business=money&i=__IMEI__&f=__IDFA__";
                    url = url.replace("__IMEI__", AppInfoUtil.getIMEI(RenWuActivity.this));
//                    url = url.replace("__IDFA__","idfa");
                    Intent intent = new Intent(RenWuActivity.this, SoWebActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra(Constant.NAV, "bianxianmao");
                    startActivity(intent);
                }
            });
            signTv = (TextView) view.findViewById(R.id.tv_content);
            signDialog.setContentView(view);
        }

        switch (flag) {
            case Constant.SIGN:
//                tvTitle.setText("签到成功");
                if (signNums != 7) {
                    LogUtils.e("签到天数" + signNums);
                    signTv.setText("今天送您" + signBean.getSignAward().get(signNums - 1) + "金币\n明天继续可以得到" + signBean.getSignAward().get(signNums) + "金币哦");
                    //改变金币的字体大小和颜色
                    String text = signTv.getText().toString();
                    int fstart = 0;
                    int fend = 0;
                    if (text.contains("您")) {
                        fstart = text.indexOf("您") + 1;
                    }
                    if (text.contains("金币")) {
                        fend = text.indexOf("金币");
                    }
                    SpannableStringBuilder style = new SpannableStringBuilder(text);
                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#0098fe")), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    style.setSpan(new AbsoluteSizeSpan(30, true), fstart, fend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    signTv.setText(style);
                } else {
                    signTv.setText("您已连续签到7天,签到积分变身现金红包\n" +
                            "您获得现金红包" + getMoney(m) + "元！");
                }
                signDialog.show();
                break;
            case Constant.RECEIVEREWARD:
                tvTitle.setText("领取成功");
                promptTv.setText("已成功领取任务奖励" + reward + "元");
                dialog.show();
                break;
        }

    }

}
