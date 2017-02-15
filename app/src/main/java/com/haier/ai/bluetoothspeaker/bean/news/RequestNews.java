package com.haier.ai.bluetoothspeaker.bean.news;

/**
 * Created by qx on 17-2-15.
 */

public class RequestNews {

    /**
     * domain : aqi
     * keywords : {"type":"头条","num":"1"}
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
         * type : 头条
         * num : 1
         */

        private String type;
        private String num;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
