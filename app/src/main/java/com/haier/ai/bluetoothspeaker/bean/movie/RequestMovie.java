package com.haier.ai.bluetoothspeaker.bean.movie;

/**
 * Created by qx on 17-2-17.
 */

public class RequestMovie {

    /**
     * domain :  movies
     * keywords : {"city":"北京"}
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
         * city : 北京
         */

        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
