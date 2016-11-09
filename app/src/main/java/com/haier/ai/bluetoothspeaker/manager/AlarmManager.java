package com.haier.ai.bluetoothspeaker.manager;

/**
 * author: qu
 * date: 16-11-9
 * introduce:
 */

public class AlarmManager {
    private final String TAG = "AlarmManager";
    public static AlarmManager sAlarmManager;


    public AlarmManager(){

    }

    public static AlarmManager getInstance(){
        if(sAlarmManager == null){
            sAlarmManager = new AlarmManager();
        }

        return sAlarmManager;
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
