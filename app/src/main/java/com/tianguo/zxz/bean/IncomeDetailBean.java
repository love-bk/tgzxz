package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by andy on 2017/7/11.
 */
public class IncomeDetailBean {

    private List<RecordBean> record;


    public class RecordBean {

        /**
         * _id : 59645ae4f8adc61aa8ee309b
         * u : 681297
         * d : Tue Jul 11 12:58:12 CST 2017
         * m : 2
         * t : 1
         */

        private String _id;
        private int u;
        private long d;
        private int m;
        private String t;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getU() {
            return u;
        }

        public void setU(int u) {
            this.u = u;
        }

        public long getD() {
            return d;
        }

        public void setD(long d) {
            this.d = d;
        }

        public int getM() {
            return m;
        }

        public void setM(int m) {
            this.m = m;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }
}
