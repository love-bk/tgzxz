package com.tianguo.zxz.uctils;

import android.content.Context;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;
/**
 * Created by admin on 2017/8/1.
 */

public class AppInfoUtil {

    public static String getIMEI(Context context) {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return mTm.getDeviceId() == null ? "" : mTm.getDeviceId();
    }


}
