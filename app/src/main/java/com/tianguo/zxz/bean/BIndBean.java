package com.tianguo.zxz.bean;

/**
 * Created by lx on 2017/5/12.
 */

public class BIndBean {


    public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }

    String tick;
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Data data;
    private int code;
    private String msg;


    public class Data {
        public int getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(int bindStatus) {
            this.bindStatus = bindStatus;
        }

        public String getSso() {
            return sso;
        }

        public void setSso(String sso) {
            this.sso = sso;
        }

        int bindStatus;
        private String sso;
    }
}
