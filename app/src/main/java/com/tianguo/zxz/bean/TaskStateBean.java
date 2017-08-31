package com.tianguo.zxz.bean;

/**
 * Created by admin on 2017/8/10.
 */

public class TaskStateBean {

    private int is_done;//:是否完成（0代表未完成，1代表完成）

    private int is_withdraw;

    public int getIs_withdraw() {
        return is_withdraw;
    }

    public void setIs_withdraw(int is_withdraw) {
        this.is_withdraw = is_withdraw;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }
}
