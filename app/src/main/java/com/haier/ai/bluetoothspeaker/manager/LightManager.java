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

    public static boolean openLight(){
        return true;
    }

    public static boolean closeLight(){
        return true;
    }

    /**
     * 设置亮度
     * @param level
     */
    public static void setBrightness(int level){

    }

    /**
     * 设置色温
     * @param level
     */
    public static void setColorTemperature(int level){

    }

    /**
     * 设置颜色
     * @param level
     */
    public static void setColor(int level){

    }
}
