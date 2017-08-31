package com.tianguo.zxz.uctils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by lx on 2017/4/24.
 */

public class PermissionsUtils {
    /**
     * 判断是否拥有权限
     *
     * @param permissions
     * @return
     */
    public static boolean hasPermission(Activity context,String... permissions) {
        if (Build.VERSION.SDK_INT>=23) {

            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }
    /**
     * 请求权限
     */
    public static void requestPermission(Activity context , int code, String... permissions) {
        if (Build.VERSION.SDK_INT>=23){
            ActivityCompat.requestPermissions(context, permissions, code);
        }
    }

}
