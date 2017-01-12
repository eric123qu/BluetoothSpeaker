/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.haier.ai.bluetoothspeaker.bean.texttospeech;

/**
 * Created by Joyson on 2016/7/26.
 */
public class RequestT2S {

    /**
     * text : 今天中午有雨，请出门时带上雨伞
     * outformat : riff-8khz-8bit-mono-mulaw
     * outlang : zh-CN
     * outgender : Female
     * outname : Yaoyao
     * voicename : MicrosoftServerSpeechTexttoSpeechVoice(zh-CN,Yaoyao,Apollo)
     */

    private String text;
    private String outformat;
    private String outlang;
    private String outgender;
    private String outname;

    public RequestT2S(String text, String outformat, String outlang, String outgender, String outname) {
        this.text = text;
        this.outformat = outformat;
        this.outlang = outlang;
        this.outgender = outgender;
        this.outname = outname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOutformat() {
        return outformat;
    }

    public void setOutformat(String outformat) {
        this.outformat = outformat;
    }

    public String getOutlang() {
        return outlang;
    }

    public void setOutlang(String outlang) {
        this.outlang = outlang;
    }

    public String getOutgender() {
        return outgender;
    }

    public void setOutgender(String outgender) {
        this.outgender = outgender;
    }

    public String getOutname() {
        return outname;
    }

    public void setOutname(String outname) {
        this.outname = outname;
    }

}
