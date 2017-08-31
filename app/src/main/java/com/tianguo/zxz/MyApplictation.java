package com.tianguo.zxz;

import android.app.Activity;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.util.ArrayList;

/**
 * Created by lx on 2017/4/18.
 */

public class MyApplictation extends TinkerApplication {
    ArrayList<Activity> list = new ArrayList<Activity>();

    public MyApplictation() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.tianguo.zxz.SampleApplicationLike", "com.tencent.tinker.loader.TinkerLoader", false);
    }
    public void init() {
//        // 设置该CrashHandler为程序的默认处理器
//        CrashHandler catchExcep = new CrashHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

    }
    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public void removeActivity(Activity a) {
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public void addActivity(Activity a) {
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    /**
     * 关闭Activity列表中的所有Activity
     */
    public void finishActivity() {
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
