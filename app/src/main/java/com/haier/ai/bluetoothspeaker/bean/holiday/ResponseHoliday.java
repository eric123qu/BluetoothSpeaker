package com.haier.ai.bluetoothspeaker.bean.holiday;

/**
 * Created by qx on 17-2-17.
 */

public class ResponseHoliday {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170217132422318000379468
     * data : {"domain":"holiday","type":"春节","from":"2017-01-27","to":"2017-02-02","desc":"1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。"}
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
         * domain : holiday
         * type : 春节
         * from : 2017-01-27
         * to : 2017-02-02
         * desc : 1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。
         */

        private String domain;
        private String type;
        private String from;
        private String to;
        private String desc;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
