package com.haier.ai.bluetoothspeaker.event;

/**
 * author: qu
 * date: 16-9-2
 * introduce:
 */
public class WakeupEvent {
    public final String message;

    public WakeupEvent(String message){
        this.message = message;
    }
}
