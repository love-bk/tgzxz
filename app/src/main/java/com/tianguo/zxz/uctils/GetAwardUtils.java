package com.tianguo.zxz.uctils;

import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.JiangLiBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by lx on 2017/7/4.
 */

public class GetAwardUtils {
    GetAwardListner listner;
    public  interface  GetAwardListner{
        void  getAward(double m);
        void  nullAward();
    }
    public static void getAward(int ty, String time, BaseActivity main, final GetAwardListner listner) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ty",ty);
            map.put("m",new MD5().getMD5ofStr("ssd!@#$%^&*"+SharedPreferencesUtil.getID(main)+ty));
            map.put("sso",SharedPreferencesUtil.getSSo(main));
            map.put("v",UpdateAppUtil.getAPPLocalVersion(main));
            LogUtils.e(UpdateAppUtil.getAPPLocalVersion(main)+"");

            Observable logins = RetroFactory.getInstance().getAward(map);
            logins.compose(main.composeFunction).subscribe(new BaseObserver<JiangLiBean>(main, main.pd) {
                @Override
                protected void onHandleSuccess(JiangLiBean loginBean) {
                    listner.getAward(loginBean.getM());
                    if (loginBean.getM()>0){
                        ToastUtil.showMessage("获得"+loginBean.getM()+"金币奖励");
                    }
                }

                @Override
                public void onHandleError(int code, String message) {
                    listner.nullAward();
                }
            });
        }catch (Exception e){

        }
    }


}
