package com.tianguo.zxz.net;


import com.tianguo.zxz.uctils.LogUtils;

import java.io.IOException;

import cn.yqzq.sharelib.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author WalterWang
 *         Created at 17/3/31 下午2:29
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (BuildConfig.DEBUG) {
            LogUtils.e(("发送请求"+ chain.request().toString()+ request.headers()));
        }
        return chain.proceed(request);
    }
}