package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by andy on 2017/7/6.
 */
public class RecodBean {


    private List<String> withdrawList;

    public List<String> getWithdrawList() {
        return withdrawList;
    }

    public void setWithdrawList(List<String> withdrawList) {
        this.withdrawList = withdrawList;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (withdrawList != null && withdrawList.size()>0){
            for (String str : withdrawList) {
                s.append(str+"      ");
            }
        }
        return s.toString();
    }
}
