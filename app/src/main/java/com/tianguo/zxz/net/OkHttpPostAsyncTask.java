package com.tianguo.zxz.net;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.tianguo.zxz.uctils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2017/7/24.
 */

public class OkHttpPostAsyncTask extends AsyncTask<Map<String,Object>, Void, Map<String,Object>> {
    public static final String REQFLAG = "reqFlag";
    public static final String RESULT = "result";
    private static String baseUrl = RetroFactory.baseUrl+"api/app/sys/config.do";
    private  Context context;
    private AsyncCallBack asyncCallBack;


    public OkHttpPostAsyncTask(Context context,AsyncCallBack asyncCallBack) {
        this.asyncCallBack = asyncCallBack;
        this.context = context;
    }

    public interface AsyncCallBack {
        void loadSuccess(String result);

        void loadFail();
    }

    @Override
    protected Map<String,Object> doInBackground(Map<String,Object>... params) {
        Map<String,Object> result = new HashMap<>();
        if(params != null && params.length > 0){
            StringBuffer param = new StringBuffer();
            Iterator<Map.Entry<String, Object>> iterator = params[0].entrySet().iterator();
            while(iterator.hasNext()){
                if(param.toString().length() == 0){
                    param.append("?");
                } else {
                    param.append("&");
                }
                Map.Entry<String, Object> next = iterator.next();
                param.append(next.getKey());
                param.append("=");
                param.append(next.getValue());
            }
            LogUtils.e(param.toString());

            result = postRequest(param.toString());
        }
        return result;
    }

    @Override
    protected void onPostExecute(Map<String,Object> result) {
        super.onPostExecute(result);
        if (result != null) {
            //请求成功的标志
            Object o = result.get(REQFLAG);
            if (o != null){
                boolean reqFlag = (boolean) o;
                if (asyncCallBack!= null){
                    if (reqFlag){
                        asyncCallBack.loadSuccess((String) result.get(RESULT));
                    }else {
                        asyncCallBack.loadFail();
                    }
                }
            }

        }
    }


    public Map<String,Object> postRequest(String param) {
        Map<String,Object> map = new HashMap<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl + param)
                .addHeader("content-type", "application/json;charset:utf-8")
                // 表单提交
                .put(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        ""))// post json提交
                .build();
        String ret = null;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ret = response.body().string();
                map.put(REQFLAG,true);
                map.put(RESULT,ret);
                LogUtils.e("开关数据请求成功了"+ret,"gjj");
                //解析数据
                parseData(ret,map);
            }else {
                map.put(REQFLAG,false);
                LogUtils.e("失败了","gjj");
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put(REQFLAG,false);
        }
        return map;
    }

    private void parseData(String result, Map<String,Object> map) {
        if (!TextUtils.isEmpty(result)&&context!=null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = 0;
                String msg = "";
                long tick = 0;
                JSONObject dataJsonProject = null;
                if (!jsonObject.isNull("code")) {
                    code = jsonObject.getInt("code");
                }
                if (!jsonObject.isNull("msg")) {
                    msg = jsonObject.getString("msg");
                }
                if (!jsonObject.isNull("data")) {
                    dataJsonProject = jsonObject.getJSONObject("data");
                }
                if (!jsonObject.isNull("tick")) {
                    tick = jsonObject.getLong("tick");
                }
                if (code == 0) {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastUtil.showMessage(msg);
                    }
                    SharedPreferencesUtil.saveTicketInfo(context, tick);

                    if (dataJsonProject != null) {
                        //保存开关数据
                        if (!dataJsonProject.isNull("switch")) {
                            JSONObject switchJsonObject = dataJsonProject.getJSONObject("switch");
                            Iterator<String> keys = switchJsonObject.keys();
                            while (keys.hasNext()) {
                                String jsonkey = keys.next();
                                SharedPreferencesUtil.removeSwitch(context, jsonkey);
                                saveSwitch(jsonkey, switchJsonObject);
                            }
                        }
                        if (!dataJsonProject.isNull("newsType")) {
                            JSONObject newTypeJsonObject = dataJsonProject.getJSONObject("newsType");
                            if (newTypeJsonObject != null) {
                                if (!newTypeJsonObject.isNull("userType")) {
                                    JSONArray userTypeJSONArray = newTypeJsonObject.getJSONArray("userType");
                                    if (userTypeJSONArray != null && userTypeJSONArray.length() > 0)
                                        SharedPreferencesUtil.saveNewUserType(context, userTypeJSONArray.toString());
                                }
                                if (!newTypeJsonObject.isNull("otherType")) {
                                    JSONArray userTypeJSONArray = newTypeJsonObject.getJSONArray("otherType");
                                    if (userTypeJSONArray != null && userTypeJSONArray.length() > 0)
                                        SharedPreferencesUtil.saveNewOtherType(context, userTypeJSONArray.toString());
                                }
                            }
                        }


                    }
                } else {
                    if (null != msg && !msg.isEmpty()) {
                        ToastUtil.showMessage(msg);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                map.put(REQFLAG,false);
            }
        }
    }


    private void saveSwitch(String key, JSONObject dataJsonProject) {
        try {
            if (!dataJsonProject.isNull(key)) {
                Object o = dataJsonProject.get(key);
                if (o!=null) {
                    if (o instanceof JSONObject){
                        JSONObject novelJsonObject = dataJsonProject.getJSONObject(key);
                        SharedPreferencesUtil.saveSwitch(context, key, novelJsonObject.toString());
                        LogUtils.e("开关保存了"+key+"=="+SharedPreferencesUtil.getSwitch(context,key),"gjj");
                    }else {
                        SharedPreferencesUtil.saveSwitch(context, key, o.toString());
                        LogUtils.e("开关保存了"+key+"=="+SharedPreferencesUtil.getSwitch(context,key),"gjj");
                    }
                }else {
                    SharedPreferencesUtil.saveSwitch(context, key, null);
                    LogUtils.e("开关保存了"+key+"=="+SharedPreferencesUtil.getSwitch(context,key),"gjj");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    public String reqExposure(){
//        OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder().url(adHubUrl).build();
//        String ret = null;
////        LogUtils.e("请求地址："+adHubUrl);
//        try {
//            Response  response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                ret = response.code()+"";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ret;
//    }


    //构造参数
    private String getParam(Map<String, Object> map) {
        Gson gson = new Gson();
        if (map != null)
            return gson.toJson(map);
        return "";
    }
}
