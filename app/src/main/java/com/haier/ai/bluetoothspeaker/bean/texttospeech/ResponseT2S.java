/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.haier.ai.bluetoothspeaker.bean.texttospeech;

/**
 * Created by Joyson on 2016/7/26.
 */
public class ResponseT2S {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * data : {"format":"riff-8khz-8bit-mono-mulaw","speech":"语音base64","length":"14897"}
     */

    private String retCode;
    private String retInfo;

    public ResponseT2S(String retCode, String retInfo, DataBean data) {
        this.retCode = retCode;
        this.retInfo = retInfo;
        this.data = data;
    }

    /**
     * format : riff-8khz-8bit-mono-mulaw
     * speech : 语音base64
     * length : 14897
     */


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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String format;
        private String speech;
        private String length;

        public DataBean(String format, String speech, String length) {
            this.format = format;
            this.speech = speech;
            this.length = length;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "format='" + format + '\'' +
                    ", speech='" + speech + '\'' +
                    ", length='" + length + '\'' +
                    '}';
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getSpeech() {
            return speech;
        }

        public void setSpeech(String speech) {
            this.speech = speech;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }
    }

    @Override
    public String toString() {
        return "ResponseT2S{" +
                "retCode='" + retCode + '\'' +
                ", retInfo='" + retInfo + '\'' +
                ", data=" + data +
                '}';
    }
}
