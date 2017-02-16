package com.haier.ai.bluetoothspeaker.bean.translation;

/**
 * Created by qx on 17-2-16.
 */

public class RequestTrans {

    /**
     * domain : translation
     * keywords : {"query":"很高兴见到你","from":"auto","to":"en"}
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
         * query : 很高兴见到你
         * from : auto
         * to : en
         */

        private String query;
        private String from;
        private String to;

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
    }
}
