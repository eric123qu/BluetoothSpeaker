package com.haier.ai.bluetoothspeaker.event;

/**
 * author: qu
 * date: 16-9-5
 * introduce:
 */
public class StartRecordEvent {
    //private String message;
    private boolean reReconize;

    public StartRecordEvent(boolean bRereconize){
        this.reReconize = bRereconize;
    }

    public void setReReconize(boolean reReconize){
        this.reReconize = reReconize;
    }

    public boolean getReReconize(){
        return this.reReconize;
    }
}
