package com.tianguo.zxz.uctils;

import android.util.Log;

/**
 * Code:WS
 * Time:2016/9/9:17:01
 * Todo:
 */
public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数
    private static final boolean isDeBug=true;
    private static final String TAG="tiaoshi";

    private LogUtils(){
        /* Protect from instantiations */
    }

    public static boolean isDebuggable() {
        return isDeBug;
    }

    private static String createLog( String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message){
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(TAG, createLog(message));
    }

    public static void e(String message,String tag){
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(tag, createLog(message));
    }

    public static void i(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(TAG, createLog(message));
    }

    public static void d(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(TAG, createLog(message));
    }

    public static void v(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(TAG, createLog(message));
    }

    public static void w(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(TAG, createLog(message));
    }

    public static void wtf(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(TAG, createLog(message));
    }

}