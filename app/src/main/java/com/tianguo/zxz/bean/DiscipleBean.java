package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by admin on 2017/8/22.
 */

public class DiscipleBean {

    private List<FollowBean> follow;

    public class FollowBean {
        private long time;
        private String head;
        private long money;
        private long follower;
        private String userPhone;


        public long getFollower() {
            return follower;
        }

        public void setFollower(long follower) {
            this.follower = follower;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public long getMoney() {
            return money;
        }

        public void setMoney(long money) {
            this.money = money;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }
    }

    public List<FollowBean> getFollow() {
        return follow;
    }

    public void setFollow(List<FollowBean> follow) {
        this.follow = follow;
    }
}
