package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by lx on 2017/5/17.
 */

public class FanKuiBean {
        private String msg;
        private List<GuideListBean> guideList;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<GuideListBean> getGuideList() {
            return guideList;
        }

        public void setGuideList(List<GuideListBean> guideList) {
            this.guideList = guideList;
        }

        public static class GuideListBean {
            /**
             * _id : 591c06154e43f53188de9205
             * i : 3898
             * t : QQ
             * b : 北京参加“一带一路”高峰论坛
             * c : <p class="text" style="font-family:" font-size:16px;text-indent:2em;background-color:#ffffff;"="">
             &nbsp; 事情是这样的——
             </p>
             <p class="text" style="font-family:" font-size:16px;text-indent:2em;background-color:#ffffff;"="">
             &nbsp; 5月15日，到北京参加“一带一路”高峰论坛的日本自民党干事长二阶俊博在北京表示，关于日本加入亚投行一事，已经到了“是否决心尽快加入”的阶段。他认为，日本政界应当加快准备，避免在此事上“大幅落于人后”。
             </p>
             <p class="text" style="font-family:" font-size:16px;text-indent:2em;background-color:#ffffff;"="">
             &nbsp; “干事长”，传统上是自民党的二号人物。第一号当然是自民党首安倍。二阶的此次表态，与过去日本对亚投行的消极和质疑态度相比，无疑有了极大的变化。这也是人们关注此事的主要原因。
             </p>
             * k : 101
             * u : http://10.0.0.42:8080/images/qq.png
             * m : 2017-05-17 16:13:06
             */

            private String _id;
            private int i;
            private String t;
            private String b;
            private String c;
            private int k;
            private String u;
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

            public String getB() {
                return b;
            }

            public void setB(String b) {
                this.b = b;
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

            public String getU() {
                return u;
            }

            public void setU(String u) {
                this.u = u;
            }

            public String getM() {
                return m;
            }

            public void setM(String m) {
                this.m = m;
            }
        }
}
