package com.tianguo.zxz.bean;

/**
 * Created by lx on 2017/4/21.
 */

public class VersionInfo {

        /**
         * ver : 1.0.1.0
         * url : http://www.tianinfo.cn/apk/qutoutiao.apk
         * info : 使用原生版本开发，使用更流畅
         */
        int needBindPhone;

    public int getNeedBindPhone() {
        return needBindPhone;
    }

    public void setNeedBindPhone(int needBindPhone) {
        this.needBindPhone = needBindPhone;
    }

        private String ver;
        private String url;
        private String info;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
}
