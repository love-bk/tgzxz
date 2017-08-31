package com.tianguo.zxz.serviec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tianguo.zxz.uctils.LogUtils;

/**
 * Created by admin on 2017/7/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("short")){
            Toast.makeText(context, "short alarm", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "repeating alarm",Toast.LENGTH_LONG).show();
            LogUtils.e("闹铃响了");
            i++;
        }
    }

}
