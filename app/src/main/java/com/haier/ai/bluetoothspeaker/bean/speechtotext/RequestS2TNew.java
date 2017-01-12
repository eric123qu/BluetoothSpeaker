/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.haier.ai.bluetoothspeaker.bean.speechtotext;

/**
 * Created by Joyson on 2016/7/28.
 */
public class RequestS2TNew {
    /**
     * speechLang : en-US
     * speechFormat : wav
     * speechRate : 8000
     * speech : XXXXXXXX
     * rawLen : 3096
     * isLuis : 0
     * luisModel :
     */

    private String speechLang;
    private String speechFormat;
    private int speechRate;
    private String speech;
    private int rawLen;
    private int isLuis;
    private String luisModel;

    public RequestS2TNew(String speechLang, String speechFormat, int speechRate, String speech, int rawLen, int isLuis, String luisModel) {
        this.speechLang = speechLang;
        this.speechFormat = speechFormat;
        this.speechRate = speechRate;
        this.speech = speech;
        this.rawLen = rawLen;
        this.isLuis = isLuis;
        this.luisModel = luisModel;
    }

    public String getSpeechLang() {
        return speechLang;
    }

    public void setSpeechLang(String speechLang) {
        this.speechLang = speechLang;
    }

    public String getSpeechFormat() {
        return speechFormat;
    }

    public void setSpeechFormat(String speechFormat) {
        this.speechFormat = speechFormat;
    }

    public int getSpeechRate() {
        return speechRate;
    }

    public void setSpeechRate(int speechRate) {
        this.speechRate = speechRate;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public int getRawLen() {
        return rawLen;
    }

    public void setRawLen(int rawLen) {
        this.rawLen = rawLen;
    }

    public int getIsLuis() {
        return isLuis;
    }

    public void setIsLuis(int isLuis) {
        this.isLuis = isLuis;
    }

    public String getLuisModel() {
        return luisModel;
    }

    public void setLuisModel(String luisModel) {
        this.luisModel = luisModel;
    }
}
