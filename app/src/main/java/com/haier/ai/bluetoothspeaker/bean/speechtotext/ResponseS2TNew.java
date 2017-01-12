/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.haier.ai.bluetoothspeaker.bean.speechtotext;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joyson on 2016/7/28.
 */
public class ResponseS2TNew {

/*
    {"retCode":"00000","retInfo":"操作成功","sn":"20170112154918238000300353","data":{"asrResult":[{"recogniationText":"打开空调","confidence":0.99}]}}
*/

    @SerializedName("retCode")
    @Expose
    private String retCode;
    @SerializedName("retInfo")
    @Expose
    private String retInfo;
    @SerializedName("sn")
    @Expose
    private String sn;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("asrResult")
        @Expose
        private List<AsrResult> asrResult = null;

        public List<AsrResult> getAsrResult() {
            return asrResult;
        }

        public void setAsrResult(List<AsrResult> asrResult) {
            this.asrResult = asrResult;
        }

    }

    public static class AsrResult {

        @SerializedName("recogniationText")
        @Expose
        private String recogniationText;
        @SerializedName("confidence")
        @Expose
        private Double confidence;

        public String getRecogniationText() {
            return recogniationText;
        }

        public void setRecogniationText(String recogniationText) {
            this.recogniationText = recogniationText;
        }

        public Double getConfidence() {
            return confidence;
        }

        public void setConfidence(Double confidence) {
            this.confidence = confidence;
        }

    }
}
