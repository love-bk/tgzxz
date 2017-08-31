package com.tianguo.zxz.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andy on 2017/6/27.
 */
public class YYListBean implements Serializable {

        /**
         * b : 打开“微信”，注册登陆后，玩2分钟
         * mo : 20
         * im : ["http://10.0.0.44:8082//upload/task/pic/4528.png","http://10.0.0.44:8082//upload/task/pic/4622.png","http://10.0.0.44:8082//upload/task/pic/4529.jpg"]
         * ty : 1
         * num : 100
         * i : 4534
         * p : com.tencent.mm
         * ma : [1,2]
         * ti : 微信
         * u : http://gdown.baidu.com/data/wisegame/7c1823c7e93002cb/weixin_1060.apk
         * si : 25
         * is_download : 0
         * ic : http://www.sweetinfo.cn/upload/task/pic/4623.png
         */

        private String b;
        private long mo;
        private int ty;
        private int num;
        private int i;
        private String p;
        private String ti;
        private String u;
        private int si;
        private int is_download;
        private String ic;
        private List<String> im;
        private List<Integer> ma;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDownload_num() {
        return download_num;
    }

    public void setDownload_num(int download_num) {
        this.download_num = download_num;
    }

    private int status;
        private  int download_num;
        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public long getMo() {
            return mo;
        }

        public void setMo(long mo) {
            this.mo = mo;
        }

        public int getTy() {
            return ty;
        }

        public void setTy(int ty) {
            this.ty = ty;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public String getTi() {
            return ti;
        }

        public void setTi(String ti) {
            this.ti = ti;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public int getSi() {
            return si;
        }

        public void setSi(int si) {
            this.si = si;
        }

        public int getIs_download() {
            return is_download;
        }

        public void setIs_download(int is_download) {
            this.is_download = is_download;
        }

        public String getIc() {
            return ic;
        }

        public void setIc(String ic) {
            this.ic = ic;
        }

        public List<String> getIm() {
            return im;
        }

        public void setIm(List<String> im) {
            this.im = im;
        }

        public List<Integer> getMa() {
            return ma;
        }

        public void setMa(List<Integer> ma) {
            this.ma = ma;
        }
}
