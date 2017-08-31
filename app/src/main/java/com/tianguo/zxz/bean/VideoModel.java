package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by shuyu on 2016/11/11.
 */

public class VideoModel {


    private List<VideoBean> video;

    public List<VideoBean> getVideo() {
        return video;
    }

    public void setVideo(List<VideoBean> video) {
        this.video = video;
    }

    public static class VideoBean {
        /**
         * s : 0
         * d : 01:32
         * im : http://p7.qhimg.com/t0166dc8dc022731e56.jpg
         * t : 航母被称为吸金兽 你知道辽宁舰加满油能吸走多少人民币吗
         * v : http://video.mp.sj.360.cn/vod_zhushou/vod-shouzhu-bj/adbe1889c63085e7a2b59923e8a6d57e.mp4
         * i : 4744
         */

        private int s;
        private String d;
        private String im;
        private String t;
        private String v;
        private int i;

        public int getS() {
            return s;
        }

        public void setS(int s) {
            this.s = s;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getIm() {
            return im;
        }

        public void setIm(String im) {
            this.im = im;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        @Override
        public String toString() {
            return "VideoBean{" +
                    "s=" + s +
                    ", d='" + d + '\'' +
                    ", im='" + im + '\'' +
                    ", t='" + t + '\'' +
                    ", v='" + v + '\'' +
                    ", i=" + i +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "video=" + video +
                '}';
    }
}
