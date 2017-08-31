package com.tianguo.zxz.bean;


import java.util.List;

/**
 * Created by lx on 2017/4/27.
 */

public class ReCiBean  {

    private List<ListBean> list;
    /**
     * code : 0
     * data : {"hb":{"cd":0,"num":1}}
     * tick : 1503563260104
     */
    private int code;
    private long tick;

    /**
     * code : 0
     * data : {"hb":{"cd":0,"num":1}}
     * tick : 1503563260104
     */

    public List<ListBean> getList() {
        return list;
    }
    public void setList(List<ListBean> list) {
        this.list = list;
    }
    public static class ListBean {
        /**
         * bus : 0
         * name : 九评
         */

        private int bus;
        private String name;

        public int getBus() {
            return bus;
        }

        public void setBus(int bus) {
            this.bus = bus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "bus=" + bus +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    /**
     * title : 百度钱包负责人离职
     * hot_flag : 1
     * url : s?q=百度钱包负责人离职&by=hot&from=wm%2A%2A%2A%2A%2A%2A
     */
    private double award;


    public double getAward() {
        return award;
    }

    public void setAward(double award) {
        this.award = award;
    }
    private String title;
    private int hot_flag;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHot_flag() {
        return hot_flag;
    }

    public void setHot_flag(int hot_flag) {
        this.hot_flag = hot_flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



        private HbBean hb;

        public HbBean getHb() {
            return hb;
        }

        public void setHb(HbBean hb) {
            this.hb = hb;
        }

        public static class HbBean {
            /**
             * cd : 0
             * num : 1
             */

            private int num;

            public long getNextTime() {
                return nextTime;
            }

            public void setNextTime(long nextTime) {
                this.nextTime = nextTime;
            }

            public long getTick() {
                return tick;
            }

            public void setTick(long tick) {
                this.tick = tick;
            }

            private  long nextTime;
private  long tick;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }
}
