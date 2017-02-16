package com.haier.ai.bluetoothspeaker.bean.Oilprice;

/**
 * Created by qx on 17-2-16.
 */

public class RequestOilprice {

    /**
     * domain : Oilprice
     * keywords : {"area":"北京","type":"95#"}
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
         * area : 北京
         * type : 95#
         */

        private String area;
        private String type;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
