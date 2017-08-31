package com.tianguo.zxz.bean;

/**
 * Created by lx on 2017/4/27.
 */

public class NewXQBean {

    /**
     * news : {"kw":"","body":"欢迎来到草民爆料广场，王者荣耀体验服昨天上线了重塑狄仁杰和新英雄干将莫邪，而战国争鸣版本就只剩下鬼谷子还未出现，反正他是接下来出现的，因为正式服的封面都有他的存在，所以跑不了，在此种情况下，另一位新英雄被曝光出来，这位新英雄名为\u201c铠\u201d，定位可战可坦，然后这个名字简单明了，不知道是哪个时代的英雄或者哪个传说中的英雄，知道的麻烦告诉草民！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"http://f.cdn.seewed.net/youmi1/1000_1493287301594.jpg\" width=\"100%\"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;技能详情（技能暂时都没用设定名称）<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;被动<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;普攻、二技能只命中一个敌人，这个敌人受到额外伤害，你们自行想象！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"http://f.cdn.seewed.net/youmi1/1000_1493287301625.jpg\" width=\"100%\"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;1技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;远程弹射攻击，类似高渐离1技能和蔡文姬的2技能，对敌方有晕眩效果，自己能回复CD和血量，属于控制消耗性的实用技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"http://f.cdn.seewed.net/youmi1/1000_1493287301673.jpg\" width=\"100%\"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;2技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;连续伤害技能，可以参考花木兰重剑形态的2技能。附带击飞效果，强化普攻以及冲锋效果<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"http://f.cdn.seewed.net/youmi1/1000_1493287301706.jpg\" width=\"100%\"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;3技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;强化效果，类似曹操大招！脱战后带增益回复<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"http://f.cdn.seewed.net/youmi1/1000_1493287301747.jpg\" width=\"100%\"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;\u201c铠\u201d是不是即将出现新英雄还有待确定，最终以官方发布为准！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;图片的图标好像是白起的技能图标，这东西的出现也是排在很后面的，因为还那么多重做英雄还没搞好，为什么英雄要重做，原因很简单：省了许多版权费，省了很多模型制作费，二是享受更多的乐趣，毕竟重做的英雄相当于新英雄了，现在都连名字要变了，如阿轲！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"http://f.cdn.seewed.net/youmi1/1000_1493287301781.gif\" width=\"100%\"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;我是草民：看完这个英雄的技能你们觉得叼么？留言分享你的看法，我们继续聊人生！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;","title":"王者荣耀·新英雄\"铠\"英雄曝光，这坦克绝对肉到爆炸...","t":7,"md5":"4c3a79f29e31c7dc6b74845ebb3c2c1f","fr":"王者荣耀·新英雄\"铠\"英雄曝光，这坦克绝对肉到爆炸...","day":"2017-04-27","thumb":"http://f.cdn.seewed.net/youmi1/1000_1493287301431.jpg,http://f.cdn.seewed.net/youmi1/1000_1493287301478.jpg,http://f.cdn.seewed.net/youmi1/1000_1493287301540.jpg","brief":"欢迎来到草民爆料广场，王者荣耀体验服昨天上线了重塑狄仁杰和新英雄干将莫邪，而战国争鸣版本就只剩下鬼谷","auth":"王者的荣耀教学","i":177902}
     */
    private double award;

    public long getIsAward() {
        return isAward;
    }

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
    public void setIsAward(int isAward) {
        this.isAward = isAward;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;



    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }

    private double gold;
    private int isUp;
    private long isAward;
    private String awardKey;

    public String getAwardKey() {
        return awardKey;
    }

    public void setAwardKey(String awardKey) {
        this.awardKey = awardKey;
    }


    public double getAward() {
        return award;
    }

    public void setAward(double award) {
        this.award = award;
    }

    private NewsBean news;

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public static class NewsBean {
        /**
         * kw :
         * body : 欢迎来到草民爆料广场，王者荣耀体验服昨天上线了重塑狄仁杰和新英雄干将莫邪，而战国争鸣版本就只剩下鬼谷子还未出现，反正他是接下来出现的，因为正式服的封面都有他的存在，所以跑不了，在此种情况下，另一位新英雄被曝光出来，这位新英雄名为“铠”，定位可战可坦，然后这个名字简单明了，不知道是哪个时代的英雄或者哪个传说中的英雄，知道的麻烦告诉草民！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://f.cdn.seewed.net/youmi1/1000_1493287301594.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;技能详情（技能暂时都没用设定名称）<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;被动<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;普攻、二技能只命中一个敌人，这个敌人受到额外伤害，你们自行想象！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://f.cdn.seewed.net/youmi1/1000_1493287301625.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;1技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;远程弹射攻击，类似高渐离1技能和蔡文姬的2技能，对敌方有晕眩效果，自己能回复CD和血量，属于控制消耗性的实用技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://f.cdn.seewed.net/youmi1/1000_1493287301673.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;2技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;连续伤害技能，可以参考花木兰重剑形态的2技能。附带击飞效果，强化普攻以及冲锋效果<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://f.cdn.seewed.net/youmi1/1000_1493287301706.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;3技能<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;强化效果，类似曹操大招！脱战后带增益回复<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://f.cdn.seewed.net/youmi1/1000_1493287301747.jpg" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;“铠”是不是即将出现新英雄还有待确定，最终以官方发布为准！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;图片的图标好像是白起的技能图标，这东西的出现也是排在很后面的，因为还那么多重做英雄还没搞好，为什么英雄要重做，原因很简单：省了许多版权费，省了很多模型制作费，二是享受更多的乐趣，毕竟重做的英雄相当于新英雄了，现在都连名字要变了，如阿轲！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<img src="http://f.cdn.seewed.net/youmi1/1000_1493287301781.gif" width="100%"/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;我是草民：看完这个英雄的技能你们觉得叼么？留言分享你的看法，我们继续聊人生！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
         * title : 王者荣耀·新英雄"铠"英雄曝光，这坦克绝对肉到爆炸...
         * t : 7
         * md5 : 4c3a79f29e31c7dc6b74845ebb3c2c1f
         * fr : 王者荣耀·新英雄"铠"英雄曝光，这坦克绝对肉到爆炸...
         * day : 2017-04-27
         * thumb : http://f.cdn.seewed.net/youmi1/1000_1493287301431.jpg,http://f.cdn.seewed.net/youmi1/1000_1493287301478.jpg,http://f.cdn.seewed.net/youmi1/1000_1493287301540.jpg
         * brief : 欢迎来到草民爆料广场，王者荣耀体验服昨天上线了重塑狄仁杰和新英雄干将莫邪，而战国争鸣版本就只剩下鬼谷
         * auth : 王者的荣耀教学
         * i : 177902
         */

        private String kw;
        private String body;
        private String title;
        private int t;
        private String md5;
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
}
