package com.haier.ai.bluetoothspeaker.bean.music;

/**
 * author: qu
 * date: 17-1-13
 * introduce:
 */

public class RequestMusic {

    /**
     * domain : music
     * keywords : {"song":"好久不见","singer":"陈奕迅","album":"认了吧","genre":"校园"}
     */

    private String domain;
    private KeywordsEntity keywords;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public KeywordsEntity getKeywords() {
        return keywords;
    }

    public void setKeywords(KeywordsEntity keywords) {
        this.keywords = keywords;
    }

    public static class KeywordsEntity {
        /**
         * song : 好久不见
         * singer : 陈奕迅
         * album : 认了吧
         * genre : 校园
         */

        private String song;
        private String singer;
        private String album;
        private String genre;

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
