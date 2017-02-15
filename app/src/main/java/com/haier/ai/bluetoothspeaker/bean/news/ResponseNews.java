package com.haier.ai.bluetoothspeaker.bean.news;

import java.util.List;

/**
 * Created by qx on 17-2-15.
 */

public class ResponseNews {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 123456789
     * data : {"domain":"news","news":[{"title":"教育部国家语委：高校学生应具有一定的书法鉴赏能力","date":"2017-02-08 16:58","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170208165855616.html"},{"title":"诺贝尔和平奖获得者家中遭窃 奖状等均被盗走","date":"2017-02-08 16:21","category":"头条","author_name":"环球网","url":"http://mini.eastday.com/mobile/170208162143157.html"}]}
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
         * domain : news
         * news : [{"title":"教育部国家语委：高校学生应具有一定的书法鉴赏能力","date":"2017-02-08 16:58","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170208165855616.html"},{"title":"诺贝尔和平奖获得者家中遭窃 奖状等均被盗走","date":"2017-02-08 16:21","category":"头条","author_name":"环球网","url":"http://mini.eastday.com/mobile/170208162143157.html"}]
         */

        private String domain;
        private List<NewsBean> news;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {
            /**
             * title : 教育部国家语委：高校学生应具有一定的书法鉴赏能力
             * date : 2017-02-08 16:58
             * category : 头条
             * author_name : 中国新闻网
             * url : http://mini.eastday.com/mobile/170208165855616.html
             */

            private String title;
            private String date;
            private String category;
            private String author_name;
            private String url;
            private String content;

            public String getTitle() {
                return title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
