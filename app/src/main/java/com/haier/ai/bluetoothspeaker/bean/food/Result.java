
package com.haier.ai.bluetoothspeaker.bean.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result {

    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("intent")
    @Expose
    private String intent;
    @SerializedName("paras")
    @Expose
    private List<Para> paras = new ArrayList<Para>();
    @SerializedName("response")
    @Expose
    private String response;

    /**
     * 
     * @return
     *     The domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 
     * @param domain
     *     The domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 
     * @return
     *     The intent
     */
    public String getIntent() {
        return intent;
    }

    /**
     * 
     * @param intent
     *     The intent
     */
    public void setIntent(String intent) {
        this.intent = intent;
    }

    /**
     * 
     * @return
     *     The paras
     */
    public List<Para> getParas() {
        return paras;
    }

    /**
     * 
     * @param paras
     *     The paras
     */
    public void setParas(List<Para> paras) {
        this.paras = paras;
    }

    /**
     * 
     * @return
     *     The response
     */
    public String getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(String response) {
        this.response = response;
    }

}
