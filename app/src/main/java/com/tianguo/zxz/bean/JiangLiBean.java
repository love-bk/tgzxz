package com.tianguo.zxz.bean;

/**
 * Created by lx on 2017/5/4.
 */

public class JiangLiBean {
    int award;
    int num;
    public int getM() {
        return m;
    }


    public void setM(int m) {
        this.m = m;
    }

    int m;
    Long followerId;
    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    @Override
    public String toString() {
        return "JiangLiBean{" +
                "award=" + award +
                ", num=" + num +
                ", m=" + m +
                ", followerId=" + followerId +
                '}';
    }
}
