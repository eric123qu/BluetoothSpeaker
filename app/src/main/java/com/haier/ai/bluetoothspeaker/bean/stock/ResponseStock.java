package com.haier.ai.bluetoothspeaker.bean.stock;

/**
 * Created by qx on 17-2-16.
 */

public class ResponseStock {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170216142811230000657951
     * data : {"dealNum":"182524451","dealPri":"188563039723","highPri":"3230.2754","increPer":"0.16","increase":"5.1405","lowpri":"3207.7852","name":"上证指数","nowpri":"3218.1262","openPri":"3210.3567","time":"2017-02-16 14:25:16","yesPri":"3212.9857","type":"index"}
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
         * dealNum : 182524451
         * dealPri : 188563039723
         * highPri : 3230.2754
         * increPer : 0.16
         * increase : 5.1405
         * lowpri : 3207.7852
         * name : 上证指数
         * nowpri : 3218.1262
         * openPri : 3210.3567
         * time : 2017-02-16 14:25:16
         * yesPri : 3212.9857
         * type : index
         */

        private String dealNum;
        private String dealPri;
        private String highPri;
        private String increPer;
        private String increase;
        private String lowpri;
        private String name;
        private String nowpri;
        private String openPri;
        private String time;
        private String yesPri;
        private String type;

        public String getDealNum() {
            return dealNum;
        }

        public void setDealNum(String dealNum) {
            this.dealNum = dealNum;
        }

        public String getDealPri() {
            return dealPri;
        }

        public void setDealPri(String dealPri) {
            this.dealPri = dealPri;
        }

        public String getHighPri() {
            return highPri;
        }

        public void setHighPri(String highPri) {
            this.highPri = highPri;
        }

        public String getIncrePer() {
            return increPer;
        }

        public void setIncrePer(String increPer) {
            this.increPer = increPer;
        }

        public String getIncrease() {
            return increase;
        }

        public void setIncrease(String increase) {
            this.increase = increase;
        }

        public String getLowpri() {
            return lowpri;
        }

        public void setLowpri(String lowpri) {
            this.lowpri = lowpri;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNowpri() {
            return nowpri;
        }

        public void setNowpri(String nowpri) {
            this.nowpri = nowpri;
        }

        public String getOpenPri() {
            return openPri;
        }

        public void setOpenPri(String openPri) {
            this.openPri = openPri;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getYesPri() {
            return yesPri;
        }

        public void setYesPri(String yesPri) {
            this.yesPri = yesPri;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
