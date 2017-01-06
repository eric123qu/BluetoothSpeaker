package com.haier.ai.bluetoothspeaker.event;

/**
 * author: qu
 * date: 16-9-28
 * introduce: 识别状态事件
 */

public class ReconizeStatusEvent {
    public final String message;

    public ReconizeStatusEvent(String msg){
        this.message = msg;
    }
}
