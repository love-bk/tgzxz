package com.tianguo.zxz.bean;

/**
 * Created by admin on 2017/8/22.
 */

public class InviteIncomeBean {


    private int followNum;//徒弟个数
    private int subFollowNum;//徒孙个数
    private long money;//收入


    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public int getSubFollowNum() {
        return subFollowNum;
    }

    public void setSubFollowNum(int subFollowNum) {
        this.subFollowNum = subFollowNum;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
