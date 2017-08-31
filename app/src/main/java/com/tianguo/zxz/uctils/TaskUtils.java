package com.tianguo.zxz.uctils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by lx on 2017/8/9.
 */

public class TaskUtils implements Application.ActivityLifecycleCallbacks {
    private static TaskUtils taskUtils;
    private isOnTaskShow  taskShow;
    public int mCount = 0;
    public static TaskUtils init(Application paramApplication)
    {
        if (taskUtils == null) {
            taskUtils = new TaskUtils();
            paramApplication.registerActivityLifecycleCallbacks(taskUtils);
        }
        return taskUtils;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        int i = this.mCount;
        this.mCount = (i + 1);
        if ((i == 0) && (taskShow != null)) {
            taskShow.onTaskSwitchToForeground();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        int i = this.mCount - 1;
        this.mCount = i;
        if ((i == 0) && (taskShow != null)) {
            taskShow.onTaskSwitchToBackground();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
    public final void setOnTaskSwitchListener(isOnTaskShow taskShow)
    {
        this.taskShow = taskShow;
    }

    public  interface isOnTaskShow
    {
      void onTaskSwitchToBackground();

         void onTaskSwitchToForeground();
    }
}
