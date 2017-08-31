package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by admin on 2017/7/17.
 */

public class ChannelUtil {

    /**
     * 获取友盟渠道名
     *
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Activity ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = "";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                @SuppressLint("WrongConstant") ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL") + "";
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }
}
