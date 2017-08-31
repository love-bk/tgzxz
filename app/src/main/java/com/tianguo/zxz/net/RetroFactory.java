package com.tianguo.zxz.net;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tianguo.zxz.net.api.RetrofitService;
import com.tianguo.zxz.uctils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFactory {
    public static String baseUrl = "http://www.sweetinfo.cn/";
//    private static String baseUrl = "http://10.0.0.44";
//    public static String baseUrl = "http://10.0.0.44:8082/";
//    private static String baseUrl = "http://10.0.0.72";
//    public static String baseUrl = "http://ssd.migua.net:7457/";
    private static String data ;
    private RetroFactory() {
    }
    private static  HttpLoggingInterceptor interceptor =  new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            if (TextUtils.isEmpty(message)) return;
            String s = message.substring(0, 1);
            //如果收到想响应是json才打印
            if ("{".equals(s) || "[".equals(s)) {
                LogUtils.e("tiaoshi: " + decode(message));
                data = decode(message);
            }
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);
    public static String getData(){
        return data;
    }
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor()).addInterceptor(interceptor).connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build();
    private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitService.class);

    public static RetrofitService getInstance() {
        return retrofitService;
    }

    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
