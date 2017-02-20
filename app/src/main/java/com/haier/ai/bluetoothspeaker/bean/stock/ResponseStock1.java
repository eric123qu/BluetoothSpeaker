package com.haier.ai.bluetoothspeaker.bean.stock;

/**
 * Created by qx on 17-2-20.
 */

public class ResponseStock1 {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170220182527796000337079
     * data : {"type":"share","gid":"sh600690","increPer":"3.91","increase":"0.400","name":"青岛海尔","todayStartPri":"10.270","yestodEndPri":"10.230","nowPri":"10.630","todayMax":"10.650","todayMin":"10.270","traNumber":"814628","traAmount":"855822455.000","date":"2017-02-20","time":"15:00:00","nowPic":"0.400","rate":"3.91"}
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
         * type : share
         * gid : sh600690
         * increPer : 3.91
         * increase : 0.400
         * name : 青岛海尔
         * todayStartPri : 10.270
         * yestodEndPri : 10.230
         * nowPri : 10.630
         * todayMax : 10.650
         * todayMin : 10.270
         * traNumber : 814628
         * traAmount : 855822455.000
         * date : 2017-02-20
         * time : 15:00:00
         * nowPic : 0.400
         * rate : 3.91
         */

        private String type;
        private String gid;
        private String increPer;
        private String increase;
        private String name;
        private String todayStartPri;
        private String yestodEndPri;
        private String nowPri;
        private String todayMax;
        private String todayMin;
        private String traNumber;
        private String traAmount;
        private String date;
        private String time;
        private String nowPic;
        private String rate;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTodayStartPri() {
            return todayStartPri;
        }

        public void setTodayStartPri(String todayStartPri) {
            this.todayStartPri = todayStartPri;
        }

        public String getYestodEndPri() {
            return yestodEndPri;
        }

        public void setYestodEndPri(String yestodEndPri) {
            this.yestodEndPri = yestodEndPri;
        }

        public String getNowPri() {
            return nowPri;
        }

        public void setNowPri(String nowPri) {
            this.nowPri = nowPri;
        }

        public String getTodayMax() {
            return todayMax;
        }

        public void setTodayMax(String todayMax) {
            this.todayMax = todayMax;
        }

        public String getTodayMin() {
            return todayMin;
        }

        public void setTodayMin(String todayMin) {
            this.todayMin = todayMin;
        }

        public String getTraNumber() {
            return traNumber;
        }

        public void setTraNumber(String traNumber) {
            this.traNumber = traNumber;
        }

        public String getTraAmount() {
            return traAmount;
        }

        public void setTraAmount(String traAmount) {
            this.traAmount = traAmount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNowPic() {
            return nowPic;
        }

        public void setNowPic(String nowPic) {
            this.nowPic = nowPic;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }
}
