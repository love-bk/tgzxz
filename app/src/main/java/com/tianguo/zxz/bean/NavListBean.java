package com.tianguo.zxz.bean;

/**
 * Created by andy on 2017/7/11.
 */
public class NavListBean {
    /**
     * data:[{i:navigation_novel,"d":"","is":0,"n":"小说"},]
     * <p/>
     * <p/>
     * <p/>
     * data:{navigation_novel : {"d":"","is":0,"n":"小说"}
     * aa : {"d":"","is":0,"n":"aa"}
     * a : {"d":"","is":0,"n":"a"}
     * navigation_change : {"d":"","is":0,"n":"领钱"}
     * b : {"d":"","is":0,"n":"a"}
     * baa : {"d":"","is":0,"n":"a"}
     * c : {"d":"","is":1,"n":"c"}
     * d : {"d":"","is":1,"n":"d"}
     * navigation_indiana : {"d":"","is":0,"n":"夺宝"}
     * navigation_live : {"d":"http://live.com","is":0,"n":"直播"}
     * navigation_navigation : {"d":"","is":1,"n":"导航"}
     * navigation_city : {"d":"","is":0,"n":"同城"}
     * navigation_search : {"d":"http://baidu.com","is":0,"n":"搜索"}
     * index_1 : {"d":"","is":0,"n":"aa"}
     * navigation_sell : {"d":"","is":0,"n":"特卖"}}
     */
    private String n;

    private String d;
    private String u;

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

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "NavListBean{" +
                "n='" + n + '\'' +
                ", d='" + d + '\'' +
                '}';
    }
}