package com.tianguo.zxz.net;


import java.io.Serializable;

public class BaseEntity<E>  implements Serializable {
    private long tick;
    private E data;
    private int code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTick() {
        return tick;
    }
    public void setTick(long tick) {
        this.tick = tick;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }


}
