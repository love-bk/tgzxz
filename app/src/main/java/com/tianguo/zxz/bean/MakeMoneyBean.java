package com.tianguo.zxz.bean;

/**
 * Created by admin on 2017/8/18.
 */

public class MakeMoneyBean  {
    private String title;
    private String url;
    private String subTitle;
    private String imgUrl;
    private int money;
    private int type;
    private int imgResId;

    public MakeMoneyBean(String title, String subTitle, int money, int type, int imgResId) {
        this.title = title;
        this.subTitle = subTitle;
        this.money = money;
        this.type = type;
        this.imgResId = imgResId;
    }

    public MakeMoneyBean(String title, int type, int imgResId) {
        this.title = title;
        this.type = type;
        this.imgResId = imgResId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
