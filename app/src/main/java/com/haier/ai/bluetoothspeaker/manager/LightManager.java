package com.haier.ai.bluetoothspeaker.manager;

/**
 * author: qu
 * date: 16-11-9
 * introduce:灯光控制
 */

public class LightManager {
    private final String TAG = "LightManager";
    public static LightManager sLightManager;

    public LightManager(){

    }

    public static LightManager getInstance(){
        if(sLightManager == null){
            sLightManager = new LightManager();
        }

        return sLightManager;
    }


}
