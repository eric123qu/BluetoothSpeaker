package com.haier.ai.bluetoothspeaker.bean.weather;

/**
 * Created by qx on 17-2-15.
 */

public class RequestWeather {

    /**
     * domain : aqi
     * keywords : {"date":"2017-01-20","city":"北京"}
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
         * date : 2017-01-20
         * city : 北京
         */

        private String date;
        private String city;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
