package com.haier.ai.bluetoothspeaker.bean.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author: qu
 * date: 16-11-10
 * introduce:
 */

public class ResponseBean {
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
    private FResponseBean data;

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

    public FResponseBean getData() {
        return data;
    }

    public void setData(FResponseBean data) {
        this.data = data;
    }
}
