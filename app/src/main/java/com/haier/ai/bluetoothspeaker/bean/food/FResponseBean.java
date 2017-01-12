
package com.haier.ai.bluetoothspeaker.bean.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FResponseBean {

    @SerializedName("dialog")
    @Expose
    private Boolean dialog;
    @SerializedName("parsed_text")
    @Expose
    private String parsedText;
    @SerializedName("raw_text")
    @Expose
    private String rawText;
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();

    /**
     * 
     * @return
     *     The dialog
     */
    public Boolean getDialog() {
        return dialog;
    }

    /**
     * 
     * @param dialog
     *     The dialog
     */
    public void setDialog(Boolean dialog) {
        this.dialog = dialog;
    }

    /**
     * 
     * @return
     *     The parsedText
     */
    public String getParsedText() {
        return parsedText;
    }

    /**
     * 
     * @param parsedText
     *     The parsed_text
     */
    public void setParsedText(String parsedText) {
        this.parsedText = parsedText;
    }

    /**
     * 
     * @return
     *     The rawText
     */
    public String getRawText() {
        return rawText;
    }

    /**
     * 
     * @param rawText
     *     The raw_text
     */
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * 
     * @param results
     *     The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

}
