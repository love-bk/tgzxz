package com.tianguo.zxz.bean;

import java.util.List;

/**
 * Created by lx on 2017/8/15.
 */

public class YYdetallBean {
        private TaskBean task;
        private List<TaskListBean> taskList;

        public TaskBean getTask() {
            return task;
        }

        public void setTask(TaskBean task) {
            this.task = task;
        }

        public List<TaskListBean> getTaskList() {
            return taskList;
        }

        public void setTaskList(List<TaskListBean> taskList) {
            this.taskList = taskList;
        }

        public static class TaskBean {

            public int getDownload_num() {
                return download_num;
            }

            public void setDownload_num(int download_num) {
                this.download_num = download_num;
            }

            private  int download_num;
            public int getIs_download() {
                return is_download;
            }

            public void setIs_download(int is_download) {
                this.is_download = is_download;
            }

            /**
             * b : 打开“微信”，注册登陆后，玩2分钟
             * mo : 20
             * c : 看新闻赚钱神器！搜罗热点资讯，无论是社会时事、各类段子、福利美图、养生健康、运动技能......趣头条看这些资讯都能赚钱。看广告赚钱够每个月交水电费了
             - 个性化推荐
             兴趣推荐，栏目订阅，随时随地看你想看。
             趣味视频
             - 海量视频看不停，社会热点、搞笑逗比、科技前沿、二次元，有趣的人群在此相遇！
             打发无聊
             - 娱乐，搞笑，段子，微视频，新闻，时事热点一手掌握
             赚零花钱
             看看广告就能赚钱！支付宝钱包，总是鼓鼓的！
             邀请一个微信好友或者QQ好友用趣头条，就送2元现金奖励~
             * im : ["http://10.0.0.44:8082//upload/task/pic/4528.png","http://10.0.0.44:8082//upload/task/pic/4622.png","http://10.0.0.44:8082//upload/task/pic/4529.jpg"]
             * ty : 1
             * num : 100
             * i : 4535
             * ma : [1,2]
             * ti : 一点
             * u : https://download.yidianzixun.com/android/yd_4.2.0.2_0d131ac-972.apk
             * si : 25
             * ic : http://www.sweetinfo.cn/upload/task/pic/4623.png
             * so : 3
             * have_get_money : 0
             */
            private  int is_download;

            private String b;
            private int mo;
            private String c;
            private int ty;
            private int num;
            private int i;
            private String ti;
            private String u;
            private int si;
            private String ic;
            private int so;
            private int have_get_money;
            private List<String> im;
            private List<Integer> ma;

            public String getB() {
                return b;
            }

            public void setB(String b) {
                this.b = b;
            }

            public int getMo() {
                return mo;
            }

            public void setMo(int mo) {
                this.mo = mo;
            }

            public String getC() {
                return c;
            }

            public void setC(String c) {
                this.c = c;
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

            public String getIc() {
                return ic;
            }

            public void setIc(String ic) {
                this.ic = ic;
            }

            public int getSo() {
                return so;
            }

            public void setSo(int so) {
                this.so = so;
            }

            public int getHave_get_money() {
                return have_get_money;
            }

            public void setHave_get_money(int have_get_money) {
                this.have_get_money = have_get_money;
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

        public static class TaskListBean {
            /**
             * duration : 60
             * id : 1111
             * type : 1
             * status : 0
             * desc : <p style='font-size:15px'>首次安装<span style='color:green;font-size:12px'>+50金币</span><br><span style='color:red;'>注意:注意前方有坑，请绕行</span><br>任务1------------</p>
             */

            private int duration;
            private int id;
            private int type;
            private int status;
            private String desc;

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
}
