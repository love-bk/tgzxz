package com.tianguo.zxz.bean;

/**
 * Created by lx on 2017/4/28.
 */

public class MYBean {

    /**
     * platform : 2
     * user : {"f":0,"phone":"","birth":1493297151456,"awd":"{\"K179004\":true,\"time\":20170428153339,\"K180581\":true,\"K178693\":true}","mood":"","exp":0,"u":822601,"s":0,"nick":"1520112697","email":"","notify":"","gold":0,"wd":0,"head":""}
     */

    private int platform;
    private UserBean user;
    private int num;

    public int getInvitor() {
        return invitor;
    }

    public void setInvitor(int invitor) {
        this.invitor = invitor;
    }

    public int invitor;

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static class UserBean {
        /**
         * f : 0
         * phone :
         * birth : 1493297151456
         * awd : {"K179004":true,"time":20170428153339,"K180581":true,"K178693":true}
         * mood :
         * exp : 0
         * u : 822601
         * s : 0
         * nick : 1520112697
         * email :
         * notify :
         * gold : 0
         * wd : 0
         * head :
         */

        private int f;
        private String phone;
        private long birth;
        private String awd;
        private String mood;
        private int exp;
        private int u;
        private int s;
        private String nick;
        private String email;
        private String notify;
        private int gold;
        private int wd;
        private String head;
        private int score;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getF() {
            return f;
        }

        public void setF(int f) {
            this.f = f;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getBirth() {
            return birth;
        }

        public void setBirth(long birth) {
            this.birth = birth;
        }

        public String getAwd() {
            return awd;
        }

        public void setAwd(String awd) {
            this.awd = awd;
        }

        public String getMood() {
            return mood;
        }

        public void setMood(String mood) {
            this.mood = mood;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public int getU() {
            return u;
        }

        public void setU(int u) {
            this.u = u;
        }

        public int getS() {
            return s;
        }

        public void setS(int s) {
            this.s = s;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNotify() {
            return notify;
        }

        public void setNotify(String notify) {
            this.notify = notify;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getWd() {
            return wd;
        }

        public void setWd(int wd) {
            this.wd = wd;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }
    }
}
