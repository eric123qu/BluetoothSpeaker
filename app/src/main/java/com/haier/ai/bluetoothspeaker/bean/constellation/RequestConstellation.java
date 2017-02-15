package com.haier.ai.bluetoothspeaker.bean.constellation;

/**
 * Created by qx on 17-2-15.
 */

public class RequestConstellation {

    /**
     * domain : constellation
     * keywords : {"consName":"狮子座","type":"today"}
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
         * consName : 狮子座
         * type : today
         */

        private String consName;
        private String type;

        public String getConsName() {
            return consName;
        }

        public void setConsName(String consName) {
            this.consName = consName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
