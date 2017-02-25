package com.haier.ai.bluetoothspeaker.bean.poetry;

/**
 * Created by qx on 17-2-25.
 */

public class RequestPoetry {

    /**
     * domain : poem
     * keywords : {"query":"李白"}
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
         * query : 李白
         */

        private String query;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }
}
