package com.tianguo.zxz.uctils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tianguo.zxz.R;

/**
 * Created by lx on 2017/5/24.
 */

public class TeileBar {

    private static int white;
    public static void myStatusBar(Activity activity, int i) {
        if (i == 0) {
            white = Color.WHITE;
        } else {
            white = activity.getResources().getColor(R.color.codetext);
        }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintResource(white);
            }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
            activity.getWindow().setStatusBarColor(white);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
}
}
