package com.haier.ai.bluetoothspeaker.bean.calendar;

/**
 * Created by qx on 17-2-20.
 */

public class ResponseCalendar {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170220172155582000397291
     * data : {"domain":"calendar","date":"2017-2-15","weekday":"星期三","animalsYear":"鸡","lunarYear":"丁酉年","lunar":"正月十九"}
     */

    private String retCode;
    private String retInfo;
    private String sn;
    private DataBean data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * domain : calendar
         * date : 2017-2-15
         * weekday : 星期三
         * animalsYear : 鸡
         * lunarYear : 丁酉年
         * lunar : 正月十九
         */

        private String domain;
        private String date;
        private String weekday;
        private String animalsYear;
        private String lunarYear;
        private String lunar;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeekday() {
            return weekday;
        }

        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        public String getAnimalsYear() {
            return animalsYear;
        }

        public void setAnimalsYear(String animalsYear) {
            this.animalsYear = animalsYear;
        }

        public String getLunarYear() {
            return lunarYear;
        }

        public void setLunarYear(String lunarYear) {
            this.lunarYear = lunarYear;
        }

        public String getLunar() {
            return lunar;
        }

        public void setLunar(String lunar) {
            this.lunar = lunar;
        }
    }
}
