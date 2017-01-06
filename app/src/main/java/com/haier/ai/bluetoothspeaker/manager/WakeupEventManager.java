package com.haier.ai.airobot.manager;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;

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
        if(TextUtils.isEmpty(param)){
            return;
        }

        if(mWpEventManager != null){
            mWpEventManager.send("wp.start", param, null, 0, 0);
        }
    }

    public void stopWakeup(){
        if(mWpEventManager != null) {
            mWpEventManager.send("wp.stop", null, null, 0, 0);
        }
    }

    public void registerEventListener(EventListener eventListener){
        this.eventListener = eventListener;
        if(eventListener != null) {
            getEventManager().registerListener(eventListener);
        }
    }

    public void unregisterEventListener(){
        if(eventListener != null){
            getEventManager().unregisterListener(eventListener);
        }
    }
}
