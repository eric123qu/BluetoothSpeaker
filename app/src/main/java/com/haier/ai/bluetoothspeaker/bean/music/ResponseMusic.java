package com.haier.ai.bluetoothspeaker.bean.music;

/**
 * author: qu
 * date: 17-1-13
 * introduce:
 */

public class ResponseMusic {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 123456789
     * data : {"domain":"music","url":"http://abc.com/def.mp3","song":"好久不见","singer":"陈奕迅","album":"认了吧","genre":"校园"}
     */

    private String retCode;
    private String retInfo;
    private String sn;
    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * domain : music
         * url : http://abc.com/def.mp3
         * song : 好久不见
         * singer : 陈奕迅
         * album : 认了吧
         * genre : 校园
         */

        private String domain;
        private String url;
        private String song;
        private String singer;
        private String album;
        private String genre;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSong() {
            return song;
        }

        public void setSong(String song) {
            this.song = song;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }
    }
}
