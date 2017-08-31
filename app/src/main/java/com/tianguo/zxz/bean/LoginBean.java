package com.tianguo.zxz.bean;

/**
 * Created by lx on 2017/4/27.
 */

public class LoginBean {

        /**
         * platform : 2
         * isInvite : 1
         * sso : 3VkqUWXL1uGvblYlc30K3yAOEcl5jQ-x
         * user : {"f":1000,"phone":"15201126937","birth":1492410551732,"awd":"{\"time\":20170531193823,\"K316412\":true,\"K313383\":true,\"K317262\":true}","mood":"","exp":42,"u":812299,"s":0,"nick":"15201126937","email":"","notify":"","gold":9094,"wd":0,"head":""}
         */
    private int platform;
        private int isInvite;
        private String sso;
        private UserBean user;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    public String getwMd5() {
        return wMd5;
    }

    public void setwMd5(String wMd5) {
        this.wMd5 = wMd5;
    }

    private String wMd5;

    public int getNewbie() {
        return newbie;
    }

    public void setNewbie(int newbie) {
        this.newbie = newbie;
    }

    private int newbie;
        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public int getIsInvite() {
            return isInvite;
        }

        public void setIsInvite(int isInvite) {
            this.isInvite = isInvite;
        }

        public String getSso() {
            return sso;
        }

        public void setSso(String sso) {
            this.sso = sso;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * f : 1000
             * phone : 15201126937
             * birth : 1492410551732
             * awd : {"time":20170531193823,"K316412":true,"K313383":true,"K317262":true}
             * mood :
             * exp : 42
             * u : 812299
             * s : 0
             * nick : 15201126937
             * email :
             * notify :
             * gold : 9094
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

