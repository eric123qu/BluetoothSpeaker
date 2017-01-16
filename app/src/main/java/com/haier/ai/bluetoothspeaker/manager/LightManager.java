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

    public boolean openLight(){
        return true;
    }

    public boolean closeLight(){
        return true;
    }

    /**
     * 设置亮度
     * @param level
     */
    public void setBrightness(int level){

    }

    /**
     * 设置色温
     * @param level
     */
    public void setColorTemperature(int level){

    }

    /**
     * 设置颜色
     * @param level
     */
    public void setColor(int level){

    }

    /**
     * 网络配网模式
     */
    public void netConfigMode(){

    }

    /**
     * 网络断开
     */
    public void netDisconnect(){

    }

    /**
     * 通常状态下的灯光 ：绿色常亮：运行状态
     */
    public void lightNormal(){

    }

    /**
     * 语音唤醒后灯光变换： 白色常亮：语音待命
     */
    public void lightWakeup(){

    }

    /**
     * 语音识别状态下灯光：   白灯闪烁（240次每分钟）：语音命令执行
     */
    public void lightRecognize(){

    }
}
