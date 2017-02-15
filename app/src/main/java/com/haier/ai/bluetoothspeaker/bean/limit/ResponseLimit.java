package com.haier.ai.bluetoothspeaker.bean.limit;

/**
 * Created by qx on 17-2-15.
 */

public class ResponseLimit {
    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 123456789
     * data : {"domain":"limit","date":"2017-01-11","number":"京A12345","limit":"1,6","islimit":"No"}
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
         * domain : limit
         * date : 2017-01-11
         * number : 京A12345
         * limit : 1,6
         * islimit : No
         */

        private String domain;
        private String date;
        private String number;
        private String limit;
        private String islimit;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getIslimit() {
            return islimit;
        }

        public void setIslimit(String islimit) {
            this.islimit = islimit;
        }
    }
}
