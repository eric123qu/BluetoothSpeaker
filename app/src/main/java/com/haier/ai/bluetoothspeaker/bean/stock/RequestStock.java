package com.haier.ai.bluetoothspeaker.bean.stock;

/**
 * Created by qx on 17-2-16.
 */

public class RequestStock {


    /**
     * domain : stock
     * keywords : {"gid":"600690","type":"today"}
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
         * gid : 600690
         * type : today
         */

        private String gid;
        private String type;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
