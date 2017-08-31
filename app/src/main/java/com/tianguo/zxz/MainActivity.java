package com.tianguo.zxz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tianguo.zxz.activity.newsLoginActivity;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.fragment.MainFragment;
import com.tianguo.zxz.fragment.ManyerFragment;
import com.tianguo.zxz.fragment.MyFragment;
import com.tianguo.zxz.fragment.NewsWebFragment;
import com.tianguo.zxz.uctils.AndroidIp;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_main_fr)
    FrameLayout flMainFr;
    @BindView(R.id.tv_main_zixun)
    RadioButton tvMainZixun;
    @BindView(R.id.tv_main_void)
    RadioButton tvMainVoid;
    @BindView(R.id.tv_main_so)
    RadioButton tvMainSo;
    @BindView(R.id.tv_main_me)
    RadioButton tvMainMe;
    @BindView(R.id.rg_bottom_tab)
    RadioGroup rgBottomTab;
    private MainFragment mainOne;
    private FragmentTransaction fragmentTransaction;
    private NewsWebFragment maintwo;
    private MyFragment mainthree;
    private ManyerFragment mainfore;
    LogingListenr logingListenr;
    private Dialog dialog;
    private HashMap<String, String> map;
    private MyApplictation application;
    private Dialog hbDialog;

    boolean isFlag = true;
    private int NUM;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
    HomeIsSHowListner listner;

    public void setOnHomeShowListner(HomeIsSHowListner listner) {
        this.listner = listner;
    }

    public void setNUM(int NUM) {
        this.NUM = NUM;
    }

    public int getNUM() {
        return NUM;
    }

    public interface HomeIsSHowListner {
        void OnHomeIsHowListner();
    }


    @Override
    protected void initView() {
        application = (MyApplictation) getApplication();
        application.init();
        application.addActivity(this);
        try {

            new Thread() {
                @Override
                public void run() {
                    SharedPreferencesUtil.saveV4IP(MainActivity.this, AndroidIp.GetNetIp());
                }
            }.start();
            map = new HashMap<>();
            if (SharedPreferencesUtil.getSSo(this).isEmpty()) {
                startActivityForResult(new Intent(this, newsLoginActivity.class), 201);
                finish();
                return;
            }
            /**
             * t弹出绑定传
             */
            View inflate = View.inflate(this, R.layout.dialog_bangding, null);
            inflate.findViewById(R.id.tv_diog_true).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog = new Dialog(this, R.style.dialog);
            dialog.setContentView(inflate);
            Intent intent = getIntent();
            if (intent.getBooleanExtra("isBangding", false)) {
                dialog.show();
            }else {
                if (SharedPreferencesUtil.getIsNew(this)) {
                    hbHint();
                }
            }
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                if (SharedPreferencesUtil.getIsNew(MainActivity.this)) {
                    hbHint();
                }
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (SharedPreferencesUtil.getIsNew(MainActivity.this)) {
                        hbHint();
                    }
                }
            });

            mainOne = new MainFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fl_main_fr, mainOne).commit();

        }catch (Exception e){

        }

    }


    @Override
    protected void initData() {
    }


    @OnClick({R.id.tv_main_zixun, R.id.tv_main_void, R.id.tv_main_me, R.id.tv_main_so})
    public void onViewClicked(View view) {
        try {
            if (null == map) {
                map = new HashMap<>();
            }
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (view.getId()) {
                case R.id.tv_main_zixun:

                    mainOne.newBieGuide();
                    skipNews(fragmentTransaction);
                    break;
                case R.id.tv_main_void:

                    map.put("click_home_nav", "视频");
                    if (SharedPreferencesUtil.getSSo(this).isEmpty()) {
                        startActivityForResult(new Intent(this, newsLoginActivity.class), 201);
                        tvMainZixun.setClickable(true);
                        return;
                    }
                    if (mainfore != null && !mainfore.isHidden()) {
                        fragmentTransaction.hide(mainfore);
                    }
                    if (mainOne != null && !mainOne.isHidden()) {
                        fragmentTransaction.hide(mainOne);
                    }
                    if (mainthree != null && !mainthree.isHidden()) {
                        fragmentTransaction.hide(mainthree);
                    }
                    if (maintwo == null) {
                        maintwo = new NewsWebFragment();
//                        maintwo = new VideoFragment();
                        fragmentTransaction.add(R.id.fl_main_fr, maintwo).commit();
                    } else {
                        fragmentTransaction.show(maintwo).commit();
                    }
                    break;
                case R.id.tv_main_me:
                    skipMy(fragmentTransaction);
                    break;
                case R.id.tv_main_so:
                    skipInvitedMmakeMoney(fragmentTransaction);
                    break;
            }
            MobclickAgent.onEvent(MainActivity.this, "home", map);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }


    private void skipMy(FragmentTransaction fragmentTransaction){
        map.put("click_home_nav", "我的");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.codetext));
        }
        if (SharedPreferencesUtil.getSSo(this).isEmpty()) {
            tvMainZixun.setClickable(true);
            startActivityForResult(new Intent(this, newsLoginActivity.class), 201);
            return;
        }
        if (mainOne != null && !mainOne.isHidden()) {
            fragmentTransaction.hide(mainOne);
        }
        if (maintwo != null && !maintwo.isHidden()) {
            fragmentTransaction.hide(maintwo);
        }
        if (mainfore != null && !mainfore.isHidden()) {
            fragmentTransaction.hide(mainfore);
        }
        if (mainthree == null) {
            mainthree = new MyFragment();
            fragmentTransaction.add(R.id.fl_main_fr, mainthree).commit();
        } else {
            fragmentTransaction.show(mainthree).commit();
        }
    }


    private void skipInvitedMmakeMoney(FragmentTransaction fragmentTransaction) {
        map.put("click_home_nav", "邀请");
        if (SharedPreferencesUtil.getSSo(this).isEmpty()) {
            tvMainZixun.setClickable(true);
            startActivityForResult(new Intent(this, newsLoginActivity.class), 201);
            return;
        }
        if (mainOne != null && !mainOne.isHidden()) {
            fragmentTransaction.hide(mainOne);
        }
        if (maintwo != null && !maintwo.isHidden()) {
            fragmentTransaction.hide(maintwo);
        }
        if (mainthree != null && !mainthree.isHidden()) {
            fragmentTransaction.hide(mainthree);
        }
        if (mainfore == null) {
            mainfore = new ManyerFragment();
            fragmentTransaction.add(R.id.fl_main_fr, mainfore).commit();
        } else {
            fragmentTransaction.show(mainfore).commit();
        }
    }

    private void skipNews(FragmentTransaction fragmentTransaction) {
        map.put("click_home_nav", "主页");
        if (mainfore != null && !mainfore.isHidden()) {
            fragmentTransaction.hide(mainfore);
        }
        if (maintwo != null && !maintwo.isHidden()) {
            fragmentTransaction.hide(maintwo);
        }
        if (mainthree != null && !mainthree.isHidden()) {
            fragmentTransaction.hide(mainthree);
        }
        if (mainOne == null) {
            mainOne = new MainFragment();
            fragmentTransaction.add(R.id.fl_main_fr, mainOne).commit();
        } else {
            fragmentTransaction.show(mainOne).commit();
        }
    }
    //设置点击head跳转
    public RadioButton getYoaing() {
        return tvMainZixun;
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @SuppressLint("WrongConstant")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KEYCODE_VOLUME_DOWN:
                if (onMusicListner != null) {
                    onMusicListner.onmusiclistner();
                }
                return false;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (onMusicListner != null) {
                    onMusicListner.onmusiclistner();
                }
                return false;
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                LogUtils.e("MUTE");
                return false;
        }

        if (keyCode == KEYCODE_BACK && event.getAction() == ACTION_DOWN) {
            if (null != onIsBackListner && onIsBackListner.OnBackListner()) {
                return true;
            }
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtil.showMessage("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 处理音量拦截
     */
    public void setOnMicListner(OnMusicListner onMicListner) {
        this.onMusicListner = onMicListner;
    }

    OnMusicListner onMusicListner;

    public interface OnMusicListner {
        void onmusiclistner();
    }

    /**
     * 当前web是否可以返回
     */
    OnIsBackListner onIsBackListner;

    public interface OnIsBackListner {
        boolean OnBackListner();
    }

    public void setOnIsBackListner(OnIsBackListner onIsBackListner) {
        this.onIsBackListner = onIsBackListner;
    }

    /**
     * 是否登录 登录刷新页面回调
     */
    public interface LogingListenr {
        void yesLogingListenr();
    }

    public void setLogingListenr(LogingListenr logingListenr) {
        this.logingListenr = logingListenr;
    }


    @Override
    protected void onDestroy() {
        try{
            try{

                application.removeActivity(this);
            }catch (Exception e){

            }
            if (dialog!=null){
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                dialog = null;
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e("进来了哈哈哈哈哈哈","gjj");
        if (resultCode == 201) {
            logingListenr.yesLogingListenr();
        }
        if (resultCode == 202) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (map != null) {
                map.put("type", "主页");
            }
            tvMainZixun.setClickable(true);
            tvMainZixun.setChecked(true);
            if (mainfore != null && !mainfore.isHidden()) {
                fragmentTransaction.hide(mainfore);
            }
            if (maintwo != null && !maintwo.isHidden()) {
                fragmentTransaction.hide(maintwo);
            }
            if (mainthree != null && !mainthree.isHidden()) {
                fragmentTransaction.hide(mainthree);
            }
            try {
                if (mainOne == null) {
                    mainOne = new MainFragment();
                    fragmentTransaction.add(R.id.fl_main_fr, mainOne).commitAllowingStateLoss();
                } else {
                    fragmentTransaction.show(mainOne).commitAllowingStateLoss();
                }
            } catch (Exception e) {
            }

            if (listner != null) {
                listner.OnHomeIsHowListner();

            }
            ToastUtil.showMessage("点击红包新闻阅读新闻可以获得红包");
        }
        if (resultCode == 204) {
            try {
                if (sclistner != null) {
                    if (data != null) {
                        sclistner.isBoutto(data.getIntExtra("position", -1));
                    }
                }
            } catch (Exception e) {

            }
        }

        if (resultCode == 205) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (map != null) {
                map.put("type", "主页");
            }

            if (mainOne == null) {
                mainOne = new MainFragment();
                fragmentTransaction.add(R.id.fl_main_fr, mainOne).commitAllowingStateLoss();
            } else {
                fragmentTransaction.show(mainOne).commitAllowingStateLoss();
            }
            mainOne.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData();
    }

    private void processExtraData() {
        Intent intent = getIntent();
        switch (intent.getIntExtra("type", -1)) {
            case 1:
                tvMainZixun.setChecked(true);
                skipNews(getSupportFragmentManager().beginTransaction());
                break;
            case 2:
                tvMainSo.setChecked(true);
                skipInvitedMmakeMoney(getSupportFragmentManager().beginTransaction());
                break;
            case 3:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (map != null) {
                    map.put("type", "主页");
                }
                tvMainZixun.setClickable(true);
                tvMainZixun.setChecked(true);
                if (mainfore != null && !mainfore.isHidden()) {
                    fragmentTransaction.hide(mainfore);
                }
                if (maintwo != null && !maintwo.isHidden()) {
                    fragmentTransaction.hide(maintwo);
                }
                if (mainthree != null && !mainthree.isHidden()) {
                    fragmentTransaction.hide(mainthree);
                }
                try {
                    if (mainOne == null) {
                        mainOne = new MainFragment();
                        fragmentTransaction.add(R.id.fl_main_fr, mainOne).commitAllowingStateLoss();
                    } else {
                        fragmentTransaction.show(mainOne).commitAllowingStateLoss();
                    }
                } catch (Exception e) {
                }

                if (listner != null) {
                    listner.OnHomeIsHowListner();

                }
                ToastUtil.showMessage("点击红包新闻阅读新闻可以获得红包");
                break;
        }
    }


    OnScllorListner sclistner;

    public interface OnScllorListner {
        void isBoutto(int postion);
    }

    public void setScllorlistner(OnScllorListner sclistner) {
        this.sclistner = sclistner;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void hbHint(){
        if (hbDialog == null) {
            hbDialog = new Dialog(this, R.style.dialog);
            View view = View.inflate(this, R.layout.new_user_bg_dialog, null);
            final ImageView ivHbApart = (ImageView) view.findViewById(R.id.iv_hb_apart);
            ivHbApart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFlag){
                        /** 设置旋转动画 */
                        ivHbApart.setImageResource(R.mipmap.new_hb);
                        ScaleAnimation animation =new ScaleAnimation(0.0f, 1f, 0.0f, 1f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(500);//设置动画持续时间
                        animation.setInterpolator(new AccelerateInterpolator());
                        animation.setRepeatCount(0);//设置重复次数
                        ivHbApart.setAnimation(animation);
                        animation.startNow();
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                isFlag = false;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }else {
                        //跳转到我的
                        tvMainMe.setChecked(true);
                        skipMy(getSupportFragmentManager().beginTransaction());
                        hbDialog.dismiss();
                    }
                }
            });
            hbDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    //处理监听事件
                    SharedPreferencesUtil.saveIsNew(MainActivity.this,false);
                    if (mainOne != null&&!mainOne.isHidden())
                        mainOne.newBieGuide();
                    LogUtils.e("对话框隐藏了","gjj");
                }
            });
            hbDialog.setContentView(view);

        }
        hbDialog.show();
    }

}
