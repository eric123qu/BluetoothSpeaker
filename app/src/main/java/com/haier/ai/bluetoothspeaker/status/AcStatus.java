package com.haier.ai.bluetoothspeaker.status;

/**
 * Created by qx on 17-3-1.
 */


/**
 * 空调状态类
 */
public class AcStatus {
    public static int ON_OFF_STATUS;        //开关机状态

    public static int CURRENT_TEMP;         //当前温度

    public static int CURRENT_HUMI;         //当前湿度

    public static int WIND_SPEED;           //风速

    public static int OPERATOR_MODE;        //模式


    public static final int STATUS_OFF = 0;

    public static final int STATUS_ON = 1;

    public static final int MODE_AUTO = 1;  //自动

    public static final int MODE_COOL = 2;  //制冷

    public static final int MODE_HEAT = 3;  //制热

    public static final int MODE_WIND = 4;  //送风

    public static final int MODE_HUMI = 5;  //除湿

    public static final int SPEED_LOW = 1;   //低风

    public static final int SPEED_MID = 2;   //中风

    public static final int SPEED_HIGH = 3;  //高风

    public static final int SPEED_AUTO = 4;  //自动
}
