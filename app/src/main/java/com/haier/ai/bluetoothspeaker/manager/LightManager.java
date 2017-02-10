package com.haier.ai.bluetoothspeaker.manager;

/**
 * author: qu
 * date: 16-11-9
 * introduce:灯光控制
 */

public class LightManager {
    private final String TAG = "LightManager";
    public static LightManager sLightManager;
    private final int LIGHT_STATE_NET_CONFIG = 1;
    private final int LIGHT_STATE_NET_DISCONNECT = 2;
    private final int LIGHT_STATE_NORMAL = 3;
    private final int LIGHT_STATE_WAKEUP = 4;
    private final int LIGHT_STATE_RECOGNIZE = 5;

    static {
        System.loadLibrary("native-lib");
    }

    /**
     *
     * @param cmd
     *  // 1,蓝色闪烁20hz 配对模式
    // 2,红色闪烁20hz 网络断开
    // 3,绿色常亮 运行状态
    // 4,白色常亮 语音待命
    // 5,白灯闪烁 2hz 语言命令执行


     * @return
     */
    public native String notifyLightCtrl(int cmd);
    public native int notifyLightInit();

    public LightManager(){
        notifyLightInit();
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
        notifyLightCtrl(LIGHT_STATE_NET_CONFIG);
    }

    /**
     * 网络断开
     */
    public void netDisconnect(){
        notifyLightCtrl(LIGHT_STATE_NET_DISCONNECT);
    }

    /**
     * 通常状态下的灯光 ：绿色常亮：运行状态
     */
    public void lightNormal(){
        notifyLightCtrl(LIGHT_STATE_NORMAL);
    }

    /**
     * 语音唤醒后灯光变换： 白色常亮：语音待命
     */
    public void lightWakeup(){
        notifyLightCtrl(LIGHT_STATE_WAKEUP);
    }

    /**
     * 语音识别状态下灯光：   白灯闪烁（240次每分钟）：语音命令执行
     */
    public void lightRecognize(){
        notifyLightCtrl(LIGHT_STATE_RECOGNIZE);
    }
}
