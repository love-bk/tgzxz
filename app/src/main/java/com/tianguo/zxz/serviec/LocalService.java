package com.tianguo.zxz.serviec;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.tianguo.zxz.uctils.AdvancedCountdownTimer;
import com.tianguo.zxz.uctils.LogUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
/**
 * Created by lx on 2017/8/9.
 */
public class LocalService extends Service {
    private volatile handler hader =new handler();
    private String packageName;
    private int millisInFuture;
    private boolean isovber = false;

    private AdvancedCountdownTimer timer;
    private int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("ssssssssss"+intent.getAction());
        Object localObject;
        if (intent != null) {
            localObject = intent.getAction();
            if ("tianguo.intent.action.START.COUNTTIMER".equals(localObject)) {
                LogUtils.e("ssssssss");
                isovber = false;
                packageName = intent.getStringExtra("packageName");
                millisInFuture = intent.getIntExtra("millisInFuture", 180);
                id = intent.getIntExtra("id", -1);
                hader.sendEmptyMessage(10);
                return 3;
            }


        }
        return super.onStartCommand(intent, flags, startId);
    }


    public class handler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    if (timer != null) {
                        timer.cancel();
                    }
                    timer = new AdvancedCountdownTimer(millisInFuture*1000l,1000l){
                        @Override
                        public void onFinish() {
                            hader.sendEmptyMessage(30);
                        }

                        @Override
                        public void onTick(long millisUntilFinished, int percent) {
                            millisInFuture--;
                            LogUtils.e("ssssssssss"+millisUntilFinished);
                            if (Build.VERSION.SDK_INT >= 24) {
                                if (!isAppOnUsageForeground(packageName)) {
                                    LogUtils.e(packageName+"");
                                    LogUtils.e(millisInFuture+"");
                                    hader.sendEmptyMessage(30);
                                }
                            } else {
                                if (!isAppOnForeground(packageName)) {
                                    hader.sendEmptyMessage(30);
                                    LogUtils.e(packageName+"");
                                    LogUtils.e(millisInFuture+"");
                                }
                            }

                        }

                    };
                    brodcast(LocalService.this, false,false );
                    timer.start();
                    LogUtils.e("ssssssssss");
                    break;
                case 30:
                    if (timer != null) {
                        timer.cancel();
                    }
                    brodcast(LocalService.this, false,true);
                    break;
            }
        }
    }

    private void brodcast(Context paramContext, boolean b1, boolean b) {
        Intent localIntent = new Intent("tianguo.intent.action.POINT_NOTIFY.EVENT");
        LogUtils.e("ssssssss");
        localIntent.putExtra("isovber",b);
        localIntent.putExtra("starttime",millisInFuture);
        localIntent.putExtra("id",id);
        localIntent.setPackage(paramContext.getPackageName());
        sendBroadcast(localIntent);
    }

    /**
     * 5.0之前：
     * 获取前台应用进程
     *
     * @param paramString
     * @return
     */
    public boolean isAppOnForeground(String paramString) {
        @SuppressLint("WrongConstant")
        Object localObject = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (localObject != null) {
            localObject = ((List) localObject).iterator();
            while (((Iterator) localObject).hasNext()) {
                ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) ((Iterator) localObject).next();
                if (localRunningAppProcessInfo.processName.startsWith(paramString)) {
                    LogUtils.e("packageName:" + paramString + " 此appimportace =" + localRunningAppProcessInfo.importance);
                    if (localRunningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            ) {
                        LogUtils.e("packageName:" + paramString + " 处于前台" + localRunningAppProcessInfo.processName+                        localRunningAppProcessInfo.importance);
                        return true;
                    }
                    LogUtils.e("packageName:" + paramString + " 处于后台" + localRunningAppProcessInfo.processName);
                }
            }
        }
        return false;
    }

    /**
     * 5.值后：
     * 获取前台应用进程是否是当前app
     *
     * @param paramString
     * @return
     */
    @SuppressLint("WrongConstant")
    @TargetApi(21)
    public boolean isAppOnUsageForeground(String paramString) {
        Object localObject1 = Calendar.getInstance();
        ((Calendar) localObject1).add(Calendar.MINUTE, -10);
        UsageStatsManager localUsageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        Object localObject2 = localUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, ((Calendar) localObject1).getTimeInMillis(), System.currentTimeMillis());
        if (localObject2 != null) {
            localObject1 = localObject2;
            if (((List) localObject2).size() != 0) {
            }
        } else {
            LogUtils.e("权限不够 或者 用户在查询时间内没有新的操作记录");
            localObject1 = Calendar.getInstance();
            ((Calendar) localObject1).add(11, -1);
            localObject1 = localUsageStatsManager.queryUsageStats(0, ((Calendar) localObject1).getTimeInMillis(), System.currentTimeMillis());
            if ((localObject1 == null) || (((List) localObject1).size() == 0)) {
                return false;
            }
        }
        long l1 = System.currentTimeMillis();
        Collections.sort((List) localObject1, new CalendarComparator());
        int i1 = 0;
        while (i1 < ((List) localObject1).size()) {
            localObject2 = (UsageStats) ((List) localObject1).get(i1);
            if (((UsageStats) localObject2).getLastTimeUsed() <= l1) {
            }
            LogUtils.e("getLastTimeUsed()>currentTime packageName:" + ((UsageStats) localObject2).getPackageName());
            i1 += 1;
        }
        LogUtils.e("packageName:" + paramString + " lastapp:" + ((UsageStats) localObject2).getPackageName());
        return ((UsageStats) localObject2).getPackageName().equals(paramString);
    }

    private static final class CalendarComparator implements Comparator<UsageStats> {
        @TargetApi(21)
        public final int compare(UsageStats paramUsageStats1, UsageStats paramUsageStats2) {
            return Long.compare(paramUsageStats2.getLastTimeUsed(), paramUsageStats1.getLastTimeUsed());
        }
    }


}
