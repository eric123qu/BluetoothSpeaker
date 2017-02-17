package com.haier.ai.bluetoothspeaker.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;

import static com.baidu.speech.EventManagerFactory.TAG;

/**
 * author: qu
 * date: 16-9-5
 * introduce:
 */
public class WakeupEventManager {
    public static WakeupEventManager sWakeupEventManager;
    private static EventManager mWpEventManager;
    private static Context mContext;
    private static EventListener eventListener;

    public static WakeupEventManager getInstance(Context context){
        if(sWakeupEventManager == null){
            sWakeupEventManager = new WakeupEventManager(context);
        }

        return sWakeupEventManager;
    }

    public WakeupEventManager(Context context){
        mContext = context;
    }

    public EventManager getEventManager(){
        if(mWpEventManager == null){
            mWpEventManager = EventManagerFactory.create(mContext, "wp");
        }

        return mWpEventManager;
    }


    public void startWakeup(String param){
        Log.e(TAG, "startWakeup: ");
        if(TextUtils.isEmpty(param)){
            return;
        }

        if(mWpEventManager != null){
            mWpEventManager.send("wp.start", param, null, 0, 0);
        }
    }

    public void stopWakeup(){
        Log.e(TAG, "stopWakeup: ");
        if(mWpEventManager != null) {
            mWpEventManager.send("wp.stop", null, null, 0, 0);
        }
    }

    public void registerEventListener(EventListener eventListener){
        Log.e(TAG, "registerEventListener: ");
        this.eventListener = eventListener;
        if(eventListener != null) {
            getEventManager().registerListener(eventListener);
        }
    }

    public void unregisterEventListener(){
        Log.e(TAG, "unregisterEventListener: ");
        if(eventListener != null){
            getEventManager().unregisterListener(eventListener);
        }
    }
}
