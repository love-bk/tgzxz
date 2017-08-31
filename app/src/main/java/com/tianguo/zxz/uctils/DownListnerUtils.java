package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by lx on 2017/6/28.
 */

public class DownListnerUtils {

        public static boolean checkApkExist(Context context, String packageName){
            if (TextUtils.isEmpty(packageName))
                return false;
            try {
                @SuppressLint("WrongConstant")
                ApplicationInfo info = context.getPackageManager()
                        .getApplicationInfo(packageName,
                                PackageManager.GET_UNINSTALLED_PACKAGES);
                LogUtils.e(info.toString());
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                LogUtils.e(e.toString());
                return false;
            }
        }

    /**
     *重点在这里
     */
    public static void openFile(File var0, Context var1) {

        Intent var2 = new Intent(Intent.ACTION_VIEW);
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            Uri uriForFile = FileProvider.getUriForFile(var1, "com.tianguo.zxz.fileprovider", var0);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
        }else{
            var2.setDataAndType(Uri.fromFile(var0), "application/vnd.android.package-archive");
        }
        try {
            var1.startActivity(var2);

        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    }
