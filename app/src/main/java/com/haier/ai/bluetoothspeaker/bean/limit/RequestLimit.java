package com.haier.ai.bluetoothspeaker.bean.limit;

/**
 * Created by qx on 17-2-15.
 */

public class RequestLimit {


    /**
     * domain : limit
     * keywords : {"date":"2017-01-11","number":"京A12345"}
     */

    private String domain;
    private KeywordsBean keywords;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public KeywordsBean getKeywords() {
        return keywords;
    }

    public void setKeywords(KeywordsBean keywords) {
        this.keywords = keywords;
    }

    public static class KeywordsBean {
        /**
         * date : 2017-01-11
         * number : 京A12345
         */

        private String date;
        private String number;

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
    }
}
