package com.haier.ai.bluetoothspeaker;

/**
 * Created by qx on 17-2-22.
 */

public class DeviceConst {
    /**
     * 载体灯光模式
     */
    public static final short LIGHT_MODE_STANDARD = 1;  //标准

    public static final short LIGHT_MODE_READ = 2;  //阅读

    public static final short LIGHT_MODE_ROMANTIC = 3;  //浪漫

    public static final short LIGHT_MODE_SLEEP = 4;  //睡眠

    public static short CURRENT_LIGHT_MODE;         //当前灯光模式状态

    /**
     * 声音相关
     */
    public static int MAX_VOICE;                    //系统最大音量

    public static int CURRENT_VOICE_LEVEL;          //当前音量大小

    /**
     * 灯光开关状态
     */
    public static int LIGHT_STATUS;

    public static final int LIGHT_STATUS_CLOSE = 0;

    public static final int LIGHT_STATUS_OPEN = 1;

    /**
     * 网络状态 0：断开  1：连接
     */
    public static final int NET_STATUS_ON = 1;

    public static final int NET_STATUS_OFF = 0;

    public static int DEVICE_NET_STATUS = NET_STATUS_OFF;

    /**
     * 音乐状态
     */
    public static int MUSIC_STATE = Const.STATE_STOP;       //音乐播放状态

}
