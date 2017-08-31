package com.tianguo.zxz.serviec;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.MyApplictation;

import java.lang.Thread.UncaughtExceptionHandler;

/**  
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.  
 *   
 *
 */    
public class CrashHandler implements UncaughtExceptionHandler {    
        
	
	private UncaughtExceptionHandler mDefaultHandler;
    public static final String TAG = "CatchExcep";  
    MyApplictation application;
      
    public CrashHandler(MyApplictation application){
         //获取系统默认的UncaughtException处理器    
         mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
         this.application = application;  
    }  
      
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {      
        if(!handleException(ex) && mDefaultHandler != null){   
            //如果用户没有处理则让系统默认的异常处理器来处理    
            mDefaultHandler.uncaughtException(thread, ex);                
        }else{         
            try{    
                Thread.sleep(2000);    
            }catch (InterruptedException e){    
                Log.e(TAG, "error : ", e);    
            }     
            Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
            PendingIntent restartIntent = PendingIntent.getActivity(    
                    application.getApplicationContext(), 0, intent,    
                    Intent.FLAG_ACTIVITY_NEW_TASK);                                                 
            //退出程序                                          
            @SuppressLint("WrongConstant") AlarmManager mgr = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,    
                    restartIntent); // 1秒钟后重启应用   
            application.finishActivity();  
        }    
    }  
      
    /**  
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.  
     *   
     * @param ex  
     * @return true:如果处理了该异常信息;否则返回false.  
     */    
    private boolean handleException(final Throwable ex) {    
        if (ex == null) {    
            return false;    
        }    
        //使用Toast来显示异常信息    
        new Thread(){    
            @SuppressLint("WrongConstant")
            @Override
            public void run() {    
                Looper.prepare();    
                Looper.loop();
            }   
        }.start();    
        return true;    
    }    
}
