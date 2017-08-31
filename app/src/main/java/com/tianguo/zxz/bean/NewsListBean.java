package com.tianguo.zxz.bean;


import java.util.List;

/**
 * Created by lx on 2017/4/19.
 */

public class NewsListBean {
    private HbBean hb;
    private List<NewsBean> news;
    private List<TagBean> otherType;
    private List<TagBean> userType;

    public HbBean getHb() {
        return hb;
    }

    public void setHb(HbBean hb) {
        this.hb = hb;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<TagBean> getOtherType() {
        return otherType;
    }

    public void setOtherType(List<TagBean> otherType) {
        this.otherType = otherType;
    }

    public List<TagBean> getUserType() {
        return userType;
    }

    public void setUserType(List<TagBean> userType) {
        this.userType = userType;
    }

    public static class HbBean {
        /**
         * num : 4
         * cdList : [90,90,90,90]
         */

        private int num;
        long nextTime;
        long tick;
        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public long getNextTime() {
            return nextTime;
        }

        public long getTick() {
            return tick;
        }
    }

    public static class NewsBean {
        public AwardBean getAwardBean() {
            return awardBean;
        }

        public void setAwardBean(AwardBean awardBean) {
            this.awardBean = awardBean;
        }

        private  AwardBean awardBean;

public  static  class  AwardBean{
    public long getNextTime() {
        return nextTime;
    }

    public void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getTick() {
        return tick;
    }

    public void setTick(long tick) {
        this.tick = tick;
    }

    long nextTime;
    int  index;
    long tick;
}
        public String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitles() {
            return titles;
        }

        public void setTitles(String titles) {
            this.titles = titles;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getLandingUrl() {
            return landingUrl;
        }

        public void setLandingUrl(String landingUrl) {
            this.landingUrl = landingUrl;
        }

        public String titles;
        public String contents;
        public String landingUrl;

        private String kw;
        private String body;
        private String title;
        private int t;
        private String md5;
        private int award;
        private String fr;
        private String day;
        private String thumb;
        private String brief;
        private String auth;
        private int i;

        public String getKw() {
            return kw;
        }

        public void setKw(String kw) {
            this.kw = kw;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }


    }


    /**
     * ads : [{"action_url":"http://c.gdt.qq.com/gdt_mclick.fcg?viewid=5iSFk4zT_ZrIFMtU2IgY8HojxUm3UMFH!MFW_J1LAh3elB1UKvdUA49pGpYW!MjSN5xggNu0!Rn!AeJs1enwAjrZGzj5rbrDW2DWiesqTQen1Vb0AFetmH!ioXgwkR!iA1fdmJOuLVBHj1T8cPLY9Nc!xff5ixVjqbXWaaF50okEyZwQITDpRkyUu398byANvOhFaQp18qpQePBmXfqdXA&jtype=0&i=1&os=2&acttype=0","adType":1,"targetid":"","api_name":0,"icon_src":"http://pgdt.gtimg.cn/gdt/0/DAACvqcAEsAEsAAEBYF2k5BItLCTic.jpg/0?ck=588cca594c0c297b4e4343e01ba62b65","expose_url":["http://v.gdt.qq.com/gdt_stats.fcg?count=1&viewid0=5iSFk4zT_ZrIFMtU2IgY8HojxUm3UMFH!MFW_J1LAh3elB1UKvdUA49pGpYW!MjSN5xggNu0!Rn!AeJs1enwAjrZGzj5rbrDW2DWiesqTQen1Vb0AFetmH!ioXgwkR!iA1fdmJOuLVBHj1T8cPLY9Nc!xff5ixVjqbXWaaF50okEyZwQITDpRkyUu398byANvOhFaQp18qpQePBmXfqdXA"],"text":"京东家电大促","click_url":"http://c.gdt.qq.com/gdt_mclick.fcg?viewid=5iSFk4zT_ZrIFMtU2IgY8HojxUm3UMFH!MFW_J1LAh3elB1UKvdUA49pGpYW!MjSN5xggNu0!Rn!AeJs1enwAjrZGzj5rbrDW2DWiesqTQen1Vb0AFetmH!ioXgwkR!iA1fdmJOuLVBHj1T8cPLY9Nc!xff5ixVjqbXWaaF50okEyZwQITDpRkyUu398byANvOhFaQp18qpQePBmXfqdXA&jtype=0&i=1&os=2&acttype=0","image_src":["http://pgdt.gtimg.cn/gdt/0/DAACvqcAUAALQABYBZGlx_DUi36lZu.jpg/0?ck=225f2b5407710297d098732a86ddf422"],"desc":"好货提前抢，家电限时秒杀！"}]
     * code : 0
     * message : ok
     */

    private int code;
    private String message;
    private List<AdsBean> ads;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean {
        /**
         * action_url : http://app.xiaomi.com/download/440188
         * adType : 2
         * api_name : 5
         * click_url : http://app.xiaomi.com/download/440188
         * clicker_url : ["http://log.ad.xiaomi.com/u/c?data=DfxkJBcEvGO1Udtg21iL0_b3mszCvYvHhCbIsbvVMgm5Do57Ic8gygvtmlap7Dhf8Z8lLMpR_JVZS1nUxydCBYLByTzDJjnmecniTRISLiZwLTqzzTBnG7DCWtvvWxA-DdLeIlBCOJopirRh-2Z6gFKnZFxi6ee3obubKq95lVNn9prw8Mn02wG4BHievA_qp8NhSHvjzMMwCCf7ugK_25vloEDswBH7Bm9gIkbJ_vIrTlQc-Q3UOy3kdRK4gXK8aOpKA03LLRw78VCOH-CrO8WDoyW9S4zrXUEXFSfJYtYi82-ziJ2-HiCuTRrqbza_q6EcKPMiagphPYjGbJiUuo-unXxhugumMLu20UQ-VnSHhk0LYGyv2GtuvsmLnIZe6RymwLEcOqw4ra6bk9ml0eL2Xph5imBM_LZvRWvptLlQbPRnoudw6hdOXiuXYw2VlDQoU7gtP4NqYsFO2fkxNsVNh8mhwWFCrGpSg4i8yLyvm8Ryi69sel0Yqt-AvDLKeHJirb9z52krP5kuxwOlYKy91VKcccrkdu25B4Qn39JpPq5E_3zc_hpYO8oseldl3O93ZPVAXAWdqdacU7AGKOTo3CJ_boKc9FgTatqqMfdo6koDTcstHDvxUI4f4Ks7xYOjJb1LjOtdQRcVJ8li1us6Q_Ub7lTk"]
         * desc : 今日头条极速版
         * expose_url : ["http://log.ad.xiaomi.com/u/v?data=DfxkJBcEvGO1Udtg21iL0_b3mszCvYvHhCbIsbvVMgm5Do57Ic8gygvtmlap7Dhf8Z8lLMpR_JVZS1nUxydCBYLByTzDJjnmecniTRISLiZwLTqzzTBnG7DCWtvvWxA-DdLeIlBCOJopirRh-2Z6gFKnZFxi6ee3obubKq95lVNn9prw8Mn02wG4BHievA_qp8NhSHvjzMMwCCf7ugK_25vloEDswBH7Bm9gIkbJ_vIrTlQc-Q3UOy3kdRK4gXK8aOpKA03LLRw78VCOH-CrO8WDoyW9S4zrXUEXFSfJYtYi82-ziJ2-HiCuTRrqbza_q6EcKPMiagphPYjGbJiUuo-unXxhugumMLu20UQ-VnSHhk0LYGyv2GtuvsmLnIZe6RymwLEcOqw4ra6bk9ml0eL2Xph5imBM_LZvRWvptLlQbPRnoudw6hdOXiuXYw2VlDQoU7gtP4NqYsFO2fkxNsVNh8mhwWFCrGpSg4i8yLyvm8Ryi69sel0Yqt-AvDLKeHJirb9z52krP5kuxwOlYKy91VKcccrkdu25B4Qn39JpPq5E_3zc_hpYO8oseldl3O93ZPVAXAWdqdacU7AGKOTo3CJ_boKc9FgTatqqMfdo6koDTcstHDvxUI4f4Ks7xYOjJb1LjOtdQRcVJ8li1us6Q_Ub7lTk"]
         * finishDownloadUrls : ["http://log.ad.xiaomi.com/u/de?data=DfxkJBcEvGO1Udtg21iL0_b3mszCvYvHhCbIsbvVMgm5Do57Ic8gygvtmlap7Dhf8Z8lLMpR_JVZS1nUxydCBYLByTzDJjnmecniTRISLiZwLTqzzTBnG7DCWtvvWxA-DdLeIlBCOJopirRh-2Z6gFKnZFxi6ee3obubKq95lVNn9prw8Mn02wG4BHievA_qp8NhSHvjzMMwCCf7ugK_25vloEDswBH7Bm9gIkbJ_vIrTlQc-Q3UOy3kdRK4gXK8aOpKA03LLRw78VCOH-CrO8WDoyW9S4zrXUEXFSfJYtYi82-ziJ2-HiCuTRrqbza_q6EcKPMiagphPYjGbJiUuo-unXxhugumMLu20UQ-VnSHhk0LYGyv2GtuvsmLnIZe6RymwLEcOqw4ra6bk9ml0eL2Xph5imBM_LZvRWvptLlQbPRnoudw6hdOXiuXYw2VlDQoU7gtP4NqYsFO2fkxNsVNh8mhwWFCrGpSg4i8yLyvm8Ryi69sel0Yqt-AvDLKeHJirb9z52krP5kuxwOlYKy91VKcccrkdu25B4Qn39JpPq5E_3zc_hpYO8oseldl3O93ZPVAXAWdqdacU7AGKOTo3CJ_boKc9FgTatqqMfdo6koDTcstHDvxUI4f4Ks7xYOjJb1LjOtdQRcVJ8li1us6Q_Ub7lTk"]
         * finishInstallUrls : ["http://log.ad.xiaomi.com/u/ie?data=DfxkJBcEvGO1Udtg21iL0_b3mszCvYvHhCbIsbvVMgm5Do57Ic8gygvtmlap7Dhf8Z8lLMpR_JVZS1nUxydCBYLByTzDJjnmecniTRISLiZwLTqzzTBnG7DCWtvvWxA-DdLeIlBCOJopirRh-2Z6gFKnZFxi6ee3obubKq95lVNn9prw8Mn02wG4BHievA_qp8NhSHvjzMMwCCf7ugK_25vloEDswBH7Bm9gIkbJ_vIrTlQc-Q3UOy3kdRK4gXK8aOpKA03LLRw78VCOH-CrO8WDoyW9S4zrXUEXFSfJYtYi82-ziJ2-HiCuTRrqbza_q6EcKPMiagphPYjGbJiUuo-unXxhugumMLu20UQ-VnSHhk0LYGyv2GtuvsmLnIZe6RymwLEcOqw4ra6bk9ml0eL2Xph5imBM_LZvRWvptLlQbPRnoudw6hdOXiuXYw2VlDQoU7gtP4NqYsFO2fkxNsVNh8mhwWFCrGpSg4i8yLyvm8Ryi69sel0Yqt-AvDLKeHJirb9z52krP5kuxwOlYKy91VKcccrkdu25B4Qn39JpPq5E_3zc_hpYO8oseldl3O93ZPVAXAWdqdacU7AGKOTo3CJ_boKc9FgTatqqMfdo6koDTcstHDvxUI4f4Ks7xYOjJb1LjOtdQRcVJ8li1us6Q_Ub7lTk"]
         * icon_src : http://t4.market.mi-img.com/download/AppStore/07d2eb517f73b4f2f2b54f6c7dd614ebd810386ca
         * image_src : ["http://t3.market.xiaomi.com/download/AppStore/07d2eb517f73b4f2f2b54f6c7dd614ebd810386ca"]
         * startDownloadUrls : ["http://log.ad.xiaomi.com/u/ds?data=DfxkJBcEvGO1Udtg21iL0_b3mszCvYvHhCbIsbvVMgm5Do57Ic8gygvtmlap7Dhf8Z8lLMpR_JVZS1nUxydCBYLByTzDJjnmecniTRISLiZwLTqzzTBnG7DCWtvvWxA-DdLeIlBCOJopirRh-2Z6gFKnZFxi6ee3obubKq95lVNn9prw8Mn02wG4BHievA_qp8NhSHvjzMMwCCf7ugK_25vloEDswBH7Bm9gIkbJ_vIrTlQc-Q3UOy3kdRK4gXK8aOpKA03LLRw78VCOH-CrO8WDoyW9S4zrXUEXFSfJYtYi82-ziJ2-HiCuTRrqbza_q6EcKPMiagphPYjGbJiUuo-unXxhugumMLu20UQ-VnSHhk0LYGyv2GtuvsmLnIZe6RymwLEcOqw4ra6bk9ml0eL2Xph5imBM_LZvRWvptLlQbPRnoudw6hdOXiuXYw2VlDQoU7gtP4NqYsFO2fkxNsVNh8mhwWFCrGpSg4i8yLyvm8Ryi69sel0Yqt-AvDLKeHJirb9z52krP5kuxwOlYKy91VKcccrkdu25B4Qn39JpPq5E_3zc_hpYO8oseldl3O93ZPVAXAWdqdacU7AGKOTo3CJ_boKc9FgTatqqMfdo6koDTcstHDvxUI4f4Ks7xYOjJb1LjOtdQRcVJ8li1us6Q_Ub7lTk"]
         * startInstallUrls : ["http://log.ad.xiaomi.com/u/is?data=DfxkJBcEvGO1Udtg21iL0_b3mszCvYvHhCbIsbvVMgm5Do57Ic8gygvtmlap7Dhf8Z8lLMpR_JVZS1nUxydCBYLByTzDJjnmecniTRISLiZwLTqzzTBnG7DCWtvvWxA-DdLeIlBCOJopirRh-2Z6gFKnZFxi6ee3obubKq95lVNn9prw8Mn02wG4BHievA_qp8NhSHvjzMMwCCf7ugK_25vloEDswBH7Bm9gIkbJ_vIrTlQc-Q3UOy3kdRK4gXK8aOpKA03LLRw78VCOH-CrO8WDoyW9S4zrXUEXFSfJYtYi82-ziJ2-HiCuTRrqbza_q6EcKPMiagphPYjGbJiUuo-unXxhugumMLu20UQ-VnSHhk0LYGyv2GtuvsmLnIZe6RymwLEcOqw4ra6bk9ml0eL2Xph5imBM_LZvRWvptLlQbPRnoudw6hdOXiuXYw2VlDQoU7gtP4NqYsFO2fkxNsVNh8mhwWFCrGpSg4i8yLyvm8Ryi69sel0Yqt-AvDLKeHJirb9z52krP5kuxwOlYKy91VKcccrkdu25B4Qn39JpPq5E_3zc_hpYO8oseldl3O93ZPVAXAWdqdacU7AGKOTo3CJ_boKc9FgTatqqMfdo6koDTcstHDvxUI4f4Ks7xYOjJb1LjOtdQRcVJ8li1us6Q_Ub7lTk"]
         * targetid : fa2128f5d3ef005a6433d2c409a0956c
         * text : 看新闻选择头条官方减肥版，更省空间
         */

        private String action_url;
        private int adType;
        private int api_name;
        private String click_url;
        private String desc;
        private String icon_src;
        private String targetid;
        private String text;
        private List<String> clicker_url;
        private List<String> expose_url;
        private List<String> finishDownloadUrls;
        private List<String> finishInstallUrls;
        private List<String> image_src;
        private List<String> startDownloadUrls;
        private List<String> startInstallUrls;

        public String getAction_url() {
            return action_url;
        }

        public void setAction_url(String action_url) {
            this.action_url = action_url;
        }

        public int getAdType() {
            return adType;
        }

        public void setAdType(int adType) {
            this.adType = adType;
        }

        public int getApi_name() {
            return api_name;
        }

        public void setApi_name(int api_name) {
            this.api_name = api_name;
        }

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon_src() {
            return icon_src;
        }

        public void setIcon_src(String icon_src) {
            this.icon_src = icon_src;
        }

        public String getTargetid() {
            return targetid;
        }

        public void setTargetid(String targetid) {
            this.targetid = targetid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<String> getClicker_url() {
            return clicker_url;
        }

        public void setClicker_url(List<String> clicker_url) {
            this.clicker_url = clicker_url;
        }

        public List<String> getExpose_url() {
            return expose_url;
        }

        public void setExpose_url(List<String> expose_url) {
            this.expose_url = expose_url;
        }

        public List<String> getFinishDownloadUrls() {
            return finishDownloadUrls;
        }

        public void setFinishDownloadUrls(List<String> finishDownloadUrls) {
            this.finishDownloadUrls = finishDownloadUrls;
        }

        public List<String> getFinishInstallUrls() {
            return finishInstallUrls;
        }

        public void setFinishInstallUrls(List<String> finishInstallUrls) {
            this.finishInstallUrls = finishInstallUrls;
        }

        public List<String> getImage_src() {
            return image_src;
        }

        public void setImage_src(List<String> image_src) {
            this.image_src = image_src;
        }

        public List<String> getStartDownloadUrls() {
            return startDownloadUrls;
        }

        public void setStartDownloadUrls(List<String> startDownloadUrls) {
            this.startDownloadUrls = startDownloadUrls;
        }

        public List<String> getStartInstallUrls() {
            return startInstallUrls;
        }

        public void setStartInstallUrls(List<String> startInstallUrls) {
            this.startInstallUrls = startInstallUrls;
        }
    }


}

