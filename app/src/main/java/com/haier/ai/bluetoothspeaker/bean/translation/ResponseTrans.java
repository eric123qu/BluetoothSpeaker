package com.haier.ai.bluetoothspeaker.bean.translation;

/**
 * Created by qx on 17-2-16.
 */

public class ResponseTrans {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170216190612757000589076
     * data : {"query":"很高兴见到你","from":"auto","to":"en","result":"Nice to meet you"}
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
         * query : 很高兴见到你
         * from : auto
         * to : en
         * result : Nice to meet you
         */

        private String query;
        private String from;
        private String to;
        private String result;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
