package com.haier.ai.bluetoothspeaker.bean.weather;

/**
 * Created by qx on 17-2-15.
 */

public class RequestWeather {


    /**
     * domain : weather
     * keywords : {"city":"北京","date":""}
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
         * date :
         */

        private String city;
        private String date;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
