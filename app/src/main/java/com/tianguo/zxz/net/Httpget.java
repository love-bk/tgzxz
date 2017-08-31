package com.tianguo.zxz.net;

import android.text.TextUtils;

import com.tianguo.zxz.uctils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.tianguo.zxz.net.RetroFactory.decode;

/**
 * Created by lx on 2017/5/27.
 */

public class Httpget {
    public static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            if (TextUtils.isEmpty(message)) return;
            String s = message.substring(0, 1);
            //如果收到想响应是json才打印
            if ("{".equals(s) || "[".equals(s)) {
                LogUtils.e("tiaoshi: " + decode(message));
            }
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    public static Call executeGet(String url) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor()).addInterceptor(interceptor).connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        return httpClient.newCall(request);
    }

}
