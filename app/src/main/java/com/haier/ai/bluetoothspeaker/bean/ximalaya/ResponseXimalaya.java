package com.haier.ai.bluetoothspeaker.bean.ximalaya;

import java.util.List;

/**
 * Created by qx on 17-2-17.
 */

public class ResponseXimalaya {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170217163641518000682914
     * data : {"domain":"ximalaya","tracks":[{"duration":2220,"intro":"","size":"17764269","source":"1","tags":"","time":"1205705584","title":"《日本梆子》郭德纲、于谦、王玥波 (郭德纲相声-郭德纲于谦)","url":"http://fdfs.xmcdn.com/group24/M0B/7F/67/wKgJMFihQu6yBOS0AQ8PrZoDtyk701.mp3"}]}
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
         * domain : ximalaya
         * tracks : [{"duration":2220,"intro":"","size":"17764269","source":"1","tags":"","time":"1205705584","title":"《日本梆子》郭德纲、于谦、王玥波 (郭德纲相声-郭德纲于谦)","url":"http://fdfs.xmcdn.com/group24/M0B/7F/67/wKgJMFihQu6yBOS0AQ8PrZoDtyk701.mp3"}]
         */

        private String domain;
        private List<TracksBean> tracks;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public List<TracksBean> getTracks() {
            return tracks;
        }

        public void setTracks(List<TracksBean> tracks) {
            this.tracks = tracks;
        }

        public static class TracksBean {
            /**
             * duration : 2220
             * intro :
             * size : 17764269
             * source : 1
             * tags :
             * time : 1205705584
             * title : 《日本梆子》郭德纲、于谦、王玥波 (郭德纲相声-郭德纲于谦)
             * url : http://fdfs.xmcdn.com/group24/M0B/7F/67/wKgJMFihQu6yBOS0AQ8PrZoDtyk701.mp3
             */

            private int duration;
            private String intro;
            private String size;
            private String source;
            private String tags;
            private String time;
            private String title;
            private String url;

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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
