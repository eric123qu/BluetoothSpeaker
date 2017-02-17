package com.haier.ai.bluetoothspeaker.bean.hotline;

/**
 * Created by qx on 17-2-17.
 */

public class RequestHotline {

    /**
     * domain : hotline
     * keywords : {"name":"报警电话"}
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
         * name : 报警电话
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
