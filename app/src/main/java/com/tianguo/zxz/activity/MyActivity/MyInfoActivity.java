package com.tianguo.zxz.activity.MyActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.Flat;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.bean.StudentListBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.PermissionsUtils;
import com.tianguo.zxz.uctils.PhoneCodeUtiles;
import com.tianguo.zxz.uctils.ResendCodeTimer;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;
import com.tianguo.zxz.uctils.UpdateAppUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by lx on 2017/6/21.
 */

public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_my_head)
    ImageView ivMyHead;
    @BindView(R.id.iv_my_id)
    TextView ivMyId;
    @BindView(R.id.iv_my_phone)
    TextView ivMyPhone;
    @BindView(R.id.ll_my_phone)
    RelativeLayout llMyPhone;
    @BindView(R.id.iv_my_women)
    TextView ivMyWomen;
    @BindView(R.id.ll_my_women)
    RelativeLayout llMyWomen;
    @BindView(R.id.tv_my_yers)
    TextView tvMyYers;
    @BindView(R.id.ll_my_yers)
    RelativeLayout llMyYers;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private String men;
    private Dialog women;
    private DatePickerDialog dlg;
    private Bitmap head;
    private PopupWindow window;
    private String fileName;
    private File file;
    private String base = "";
    private String birthday = "未知";
    private String phone = "";
    private String wechatId = "";
    private int sex;
    private String mysex = "未知";
    private int flag = 0;
    private int taskBeanId;
    private StudentListBean infoBean;
    private Intent intent;
    private RadioButton rb_men;
    private RadioButton rb_women;
    private TextView tvCancle;
    private TextView tvConfirm;
    private EditText etBangdingPhone;
    private EditText etBangdingYanzheng;
    private Button btBangding;
    private TextView tvBangdingYanzheng;
    private ResendCodeTimer timer;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_info;
    }

    final Calendar calendar = Calendar.getInstance();
    @SuppressLint("WrongConstant")
    int yy = calendar.get(Calendar.YEAR);
    @SuppressLint("WrongConstant")
    int mm = calendar.get(Calendar.MONTH);
    @SuppressLint("WrongConstant")
    int dd = calendar.get(Calendar.DAY_OF_MONTH);
    private static String path = "/sdcard/myHead/";// sd路径
    ByteArrayOutputStream b = null;

    private Dialog dialog;

    @Override
    protected void initView() {
        tvBack.setText(R.string.account_info);
        timer = new ResendCodeTimer(this, false);
        taskBeanId = getIntent().getIntExtra("id", 0);
        intent = new Intent(MyInfoActivity.this, SetPassWordActivity.class);
        setLoadingFlag(false);
        ivMyId.setText(SharedPreferencesUtil.getID(this) + "");
        final View pop = View.inflate(this, R.layout.pop_slece_image, null);
        window = new PopupWindow(this);
        window.setContentView(pop);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.take_photo_anim);
        getInfo();
        pop.findViewById(R.id.tv_cream_heam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionsUtils.hasPermission(MyInfoActivity.this, Manifest.permission.CAMERA)) {
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                    startActivityForResult(intent2, 2);// 采用ForResult打开
                } else {
                    ToastUtil.showMessage("请开启相机权限");
                    PermissionsUtils.requestPermission(MyInfoActivity.this, 0, Manifest.permission.CAMERA);
                }

            }
        });
        pop.findViewById(R.id.tv_photo_heam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                window.dismiss();

            }
        });


        View inflate = View.inflate(this, R.layout.dialog_my_wumen, null);
        women = new Dialog(this, R.style.dialog);
        women.setContentView(inflate);
        rb_men = (RadioButton) inflate.findViewById(R.id.rb_men);
        rb_women = (RadioButton) inflate.findViewById(R.id.rb_women);
        tvCancle = (TextView) inflate.findViewById(R.id.tv_cancle);
        tvConfirm = (TextView) inflate.findViewById(R.id.tv_confirm);
        tvCancle.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        dlg = new DatePickerDialog(new ContextThemeWrapper(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int i = month + 1;
                tvMyYers.setText(year + "-" + i + "-" + dayOfMonth);
            }
        }, yy, mm, dd);
        dlg.setTitle("请选择出生年月");


        //绑定手机号
        bindPhoneNumber();
    }

    private void bindPhoneNumber() {
        View inflate = View.inflate(this, R.layout.dialog_bangding_phone, null);
        etBangdingPhone = (EditText) inflate.findViewById(R.id.et_bangding_phone);
        TextView tvBindTitle = (TextView) inflate.findViewById(R.id.tv_bind_title);
        tvBindTitle.setText("绑定手机号");
        etBangdingYanzheng = (EditText) inflate.findViewById(R.id.et_bangding_yanzheng);
        btBangding = (Button) inflate.findViewById(R.id.bt_bangding);
        tvBangdingYanzheng = (TextView) inflate.findViewById(R.id.tv_bangding_yanzheng);
        btBangding.setOnClickListener(this);
        tvBangdingYanzheng.setOnClickListener(this);
        PhoneCodeUtiles.PhoneNum(etBangdingPhone, etBangdingYanzheng);
        dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(inflate);
    }

    public void getSMS() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", etBangdingPhone.getText().toString());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getSMS(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                ToastUtil.showMessage("验证码已发送至您的手机请注意查收");
                timer.setTv(tvBangdingYanzheng);
                timer.cancel();
                timer.start();
            }
        });
    }

    @Override
    protected void initData() {
        try {


            ivMyPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                        hideSystemKeyBoard(ivMyPhone);
                        //处理事件
                    }
                    return false;
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 102) {
            infoBean.setPwd(1);
        }
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        ivMyHead.setImageBitmap(head);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        file = new File(path);
        file.mkdirs();// 创建文件夹
        // 图片名字
        fileName = path + "head.jpg";
        try {
            b = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            byte[] bytes = b.toByteArray();
            base = Base64.encodeToString(bytes, Base64.DEFAULT);
            base = Base64.encodeToString(bytes, Base64.DEFAULT);
            sethead();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.iv_my_head, R.id.ll_my_women, R.id.ll_my_yers, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                switch (sex) {
                    case 0:
                        mysex = "未知";
                        break;
                    case 1:
                        mysex = "男";
                        break;
                    case 2:
                        mysex = "女";
                        break;
                }
                if (ivMyPhone.getText().toString().equals(phone)
                        && ivMyWomen.getText().toString().equals(mysex)
                        && tvMyYers.getText().toString().equals(birthday)) {
                    returnTask();
                    finish();
                } else {
                    setInfo();

                }
                break;
            case R.id.iv_my_head:
                if (window != null) {
                    window.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.ll_my_women:
                if ("女".equals(ivMyWomen.getText().toString())) {
                    rb_women.setChecked(true);
                } else {
                    rb_men.setChecked(true);
                }
                women.show();
                break;
            case R.id.ll_my_yers:
                dlg.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (sex) {
            case 0:
                mysex = "未知";
                break;
            case 1:
                mysex = "男";
                break;
            case 2:
                mysex = "女";
                break;
        }
        LogUtils.e(ivMyPhone.getText().toString().equals(phone) + "" + ivMyWomen.getText().toString().equals(mysex)
                + "" + wechatId + tvMyYers.getText().toString().equals(birthday));
        if (ivMyPhone.getText().toString().equals(phone)
                && ivMyWomen.getText().toString().equals(mysex)
                && tvMyYers.getText().toString().equals(birthday)) {
            returnTask();
            finish();
        } else {
            setInfo();

        }
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


                    if (!TextUtils.isEmpty(bean.getBirthday())) {
                        birthday = bean.getBirthday();
                        tvMyYers.setText(bean.getBirthday().toString().trim());
                    }

                    if (!TextUtils.isEmpty(bean.getHead())) {
                        try {
                            Glide.with(MyInfoActivity.this).load(bean.getHead()).into(ivMyHead);
                        } catch (Exception e) {
                            LogUtils.e("加载失败");
                        }
                    }
                    if (!TextUtils.isEmpty(bean.getWechatId())) {
                        wechatId = bean.getWechatId();
                        LogUtils.e(wechatId + "");
                    }
                    if (bean.getSex() != 0) {
                        sex = bean.getSex();
                        ivMyWomen.setText(bean.getSex() == 1 ? "男" : "女");
                    }

                    if (!TextUtils.isEmpty(bean.getPhone())) {
                        phone = bean.getPhone();
                        ivMyPhone.setText(bean.getPhone().toString().trim());
                        ivMyPhone.setClickable(false);
                    } else {
                        //绑定手机号
                        ivMyPhone.setClickable(true);
                        ivMyPhone.setOnClickListener(MyInfoActivity.this);
                    }
                }

            }

            @Override
            public void onHandleError(int code, String message) {
            }
        });
    }

    private void setInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        switch (ivMyWomen.getText().toString().trim()) {
            case "未知":
                map.put("sex", 0);
                break;
            case "男":
                map.put("sex", 1);
                break;
            case "女":
                map.put("sex", 2);
                break;
        }
        LogUtils.e(ivMyWomen.getText().toString().trim() + tvMyYers.getText().toString().trim());
        map.put("birth", tvMyYers.getText().toString().trim());
        map.put("phone", ivMyPhone.getText().toString().trim());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        final Observable logins = RetroFactory.getInstance().setMyinfo(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<StudentListBean>(this, pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(final StudentListBean bean) {
                returnTask();
                finish();
            }

            @Override
            public void onHandleError(int code, String message) {
                finish();

            }
        });
    }


    private void returnTask() {
        if (isNull()) {
            Intent intent = new Intent();
            intent.putExtra(Flat.TASK_FLAG, flag);
            setResult(2, intent);
        }
    }

    private boolean isNull() {
        if (ivMyHead.getDrawable() != null && !TextUtils.isEmpty(ivMyPhone.getText())
                && !TextUtils.isEmpty(ivMyPhone.getText())
                && !TextUtils.isEmpty(tvMyYers.getText())) {
            flag = 1;
            return true;
        } else {
            flag = 0;
            return false;
        }
    }

    private void sethead() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        if (file != null) {
            map.put("file", base);
        }
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        Observable logins = RetroFactory.getInstance().getInfo(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<StudentListBean>(this, pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(final StudentListBean bean) {

            }

            @Override
            public void onHandleError(int code, String message) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                if (women != null && women.isShowing())
                    women.dismiss();
                break;
            case R.id.tv_confirm:
                if (rb_men.isChecked()) {
                    men = rb_men.getText().toString().trim();
                    ivMyWomen.setText(men);
                } else {
                    men = rb_women.getText().toString().trim();
                    ivMyWomen.setText(men);
                }
                if (women != null && women.isShowing())
                    women.dismiss();
                break;
            case R.id.tv_bangding_yanzheng:
                if (etBangdingPhone.getText().toString().trim() != null) {
                    getSMS();
                } else {
                    ToastUtil.showMessage("请输入手机号");
                }
                break;
            case R.id.bt_bangding:
                if (TextUtils.isEmpty(etBangdingPhone.getText().toString()) || TextUtils.isEmpty(etBangdingYanzheng.getText().toString())) {
                    ToastUtil.showMessage("请输入完整信息");
                    return;
                }
                getBangding();
                break;
            case R.id.iv_my_phone:
                if (dialog != null && !dialog.isShowing())
                    dialog.show();
                break;
        }
    }

    private void getBangding() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", etBangdingPhone.getText().toString());
        map.put("code", etBangdingYanzheng.getText().toString());
        map.put("devid", SharedPreferencesUtil.getOnlyID(this));
        map.put("v", UpdateAppUtil.getAPPLocalVersion(this));
        map.put("sso", SharedPreferencesUtil.getSSo(this));
        Observable logins = RetroFactory.getInstance().getBangd(map);
        logins.compose(composeFunction).subscribe(new BaseObserver<MYBean>(this, pd) {
            @Override
            protected void onHandleSuccess(MYBean loginBean) {
                ToastUtil.showMessage("绑定成功");
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                ivMyPhone.setText(etBangdingPhone.getText().toString().trim());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (women != null && women.isShowing())
            women.dismiss();
        women = null;

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = null;
    }


}
