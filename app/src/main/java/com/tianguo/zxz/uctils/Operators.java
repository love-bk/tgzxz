package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
/**
 * Created by lx on 2017/6/7.
 *  * 获取手机运营商

 */

public class Operators {
    //
    //获取手机运营商
    public   static  int getOperators(Context context) {
        try {


        // 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）（也称为“MCC /
        // MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC 获取手机服务商信息
        int OperatorsName = 0;
        if (context==null){
            return 1;
        }
        @SuppressLint("WrongConstant") TelephonyManager IMSI =  (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (IMSI==null||IMSI.getSubscriberId()==null){
            return 1;
        }
        String subscriberId = IMSI.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
        System.out.println(IMSI);
        if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002") || subscriberId.startsWith("46007")) {
            OperatorsName = 1;
        } else if (subscriberId.startsWith("46001") || subscriberId.startsWith("46006")) {
            OperatorsName = 2;
        } else if (subscriberId.startsWith("46003") || subscriberId.startsWith("46005")) {
            OperatorsName = 3;
        }
        return OperatorsName;
        }catch (Exception e){
            return 1;

        }
    }
}
