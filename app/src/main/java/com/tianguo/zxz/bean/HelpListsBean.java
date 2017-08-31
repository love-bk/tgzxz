package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by lx on 2017/5/16.
 */

public class HelpListsBean {

        private String msg;
        private List<HelpListBean> helpList;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<HelpListBean> getHelpList() {
            return helpList;
        }

        public void setHelpList(List<HelpListBean> helpList) {
            this.helpList = helpList;
        }

        public static class HelpListBean {
            /**
             * _id : 591aad4070a64c50c4721b17
             * i : 3885
             * t : 如何使用该APP啦啦啦啦？
             * c : asdasd asdas dasd asdas das dasd asdas das dasd
             * k : 9
             * p : 3
             * m : 2017-05-16 15:41:50
             */

            private String _id;
            private int i;
            private String t;
            private String c;
            private int k;
            private int p;
            private String m;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public int getI() {
                return i;
            }

            public void setI(int i) {
                this.i = i;
            }

            public String getT() {
                return t;
            }

            public void setT(String t) {
                this.t = t;
            }

            public String getC() {
                return c;
            }

            public void setC(String c) {
                this.c = c;
            }

            public int getK() {
                return k;
            }

            public void setK(int k) {
                this.k = k;
            }

            public int getP() {
                return p;
            }

            public void setP(int p) {
                this.p = p;
            }

            public String getM() {
                return m;
            }

            public void setM(String m) {
                this.m = m;
            }
        }
    }