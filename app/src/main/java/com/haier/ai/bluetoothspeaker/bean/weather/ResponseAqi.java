package com.haier.ai.bluetoothspeaker.bean.weather;

/**
 * Created by qx on 17-2-16.
 */

public class ResponseAqi {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170217111909837000478101
     * data : {"domain":"aqi","date":"2017-02-15","city":"北京","aqi":321,"aqs":"严重污染","pp":"PM25","pm25":271,"update":"2017-02-15 23:00:00"}
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
         * domain : aqi
         * date : 2017-02-15
         * city : 北京
         * aqi : 321
         * aqs : 严重污染
         * pp : PM25
         * pm25 : 271
         * update : 2017-02-15 23:00:00
         */

        private String domain;
        private String date;
        private String city;
        private int aqi;
        private String aqs;
        private String pp;
        private int pm25;
        private String update;

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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public String getAqs() {
            return aqs;
        }

        public void setAqs(String aqs) {
            this.aqs = aqs;
        }

        public String getPp() {
            return pp;
        }

        public void setPp(String pp) {
            this.pp = pp;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }
    }
}
