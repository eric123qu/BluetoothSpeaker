package com.haier.ai.bluetoothspeaker.bean.poetry;

/**
 * Created by qx on 17-2-25.
 */

public class ResponsePoetry {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170225113857548000415154
     * data : {"domain":"poem","author":"李白","dynasty":"唐","title":"独坐敬亭山","content":"众鸟高飞尽，孤云独去闲。相看两不厌，只有敬亭山。"}
     */

    private String retCode;
    private String retInfo;
    private String sn;
    private DataBean data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * domain : poem
         * author : 李白
         * dynasty : 唐
         * title : 独坐敬亭山
         * content : 众鸟高飞尽，孤云独去闲。相看两不厌，只有敬亭山。
         */

        private String domain;
        private String author;
        private String dynasty;
        private String title;
        private String content;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDynasty() {
            return dynasty;
        }

        public void setDynasty(String dynasty) {
            this.dynasty = dynasty;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
