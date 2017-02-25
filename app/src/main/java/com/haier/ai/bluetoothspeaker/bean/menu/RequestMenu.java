package com.haier.ai.bluetoothspeaker.bean.menu;

/**
 * Created by qx on 17-2-25.
 */

public class RequestMenu {

    /**
     * domain : cookbook
     * keywords : {"name":"宫保鸡丁"}
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
         * name : 宫保鸡丁
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
