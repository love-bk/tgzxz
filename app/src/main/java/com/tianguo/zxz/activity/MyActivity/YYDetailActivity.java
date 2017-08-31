package com.tianguo.zxz.activity.MyActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.MylistTaskAdpater;
import com.tianguo.zxz.adapter.YYXZDetailItemAdapter;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.YYdetallBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.serviec.LocalService;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.DownListnerUtils;
import com.tianguo.zxz.uctils.IntegralallWUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.MD5;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;
import com.tianguo.zxz.view.MyListView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class YYDetailActivity extends BaseActivity {
    @BindView(R.id.tv_shuoming)
    TextView tv_shuoming;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_yy_icon)
    ImageView ivYyIcon;
    @BindView(R.id.tv_teile)
    TextView tvTeile;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_content2)
    TextView tvContent2;
    @BindView(R.id.tv_gf)
    TextView tvGf;
    @BindView(R.id.tv_aq)
    TextView tvAq;
    @BindView(R.id.my_list_task)
    MyListView myListTask;
    @BindView(R.id.detail_recyclerView)
    RecyclerView detailRecyclerView;
    @BindView(R.id.tv_rjjs_content)
    TextView tvRjjsContent;
    @BindView(R.id.btn_download)
    RadioButton btnDownload;
    /**
     * 是否正在下载
     */
    @SuppressLint("HandlerLeak")
    private IntentFilter paramBundle;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public final void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {
            LogUtils.e("ssssssssssss");
            if (paramAnonymousContext.getPackageName().equals(paramAnonymousIntent.getPackage())) {
                if ("tianguo.intent.action.POINT_NOTIFY.EVENT".equals(paramAnonymousIntent.getAction())) {
                    LogUtils.e(paramAnonymousIntent.getBooleanExtra("isovber", false) + "");
                    if (paramAnonymousIntent.getBooleanExtra("isovber", false)) {
                        endTime(paramAnonymousIntent.getIntExtra("id",-1));
                    }
                    return;
                }
            }
        }
    };
    private List<String> list;
    private YYXZDetailItemAdapter adapter;
    private List<YYdetallBean.TaskListBean> otherTask;
    private MylistTaskAdpater mylistTaskAdpater;
    private YYdetallBean.TaskBean task;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_yydetail;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {

        getYYxq();

    }

    private void endTime(int id) {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("m", new MD5().getMD5ofStr("ssd@#$%^*!@#" + SharedPreferencesUtil.getID(this) + getIntent().getIntExtra("id", -1)).substring(12, 20));
        map.put("sso", SharedPreferencesUtil.getSSo(this));
                map.put("id", id);
        Observable observable = RetroFactory.getInstance().getEndTime(map);
        observable.compose(composeFunction).subscribe(new BaseObserver<YYdetallBean>(this, pd) {
                                                          @Override
                                                          protected void onHandleSuccess(YYdetallBean yYdetallBean) {
                                                              PendingIntent pendingIntent3 = PendingIntent.getActivity(YYDetailActivity.this, 0,
                                                                      new Intent(YYDetailActivity.this, YYXZActivity.class), 0);
                                                              @SuppressLint("WrongConstant")
                                                              NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                              Notification builder = new Notification.Builder(YYDetailActivity.this)
                                                                      .setSmallIcon(R.mipmap.zhuanlogo)
                                                                      .setTicker(getResources().getString(R.string.app_name))
                                                                      .setContentTitle("获得奖励")
                                                                      .setContentText("您已获得" + task.getMo() / 100 + "元")
                                                                      .setContentIntent(pendingIntent3)
                                                                      .setNumber(1).build();
                                                              builder.flags |= Notification.FLAG_AUTO_CANCEL;
                                                              nm.notify(1, builder);
                                                          }
                                                      }
        );
    }

    private void startTime(int id) {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("m", new MD5().getMD5ofStr("ssd@#$%^*!@#" + SharedPreferencesUtil.getID(this) + getIntent().getIntExtra("id", -1)).substring(12, 20));
                map.put("id",id);
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        Observable observable = RetroFactory.getInstance().getStartTime(map);
        observable.compose(composeFunction).subscribe(new BaseObserver<YYdetallBean>(this, pd) {
                                                          @Override
                                                          protected void onHandleSuccess(YYdetallBean yYdetallBean) {

                                                          }
                                                      }
        );
    }


    private void setDownxq(int id) {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        map.put("id", id);
        map.put("m", new MD5().getMD5ofStr("ssd@#$%^*!@#" + SharedPreferencesUtil.getID(this) + id).substring(12, 20));
        Observable observable = RetroFactory.getInstance().getDown(map);
        observable.compose(composeFunction).subscribe(new BaseObserver<YYdetallBean>(this, pd) {
            @Override
            protected void onHandleSuccess(YYdetallBean yYdetallBean) {
                LogUtils.e("sssssss");
                task.setIs_download(1);
                if (IntegralallWUtils.openApp(YYDetailActivity.this, getIntent().getStringExtra("packname"))) {
                    Intent paramAnonymousView = new Intent(YYDetailActivity.this, LocalService.class);
                    paramAnonymousView.setAction("tianguo.intent.action.START.COUNTTIMER");
                    paramAnonymousView.putExtra("packageName", getIntent().getStringExtra("packname"));
                    paramAnonymousView.putExtra("millisInFuture", otherTask.get(0).getDuration());
                    paramAnonymousView.putExtra("elapsedRealtime", SystemClock.elapsedRealtime());
                    startService(paramAnonymousView);
                }
            }
        });
    }

    private void getYYxq() {
        setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        LogUtils.e(getIntent().getIntExtra("id", -1) + "sssss");
        LogUtils.e(SharedPreferencesUtil.getSSo(this) + "sssss");
        map.put("id", getIntent().getIntExtra("id", -1));
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable observable = RetroFactory.getInstance().getYYAddXq(map);
        observable.compose(composeFunction).subscribe(new BaseObserver<YYdetallBean>(this, pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(YYdetallBean yyListBean) {
                task = yyListBean.getTask();
                otherTask = yyListBean.getTaskList();
                list = yyListBean.getTask().getIm();
                Glide.with(getApplicationContext()).load(yyListBean.getTask().getIc()).into(ivYyIcon);
                adapter = new YYXZDetailItemAdapter(list, YYDetailActivity.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(YYDetailActivity.this, LinearLayout.HORIZONTAL, false);
                detailRecyclerView.setLayoutManager(layoutManager);
                detailRecyclerView.setAdapter(adapter);
                mylistTaskAdpater = new MylistTaskAdpater(YYDetailActivity.this, otherTask);
                myListTask.setAdapter(mylistTaskAdpater);
                tvTeile.setText(yyListBean.getTask().getTi());
                tvContent.setText(yyListBean.getTask().getSi() + "M" + "      " + yyListBean.getTask().getDownload_num() + "万人下载");
                SpannableString ss = new SpannableString("总奖励  " + yyListBean.getTask().getMo() + "万");
                ForegroundColorSpan fsp = new ForegroundColorSpan(Color.GREEN);
                ss.setSpan(fsp, 3, ss.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvContent2.setText(ss);
                SpannableString s2s = new SpannableString("还有" + (yyListBean.getTask().getNum() == 0 ? "" : yyListBean.getTask().getNum()) + "个任务");
                ForegroundColorSpan f2sp = new ForegroundColorSpan(Color.RED);
                ss.setSpan(f2sp, 2, ss.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvContent2.setText(ss);
                if (getIntent().getIntExtra("status", 2) != 2) {
                    tv_shuoming.setText("任务说明" + "\n" + s2s);
                    if (yyListBean.getTask().getNum() == 0) {
                        btnDownload.setText("已抢完");
                        btnDownload.setBackgroundColor(getResources().getColor(R.color.xian));
                    }
                }
                if (!DownListnerUtils.checkApkExist(YYDetailActivity.this, getIntent().getStringExtra("packname")) && yyListBean.getTask().getIs_download() == 1) {
                    btnDownload.setText("重新安装");
                    btnDownload.setTag(1);
                    btnDownload.setBackgroundColor(getResources().getColor(R.color.xian));
                } else if (DownListnerUtils.checkApkExist(YYDetailActivity.this, getIntent().getStringExtra("packname"))) {
                    btnDownload.setText("继续体验");
                    btnDownload.setTag(0);
                } else {
                    btnDownload.setText("立即安装");
                    btnDownload.setTag(1);
                }
                if (yyListBean.getTask().getMa() != null && yyListBean.getTask().getMa().size() != 0) {
                    for (Integer integer : yyListBean.getTask().getMa()) {
                        int i = integer;
                        switch (i) {
                            case 1://官方
                                tvGf.setVisibility(View.VISIBLE);
                                break;
                            case 2://安全
                                tvAq.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                }
            }

        });
    }

    @Override
    public void onResume() {
        if (DownListnerUtils.checkApkExist(this, getIntent().getStringExtra("packname"))) {
            btnDownload.setText("继续体验");
            btnDownload.setTag(0);
        } else {
            btnDownload.setText("立即安装");
            btnDownload.setTag(1);
        }
        super.onResume();
    }

    @Override
    protected void initData() {


    }


    @OnClick({R.id.btn_download, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                if (!IntegralallWUtils.checkUsagePermission(YYDetailActivity.this)) {
                    return;
                }
                if ((int) btnDownload.getTag() == 0) {
                    if (task.getIs_download() == 0) {
                        ToastUtil.showMessage("您使用的app不是在本平台下载的哦，请卸载后重新下载才能领取奖励");
                        return;
                    } else {
                        if (IntegralallWUtils.openApp(YYDetailActivity.this, getIntent().getStringExtra("packname"))) {
                            Intent paramAnonymousView = new Intent(YYDetailActivity.this, LocalService.class);
                            paramAnonymousView.setAction("tianguo.intent.action.START.COUNTTIMER");
                            paramAnonymousView.putExtra("packageName", getIntent().getStringExtra("packname"));
                            paramAnonymousView.putExtra("elapsedRealtime", SystemClock.elapsedRealtime());
                            for (int i = 0; i < otherTask.size(); i++) {
                                if (otherTask.get(i).getType() == 1&&otherTask.get(i).getStatus()==0) {
                                    paramAnonymousView.putExtra("millisInFuture", otherTask.get(i).getDuration());
                                    paramAnonymousView.putExtra("id",otherTask.get(i).getId());
                                    startTime(otherTask.get(i).getId());
                                    startService(paramAnonymousView);
                                    LogUtils.e("ssssssss");
                                    break;
                                }

                            }

                        }

                    }
                } else {
                    if (task.getIs_download() == 0) {
                        setDownxq(getIntent().getIntExtra("id", -1));
                    }
                    Intent intent = new Intent(YYDetailActivity.this, DownGGUtils.class);
                    intent.putExtra("downloadurl", task.getU());
                    intent.putExtra("teile", task.getTi());
                    intent.putExtra("descrption", task.getB());
                    intent.putExtra("packageName", getIntent().getStringExtra("packname"));
                    intent.putExtra("flag", true);
                    intent.putExtra("yyId", task.getI());
                    intent.putExtra("isdownload", task.getIs_download());
                    startService(intent);
                }

                break;
            case R.id.tv_title:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramBundle = new IntentFilter();
        paramBundle.addAction("tianguo.intent.action.POINT_NOTIFY.EVENT");
        registerReceiver(receiver, paramBundle);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
