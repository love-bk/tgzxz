package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by lx on 2017/7/4.
 */

public class QiandaoBean {
    /**
     * signNums : 1
     * lastSignTime : 1499224179686
     * signStatus : 1
     * taskList : [{"c":"为什么甜果资讯会给你奖励，如何获取?","t":"了解如何赚钱","i":1,"m":5},{"c":"完善头像、生日、性别等个人信息","t":"完善个人信息","i":2,"m":10},{"c":"下载app并试玩3分钟，获得金币奖励","t":"完成0.6元下载任务","i":3,"m":60},{"c":"分享甜果下载链接到微信好友，邀请成功再获2.5元","t":"首次收徒","i":4,"m":10},{"c":"认真阅读了，到账有延迟，每天奖励1次","t":"分享到微信群","i":5,"m":10}]
     * signAward : [12,12,12,12,12,12,15]
     */

    private int signNums;
    private long lastSignTime;
    private int signStatus;
    private List<TaskListBean> taskList;
    private List<Integer> signAward;
    private TaskBean task;
    private int isNewbie;//领取一元现金红包是否显示的判断
    public int getSignNums() {
        return signNums;
    }

    public void setSignNums(int signNums) {
        this.signNums = signNums;
    }

    public long getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(long lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public List<TaskListBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskListBean> taskList) {
        this.taskList = taskList;
    }

    public List<Integer> getSignAward() {
        return signAward;
    }

    public void setSignAward(List<Integer> signAward) {
        this.signAward = signAward;
    }

    public static class TaskListBean {
        /**
         * c : 为什么甜果资讯会给你奖励，如何获取?
         * t : 了解如何赚钱
         * i : 1
         * m : 5
         */

        private String c;
        private String t;
        private int i;
        private int m;
        private int is;

        public int getIs() {
            return is;
        }

        public void setIs(int is) {
            this.is = is;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getM() {
            return m;
        }

        public void setM(int m) {
            this.m = m;
        }

        @Override
        public String toString() {
            return "TaskListBean{" +
                    "c='" + c + '\'' +
                    ", t='" + t + '\'' +
                    ", i=" + i +
                    ", m=" + m +
                    '}';
        }
    }

    public int getIsNewbie() {
        return isNewbie;
    }

    public void setIsNewbie(int isNewbie) {
        this.isNewbie = isNewbie;
    }

    public class TaskBean {
        private int taskSum;
        private int taskNum;

        public int getTaskSum() {
            return taskSum;
        }

        public void setTaskSum(int taskSum) {
            this.taskSum = taskSum;
        }

        public int getTaskNum() {
            return taskNum;
        }

        public void setTaskNum(int taskNum) {
            this.taskNum = taskNum;
        }
    }

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
        this.task = task;
    }
}