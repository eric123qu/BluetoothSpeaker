package com.haier.ai.bluetoothspeaker.bean.calendar;

/**
 * Created by qx on 17-2-20.
 */

public class RequestCalendar {

    /**
     * domain : calendar
     * keywords : {"date":"2017-02-15"}
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
         * date : 2017-02-15
         */

        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
