package com.haier.ai.bluetoothspeaker.bean.ximalaya;

/**
 * Created by qx on 17-2-17.
 */

public class RequestXimalaya {

    /**
     * domain : ximalaya
     * keywords : {"query":"郭德纲","num":"1","type":"相声"}
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
         * query : 郭德纲
         * num : 1
         * type : 相声
         */

        private String query;
        private String num;
        private String type;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
