package com.haier.ai.bluetoothspeaker.bean.hotline;

/**
 * Created by qx on 17-2-17.
 */

public class ResponseHotline {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170217134203981000774145
     * data : {"domain":"hotline","name":"报警电话","phone":"110"}
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
         * domain : hotline
         * name : 报警电话
         * phone : 110
         */

        private String domain;
        private String name;
        private String phone;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
