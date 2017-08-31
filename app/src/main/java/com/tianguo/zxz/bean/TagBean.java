package com.tianguo.zxz.bean;

import java.io.Serializable;

public class TagBean implements Serializable{
    /**
     * text : 推荐
     * type : 1
     */

    private String text;
    private int type;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TagBean(String text, int type,String url) {
        this.text = text;
        this.type = type;
        this.url = url;
    }

    public TagBean(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "TagBean{" +
                "text='" + text + '\'' +
                ", type=" + type +
                '}';
    }
}