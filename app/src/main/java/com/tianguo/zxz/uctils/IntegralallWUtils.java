package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.view.Window;

import com.tianguo.zxz.activity.MyActivity.DialogActivity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lx on 2017/8/8.
 */

public class IntegralallWUtils {
    @SuppressLint("WrongConstant")
    @TargetApi(21)
    public static boolean checkUsagePermission(Activity paramActivity) {
        if (Build.VERSION.SDK_INT >= 24) {
            Object localObject = Calendar.getInstance();
            ((Calendar) localObject).add(Calendar.DATE, -7);
            localObject = ((UsageStatsManager) paramActivity.getSystemService(Context.USAGE_STATS_SERVICE)).queryUsageStats(UsageStatsManager.INTERVAL_DAILY, ((Calendar) localObject).getTimeInMillis(), System.currentTimeMillis());
            if ((localObject == null) || (((List) localObject).size() == 0)) {
                showUsageDlg(paramActivity);
                return false;
            }
        }
        return true;
    }

    public static void showUsageDlg(final Activity paramActivity) {
        final AlertDialog localAlertDialog = new AlertDialog.Builder(paramActivity).create();
        localAlertDialog.setCancelable(true);
        localAlertDialog.setCanceledOnTouchOutside(false);
        try {
            Window localWindow = localAlertDialog.getWindow();
            localWindow.setBackgroundDrawable(new ColorDrawable(0));
            localAlertDialog.setTitle("温馨提示");
            localAlertDialog.setMessage("允许查看手机使用情况，才能顺利完成任务哦");
            localAlertDialog.setButton("去开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        if (localAlertDialog.isShowing()) {
                            localAlertDialog.dismiss();
                            showUsageSettings(paramActivity);
                            return;
                        }
                        return;
                    } catch (Exception localException) {
                        return;
                    }
                }
            });
            localAlertDialog.show();
        } catch (Exception e) {

        }
    }

    public static void showUsageSettings(final Context paramContext) {
        final Intent localIntent = new Intent(paramContext, DialogActivity.class);
        try {
            paramContext.startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
            new Thread(new Runnable() {
                public final void run() {
                    SystemClock.sleep(450L);
                    paramContext.startActivity(localIntent);
                }
            }).start();
            return;
        } catch (Exception localException) {
        }
    }
    public static boolean openApp(Context paramContext, String paramString)
    {
        PackageManager localPackageManager = paramContext.getPackageManager();
        try
        {
            Intent paramStrings = localPackageManager.getLaunchIntentForPackage(paramString);
            if (paramString == null) {
                return false;
            }
            paramStrings.addCategory("android.intent.category.LAUNCHER");
            paramContext.startActivity(paramStrings);
            return true;
        }
        catch (Exception paramStrings) {}
        return false;
    }

}
