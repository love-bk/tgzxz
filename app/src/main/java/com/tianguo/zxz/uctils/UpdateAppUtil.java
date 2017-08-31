package com.tianguo.zxz.uctils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.tianguo.zxz.bean.VersionInfo;
import com.tianguo.zxz.serviec.DownLoadService;
import com.tianguo.zxz.view.ConfirmDialog;

/**
 * Created by lx on 2017/4/21.
 */

public class UpdateAppUtil {

    private static String appVersionName;

    /**
     * 获取当前apk的版本号 currentVersionCode
     *
     * @param ctx
     * @return
     */
    public static String getAPPLocalVersion(Context ctx) {

        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);

            // 版本名
            appVersionName = info.versionName;
//            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName==null?"":appVersionName.substring(0,7);
    }


    /**
     * 判断版本号，更新APP
     */
    public static void updateApp(final Activity main, final VersionInfo info) {
        if (Build.VERSION.SDK_INT>= 23){
        if (!PermissionsUtils.hasPermission(main, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            PermissionsUtils.requestPermission(main, 0, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
            return;
        }
        }

            ConfirmDialog dialog = new ConfirmDialog(main, new ConfirmDialog.Callback() {
                @Override
                public void callback() {
                    if (!PermissionsUtils.hasPermission(main, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ToastUtil.showMessage("请开启app请求权限否则部分功能将无法使用");
                        PermissionsUtils.requestPermission(main, 0, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                        return;
                    }
                    Intent service = new Intent(main, DownLoadService.class);
                    service.putExtra("downloadurl", info.getUrl());
                    ToastUtil.showMessage(  "开始更新下载");
                    main.startService(service);
                }
            });
            dialog.setContent("发现新版本:" + info.getVer() + "\n是否下载更新?");
            dialog.setCancelable(false);
            dialog.show();
    }
}

