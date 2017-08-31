package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by lx on 2017/5/24.
 */

public class SoDaoBean {

        private String msg;
        private List<SearchListBean> searchList;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<SearchListBean> getSearchList() {
            return searchList;
        }

        public void setSearchList(List<SearchListBean> searchList) {
            this.searchList = searchList;
        }

        public static class SearchListBean {
            /**
             * i : 3991
             * u : https://www.baidu.com
             * n : 百度
             * p : /upload/search/3990.jpg
             */

            private int i;
            private String u;
            private String n;
            private String p;

            public int getI() {
                return i;
            }

            public void setI(int i) {
                this.i = i;
            }

            public String getU() {
                return u;
            }

            public void setU(String u) {
                this.u = u;
            }

            public String getN() {
                return n;
            }

            public void setN(String n) {
                this.n = n;
            }

            public String getP() {
                return p;
            }

            public void setP(String p) {
                this.p = p;
            }
        }
}
