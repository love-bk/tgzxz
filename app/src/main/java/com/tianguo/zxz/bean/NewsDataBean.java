package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by lx on 2017/6/29.
 */

public class NewsDataBean {
        private AdBean ad;
        private List<NewsBean> news;

        public AdBean getAd() {
            return ad;
        }

        public void setAd(AdBean ad) {
            this.ad = ad;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class AdBean {
            /**
             * p : http://10.0.0.42:8080/upload/ad/pic/4414.jpg
             * a : 陌陌
             * r : 月结
             * c : http://10.0.0.42:8080/upload/ad/icon/4410.jpg
             * t : 3
             * d : 2017-06-14 11:37:48
             * u :
             * w : http://m.tianinfo.cn/tianguozixunzhuan_1.0.1.7_37_guanwang_sign.apk
             * i : 4412
             * m : 2017-06-12 11:36:46
             * n : 她距离你不到600米，进来认识一下吧！
             */

            private String p;
            private String a;
            private String r;
            private String c;
            private int t;
            private String d;
            private String u;
            private String w;
            private int i;
            private String m;
            private String n;

            public String getP() {
                return p;
            }

            public void setP(String p) {
                this.p = p;
            }

            public String getA() {
                return a;
            }

            public void setA(String a) {
                this.a = a;
            }

            public String getR() {
                return r;
            }

            public void setR(String r) {
                this.r = r;
            }

            public String getC() {
                return c;
            }

            public void setC(String c) {
                this.c = c;
            }

            public int getT() {
                return t;
            }

            public void setT(int t) {
                this.t = t;
            }

            public String getD() {
                return d;
            }

            public void setD(String d) {
                this.d = d;
            }

            public String getU() {
                return u;
            }

            public void setU(String u) {
                this.u = u;
            }

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }

            public int getI() {
                return i;
            }

            public void setI(int i) {
                this.i = i;
            }

            public String getM() {
                return m;
            }

            public void setM(String m) {
                this.m = m;
            }

            public String getN() {
                return n;
            }

            public void setN(String n) {
                this.n = n;
            }

            @Override
            public String toString() {
                return "AdBean{" +
                        "p='" + p + '\'' +
                        ", a='" + a + '\'' +
                        ", r='" + r + '\'' +
                        ", c='" + c + '\'' +
                        ", t=" + t +
                        ", d='" + d + '\'' +
                        ", u='" + u + '\'' +
                        ", w='" + w + '\'' +
                        ", i=" + i +
                        ", m='" + m + '\'' +
                        ", n='" + n + '\'' +
                        '}';
            }
        }

        public static class NewsBean {
            /**
             * brief : 请点击上面　　免费订阅女友带她闺蜜来睡觉我睡哪儿某剩女，一天再次去相亲。男问：你有什么业余爱好啊？女
             * t : 7
             * auth : 爆笑兔
             * thumb : http://file.cdn.tianh5.cn/news1/1000_1498530196779.jpg,http://file.cdn.tianh5.cn/news1/1000_1498530197145.jpg,http://file.cdn.tianh5.cn/news1/1000_1498530197529.jpg
             * i : 4510
             * fr : 有种你出来跟老娘单挑
             * kw :
             * title : 有种你出来跟老娘单挑
             * body : 请点击上面　<img src="http://file.cdn.tianh5.cn/news1/1000_1498530197649.jpg" width="100%"/>　免费订阅<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;女友带她闺蜜来睡觉我睡哪儿<img src="http://file.cdn.tianh5.cn/news1/1000_1498530197885.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;某剩女，一天再次去相亲。 男问：你有什么业余爱好啊？ 女答：我喜欢养猫。女反问：你有什么爱好啊？ 男答：我喜欢养鸟。 剩女笑曰：我们俩结婚吧！这么兴趣相投的实在不多。没事的时候，你在家玩鸟，我在家玩咪咪。<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;啊啊啊啊 快到高潮就萎了<img src="http://file.cdn.tianh5.cn/news1/1000_1498530200530.gif" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;问哥们泡妞要怎么才会提高成功率，他说：“直接把他推倒！必定成功。”之后相亲，和妹子出去我把她推倒了，妹子骂骂咧咧的跑了，都怪我没注意路边的水坑。<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;终于把双手都解放可以边看边撸了<img src="http://file.cdn.tianh5.cn/news1/1000_1498530200827.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;放学老爸来接我，等了很久都没等到我，后来他回家后就把我打了一顿。说：“你逃课，不去上学，白白让我在你们小学门口等了一个小时。我含着泪说：“爸，我现在上初一了。”<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;据说这是广告，怎么有AV的即视感？<img src="http://file.cdn.tianh5.cn/news1/1000_1498530203268.gif" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;认识一个蓝翔的女同学，毕业以后去红灯区做起了妈妈桑，我很惊讶，一打听才知道，原来她学的是挖掘鸡。<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;有种你出来跟老娘单挑<img src="http://file.cdn.tianh5.cn/news1/1000_1498530203546.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;今天出门太急，开车快了点，不小心刮倒一老太太，赶紧下车说到：“姐姐你没事吧？”<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;老太太爬起来拍拍衣服说道：“小伙子嘴巴真甜，姐没事，你走吧！”<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;兄弟们，知道为什么从为旷课的我数学为什么这么差了吧<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://file.cdn.tianh5.cn/news1/1000_1498530204238.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;路上一情侣吵架，那女的直接动手，掐捏，扭都用上了。<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;看那男的表情那个难受啊。<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;只见那男的一甩手，生气道 “你等我回去练肌肉！”<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;“练肌肉又能怎样？<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;那男的愤愤的回答 “我让你捏不动！”<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;这是铅球吗<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://file.cdn.tianh5.cn/news1/1000_1498530204884.gif" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;“亲爱的，我会变美的，人家都说女大十八变嘛！”<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;“得了吧，孙悟空七十二变还是只猴子，猪八戒三十六变还是头猪，<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;你说你十八变还有啥指望的？”<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
             * day : 2017-06-24
             * md5 : 3b3a7a25aba5a3c6fddc7369dfc67a92
             */

            private String brief;
            private int t;
            private String auth;
            private String thumb;
            private int i;
            private String fr;
            private String kw;
            private String title;
            private String body;
            private String day;
            private String md5;

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public int getT() {
                return t;
            }

            public void setT(int t) {
                this.t = t;
            }

            public String getAuth() {
                return auth;
            }

            public void setAuth(String auth) {
                this.auth = auth;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getI() {
                return i;
            }

            public void setI(int i) {
                this.i = i;
            }

            public String getFr() {
                return fr;
            }

            public void setFr(String fr) {
                this.fr = fr;
            }

            public String getKw() {
                return kw;
            }

            public void setKw(String kw) {
                this.kw = kw;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }
        }
}
