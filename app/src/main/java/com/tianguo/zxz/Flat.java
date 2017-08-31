package com.tianguo.zxz;


import com.tianguo.zxz.bean.MyGGbean;

import java.util.List;

/**
 * Created by lx on 2017/5/17.
 */

public class Flat {
    public static final String REQUESTFLAG = "requestFlag";
    /**
     * 神马的链接
     */
    public static String  ShenMaUrl="https://yz.m.sm.cn/s?from=wm280283&q=";
    /**
     * 首页标题通
     */
   public static  String [] NewsTeile =  new String[]{"推荐", "娱乐", "视频", "图片", "搞笑", "女人", "生活", "科技"};
   public static  String [] YYXZTabTitle =  new String[]{"应用", "手游"};
   public static  String [] PAYTabTitle =  new String[]{"金币", "现金"};
   public static  String [] ApprenticeTabTitle =  new String[]{"徒弟列表", "徒孙列表"};
   public static  String  YYXZ_FLAG =  "应用下载";
   public static final String  TASK_FLAG =  "taskflag";

public  static List<MyGGbean.Cpa2Bean> list ;
    public  static List<MyGGbean.Cpa1Bean> cp1 ;
    public  static List<MyGGbean.Cpa3Bean> cp3 ;
    public  static List<MyGGbean.Cpa4Bean> cp4 ;


}
