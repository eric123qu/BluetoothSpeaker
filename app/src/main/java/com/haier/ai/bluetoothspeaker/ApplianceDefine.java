package com.haier.ai.bluetoothspeaker;

/**
 * author: qu
 * date: 15-9-15
 * introduce:
 */
public class ApplianceDefine {
    /**
     * 命令类型
     */
    public static final byte ORDER_QUERY = 0x01;                 //下发查询指令
    public static final byte ORDER_CONTROL = 0x02;               //下发控制指令
    public static final byte ORDER_REPORT_STATE = 0x03;          //上报数据结果
    public static final byte ORDER_UGW = 0x04;                   //下发ugw设备入网指令
    public static final byte ORDER_DEVLIST = 0x05;               //设备列表管理
    public static final byte ORDER_IFTTT = 0x06;                 //Ifttt 控制
    public static final byte ORDER_ZIGBEE = 0x07;                //10W zigbee配网指令
    public static final byte ORDER_TEM_ELECT = 0x08;             //上报温湿度，剩余电量
    public static final byte ORDER_INFRARED_ALARM = 0x09;        //红外报警
    public static final byte ORDER_ERROR_DATA = (byte)0xF1;      //上报错误数据，下发的帧数据错误，其他数据原数据返回
    public static final byte ORDER_NOT_REDAY = (byte)0xF2;       //数据下发，但是底层设备没有准备好
    public static final byte ORDER_NO_DEVICE = (byte)0xF3;       //上报没有此设备

    /**
     * 设备类型
     */
    public static final byte DEV_UNKNOW = 0x00;
    public static final byte DEV_WIFI = 0x01;
    //public static final byte DEV_60 = 0x02;
    public static final byte DEV_SMARTCARE = 0x03;
    public static final byte DEV_INFRARED = 0x04;               //红外报警
    public static final byte DEV_SPEAKER = 0x02;                //音箱
    public static final byte DEV_BOX = 0x18;                //音箱音乐

    /**
     * 设备种类
     */
    public static final byte TYPE_DOOR = 0x01;
    public static final byte TYPE_WATER = 0x02;
    public static final byte TYPE_AIRCONDITIONER= 0x10;        //空调
    public static final byte TYPE_AIR_CLEANER = 0x11;          //空气净化器
    public static final byte TYPE_WATER_HEATER = 0x12;         //热水器
    public static final byte TYPE_WASHER = 0x13;               //洗衣机
    public static final byte TYPE_FRIDGE = 0x14;               //冰箱
    public static final byte TYPE_HOODS = 0x15;                //油烟机
    public static final byte TYPE_SWEEPING = 0x16;             //扫地机器人
    public static final byte TYPE_SPEAKER_LIGHT = 0x17;        //音箱灯光
    public static final byte TYPE_SPEAKER_MUSIC = 0x18;                //音箱音乐
    public static final byte TYPE_60_AC = 0x20;                //60 空调
    public static final byte TYPE_LIGHT = 0x21;                //灯光
    public static final byte TYPE_CURTAIN = 0x22;              //窗帘
    public static final byte TYPE_SCENE = 0x23;                //场景
    public static final byte TYPE_HEATER = 0x24;               //地暖
    public static final byte TYPE_CENTRALVENTILATION = 0x25;   //新风
    public static final byte TYPE_MUSIC = 0x26;                //背景音乐
    public static final byte TYPE_RISCO = 0x27;                //RISCO 安防
    public static final byte TYPE_INFRARED = 0x30;             //10W红外报警设备



    /**
     * 设备位置
     */
    public static final byte LOCATION_UNKONW = 0x00;
    public static final byte LOCATION_LIVINGROOM = 0x01;
    public static final byte LOCATION_BEDROOM = 0x02;
    public static final byte LOCATION_KITCHEN = 0x03;
    public static final byte LOCATION_ALL = (byte)0xFF;

    /**
     * 设备序号
     */
    public static final byte SEQUENCE_UNKONW = 0x00;
    public static final byte SEQUENCE_ALL = (byte)0xFF;

    /**
     * 设备属性（按照网关头文件）
     */
    public static final byte PROPERTY_SWITCH = 0x01;
    public static final byte PROPERTY_TEMPERATURE = 0x02;

    //空调 (0x10)
    public static final byte AIRCON_DEV = (byte) 0x02;
    public static final byte AIRCON_indoorTemp = (byte)0x01;        //查询上报室内温度：temp
    public static final byte AIRCON_indoorHumi = (byte)0x02;        //查询上报室内湿度：humi
    public static final byte AIRCON_status = (byte)0x03;            //空调 关闭、打开： 0--off, 1--on
    public static final byte AIRCON_operation = (byte)0x04;         //空调模式，自动、制冷、送风、除湿： 1--auto, 2--cool, 3--heat, 4--wind, 5--humi
    public static final byte AIRCON_targetTemp = (byte)0x05;        //设置当前温度：temp val(16-30)
    public static final byte AIRCON_outdoorTemp = (byte)0x06;       //查询上报室外温度：temp
    public static final byte AIRCON_windSpeed = (byte)0x07;         //风速控制，低、中、高、自动： 1--low, 2--med, 3--high, 4--auto
    public static final byte AIRCON_windDVertical = (byte)0x08;     //上下摆风，固定、摆动： 1--stationary, 2--swing
    public static final byte AIRCON_windDHorizontal = (byte)0x09;   //左右摆风，固定、摆动： 1--stationary, 2--swing
    public static final byte AIRCON_airQuality = (byte)0x0A;        // 查询上报空气质量 优、良、中、差：--perfect, 2--better, 3--normal, 4--bad
    public static final byte AIRCON_electricHeating = (byte)0x0B;   // 电加热功能：0--off, 1--on
    public static final byte AIRCON_freshAir = (byte)0x0C;          // 新风功能：0--off, 1--on
    public static final byte AIRCON_anion = (byte)0x0D;             // 负离子功能：0--off, 1--on
    public static final byte AIRCON_eLock = (byte)0x0E;             // 电子锁：0--off, 1--on
    public static final byte AIRCON_selfCleaning = (byte)0x0F;      //自清洁： 0--off, 1--on
    public static final byte AIRCON_cloudConrtol = (byte)0x10;      // 云适应：0--off, 1--on
    public static final byte AIRCON_setSleepCurve = (byte)0x11;     // 睡眠曲线：0--off, 1--on
    public static final byte AIRCON_human_Sensing = (byte)0x12;     //感人模式： 0--off, 1--on
    public static final byte AIRCON_pm25Val = (byte)0x13;           //查询上报：PM25
    public static final byte AIRCON_alarmCancel = (byte)0x14;       //告警解除
    public static final byte AIRCON_humiditySensorErr = (byte)0x15; //湿度传感器故障
    public static final byte AIRCON_tempSensorErr = (byte)0x16;     //室内温度传感器故障
    public static final byte AIRCON_powerProtection = (byte)0x17;   //电源超、欠压保护
    public static final byte AIRCON_tempAutoControl = (byte)0x19;   //自动控制温度（附加，温度高点低点）0：-Temp, 1: +Temp
    public static final byte AIRCON_humiAutoControl = (byte)0x1B;   //自动控制风速（附加，风速高点低点） 0: -speed, 1: +speed

    public static final int AIRCON_MODE_AUTO = 1;
    public static final int AIRCON_MODE_COOL = 2;
    public static final int AIRCON_MODE_HEAT = 3;
    public static final int AIRCON_MODE_WIND = 4;
    public static final int AIRCON_MODE_HUMI = 5;
    public static final int AIRCON_WIND_LOW = 1;
    public static final int AIRCON_WIND_MED = 2;
    public static final int AIRCON_WIND_HIGH = 3;
    public static final int AIRCON_WIND_AUTO = 4;


    //空气净化器(0x11)
    public static final byte AIRCLEANER_DEV = (byte)0x03;           //空气净化器
    public static final byte AIRCLEANER_indoorTemp = (byte)0x01;    //上报室内温度
    public static final byte AIRCLEANER_indoorHumi = (byte)0x02;    //上报室内湿度
    public static final byte AIRCLEANER_onoffstatus = (byte)0x03;   //开关机状态 0--off, 1--on
    public static final byte AIRCLEANER_operationMode = (byte)0x04; //功能模式 1--auto, 6--handset, 7--sleep, 8--disinfect杀菌
    public static final byte AIRCLEANER_windDirection = (byte)0x05; //导板（气流） 1--normal, 2--swing, 3--random
    public static final byte AIRCLEANER_windSpeed = (byte)0x06;     //风速 1--low, 2--med, 3--high, 4--auto, 5--mute
    public static final byte AIRCLEANER_airQuality = (byte)0x07;    //空气质量 1--perfect, 2--better, 3--normal, 4--bad, 5--very bad, 6--serious
    public static final byte AIRCLEANER_filterReplaceAlarmExist = (byte)0x08;    //滤网更换提醒有无 0--no, 1--have
    public static final byte AIRCLEANER_pm25Val = (byte)0x09;       //室内PM2.5值
    public static final byte AIRCLEANER_ch2oValue = (byte)0x0A;     //室内甲醛值
    public static final byte AIRCLEANER_vocValue = (byte)0x0B;      //室内VOC值
    public static final byte AIRCLEANER_co2Value = (byte)0x0C;      //室内CO2值
    public static final byte AIRCLEANER_alarmCancel = (byte)0x0D;   //告警解除
    public static final byte AIRCLEANER_humiditySensorErr = (byte)0x0E;    //湿度传感器故障
    public static final byte AIRCLEANER_tempSensorErr = (byte)0x0F; //温度传感器故障
    public static final byte AIRCLEANER_airQualitySensorErr = (byte)0x10;      //空气质量传感器故障
    public static final byte AIRCLEANER_vocSensorErr = (byte)0x11;  //VOC传感器故障
    public static final byte AIRCLEANER_pm2p5SensorErr = (byte)0x12;      //PM2.5传感器故障
    public static final byte AIRCLEANER_indoorFanErr = (byte)0x13;      //内风机故障

    // 热水器(0x12)
    public static final byte HEATER_DEV = (byte)0x04;                       //热水器
    public static final byte HEATER_onOffStatus = (byte)0x01;               //开关机状态 0--off, 1--on
    public static final byte HEATER_time = (byte)0x02;                      //当前时间
    public static final byte HEATER_targetTemp = (byte)0x03;                //目标水温
    public static final byte HEATER_currentTemp = (byte)0x04;               //当前水温
    public static final byte HEATER_volume = (byte)0x05;                    //容积
    public static final byte HEATER_heatingStatus = (byte)0x06;             //保温/加热功能状态
    public static final byte HEATER_outTemp = (byte)0x07;                   //出水温度
    public static final byte HEATER_profiles = (byte)0x08;                  //情景模式  0 none 1 life mode 2 shower mode 3 bathtub mode
    public static final byte HEATER_resnStatus = (byte)0x09;                //预约功能开关
    public static final byte HEATER_resnMode = (byte)0x0A;                  //预约模式
    public static final byte HEATER_resn1Time = (byte)0x0B;                 //预约1时间
    public static final byte HEATER_resn1Temperature = (byte)0x0C;                 //预约2时间
    public static final byte HEATER_resn2Time = (byte)0x0D;
    public static final byte HEATER_resn2Temperature = (byte)0x0E;
    public static final byte HEATER_alarmCancel = (byte)0x0F;               //告警解除
    public static final byte HEATER_dryHeatingAlarm = (byte)0x10;           //干烧超温报警
    public static final byte HEATER_leakageAlarm = (byte)0x11;              //漏电报警
    public static final byte HEATER_powerCommunicationErr = (byte)0x12;     //电源板通讯故障
    public static final byte HEATER_tVCommunicationErr = (byte)0x13;        //恒温阀通讯故障
    public static final byte HEATER_setTemperatureErr = (byte)0x14;         //设置温度异常
    public static final byte HEATER_middleTempSensorErr = (byte)0x15;       //水箱温度中传感器故障报警
    public static final byte HEATER_upTempSensorErr = (byte)0x16;           //水箱温度上故障报警
    public static final byte HEATER_downTempSensorErr = (byte)0x17;         //水箱温度下故障报警
    public static final byte HEATER_middleUpTempSensorErr = (byte)0x18;     //水箱温度中上故障报警
    public static final byte HEATER_middleDownTempSensorErr = (byte)0x19;   //水箱温度中下故障报警
    public static final byte HEATER_relayErr = (byte)0x1A;                  //电源板继电器故障
    public static final byte HEATER_inTempSensorErr = (byte)0x1B;           //进水温度传感器故障
    public static final byte HEATER_tVColdWaterSensorErr = (byte)0x1C;      //恒温阀冷水传感器故障
    public static final byte HEATER_tVHotWaterSensorErr = (byte)0x1D;       //恒温阀热水传感器故障
    public static final byte HEATER_stepMotorErr = (byte)0x1E;              //步进电机故障
    public static final byte HEATER_tankMaintenanceErr = (byte)0x1F;        //内胆保养故障
    public static final byte HEATER_heaterMaintenanceErr = (byte)0x20;      //加热体保养故障

    //0x13 洗衣机
    public static final byte WASHING_dev = 0x05;                            //洗衣机
    public static final byte WASHING_onOffStatus = (byte)0x01;              //开关机状态 0--off, 1--on
    public static final byte WASHING_runningMode = (byte)0x02;              //运行状态 0--off, 1--on
    public static final byte WASHING_childLockStatus = (byte)0x03;          //童锁状态 0--off, 1--on
    public static final byte WASHING_laundryCycle = (byte)0x04;             //洗衣程序
    public static final byte WASHING_dryMode = (byte)0x05;                  //烘干程序
    public static final byte WASHING_dirtyLevel = (byte)0x06;               //脏污度
    public static final byte WASHING_stainType = (byte)0x07;                //特殊污渍种类
    public static final byte WASHING_spinSpeed = (byte)0x08;                //脱水转速
    public static final byte WASHING_spinTime = (byte)0x09;                 //脱水时间
    public static final byte WASHING_rinseTimes = (byte)0x0A;               //漂洗次数
    public static final byte WASHING_washWaterLevel = (byte)0x0B;           //洗涤水位
    public static final byte WASHING_WashTime = (byte)0x0C;                 //主洗时间
    public static final byte WASHING_autoDetergentStatus = (byte)0x0D;      //洗涤剂自动投放功能状态
    public static final byte WASHING_autoSoftenerStatus = (byte)0x0E;       //柔顺剂自动投放功能状态
    public static final byte WASHING_autoDisinfectantStatus = (byte)0x0F;   //消毒剂自动投放功能状态
    public static final byte WASHING_resnStatus = (byte)0x10;               //预约功能状态
    public static final byte WASHING_finishReminderStatus = (byte)0x11;     //洗完1小时提醒功能
    public static final byte WASHING_finishShutdownStatus = (byte)0x12;     //洗衣程序运行完取消操作(解锁并关机)
    public static final byte WASHING_purifiedWashStatus = (byte)0x13;       //净水洗功能状态
    public static final byte WASHING_echoStatus = (byte)0x14;               //蜂鸣音功能状态
    public static final byte WASHING_voiceStatus = (byte)0x15;              //语音功能状态
    public static final byte WASHING_powerfulStatus = (byte)0x16;           //强力去污功能状态
    public static final byte WASHING_anionStatus = (byte)0x17;              //负离子功能状态
    public static final byte WASHING_actualClothingWeight = (byte)0x18;     //衣物实测重量
    public static final byte WASHING_remainingHour = (byte)0x19;            //当前剩余时间小时数
    public static final byte WASHING_remainingMinute = (byte)0x1A;          //当前剩余时间分钟数
    public static final byte WASHING_totalWashCycle = (byte)0x1B;           //累计洗涤周期
    public static final byte WASHING_currentWashCycle = (byte)0x1C;         //周期内累计洗涤次数
    public static final byte WASHING_waterUsed = (byte)0x1D;                //本次用水量
    public static final byte WASHING_totalWaterUsed = (byte)0x1E;           //累计用水量
    public static final byte WASHING_cyclePhase = (byte)0x1F;               //洗衣程序运行阶段
    public static final byte WASHING_doorStatus = (byte)0x20;               //门盖/筒门开关
    public static final byte WASHING_doorLockStatus = (byte)0x21;           //门盖锁状态
    public static final byte WASHING_laundryCycleStatus = (byte)0x22;       //程序运行状态
    public static final byte WASHING_remoteControlStatus = (byte)0x23;      //远程控制功能状态
    public static final byte WASHING_hintMsg = (byte)0x24;                  //提示信息
    //洗衣程序
    public static final byte MODE_COTTON = (byte)0x04;
    public static final byte MODE_CHEMICAL_FIBER = (byte)0x05;
    public static final byte MODE_DOWN = (byte)0x07;
    public static final byte MODE_CURTAIN = (byte)0x23;
    public static final byte MODE_MIX = (byte)0x11;
    public static final byte MODE_SHIRT = (byte)0x16;
    public static final byte MODE_CHILDREN_CLOTHES = (byte)0x17;
    public static final byte MODE_SUPER_SOFT = (byte)0x0A;
    public static final byte MODE_HOT = (byte)0x21;
    public static final byte MODE_OUTDOOR = (byte)0x15;
    public static final byte MODE_HAND = (byte)0x18;
    public static final byte MODE_DRY_STRONG = (byte)0x19;
    public static final byte MODE_DRY_WEAK = (byte)0x20;

    /**
     * 0x16 扫地机器人
     */
    public static final byte SWEEPING_dev = 0x08;                           //扫地机器人
    public static final byte MODE_WORK_STATE = 0x01;                        //工作状态 1 开始清扫 2 停止清扫 3 回冲
    public static final byte MODE_CLEAN = 0x02;                             //清扫模式 1 随机清扫 2沿边清扫 3螺旋 4 规划
    public static final byte MODE_FAN_STATE = 0x03;                         //强力度 1 正常 2 强力 3 停止

    /**
     * 0x17 音箱灯光
     */
    public static final byte SPEAKER_LIGHT_dev = 0x09;
    public static final byte MODE_ONOFF_STATUS = 0x01;                      //开关机状态
    public static final byte MODE_LED_BRIGHTNESS = 0x02;                    //灯光亮度 0-100
    public static final byte MODE_LED_COLOR = 0x03;                         //灯光颜色 1红2橙3黄4绿5青6蓝7紫
    public static final byte MODE_LED_MODE = 0x04;                          //演示版固定四种颜色模式（1标准，2阅读，3浪漫，4睡眠）


    /**
     * 0x18 音箱音乐
     */
    public static final byte SPEAKER_MUSIC_dev = 0x0a;
    public static final byte MODE_PLAY_MODE = 0x01;                         //音乐播放状态 0 暂停 1播放
    public static final byte MODE_LOOP_STATUS = 0x02;                       // 循环状态 1 单曲 2列表 3 随机播放
    public static final byte MODE_VOLUME = 0x03;                            //音量 0减  1加
    public static final byte MODE_MUTE_STATUS = 0x04;                       //静音状态 0 非静音 1 静音
    public static final byte MODE_PLAY_SONG = 0x05;                         //昵称填写歌名
    public static final byte MODE_PLAY_CONTROL = 0x06;                      //播放控制  1 上一首 2下一首


    /**
     * HK60(0x02)
     */
    public static final byte HK60_DEV = (byte)0x01;
    //空调（0x20)
    public static final byte HK_AIR_STATUS = (byte)0x37;                    //控制空调状态
    //灯光（0x21）
    public static final byte HK_ALLOPEN = (byte)0x05;                       //全开指令 灯 0x32, 窗帘 0x33
    public static final byte HK_ALLCLOSE = (byte)0x06;                      //全关指令 灯 0x32, 窗帘 0x33
    public static final byte HK_OPERATOR = (byte)0x07;                      //指定灯光  byte1: 0x00-off, 0x01- on;0x02-level ctrl,
                                                                            //         byte2: 0x02-0x08--level(1-7) ---if byte1=0x02, need byte2 level
    public static final byte HK_COMMON_ON = (byte)0x01;
    public static final byte HK_COMMON_OFF = (byte)0x00;
    //窗帘(0x22) 同灯光
    //场景(0x23)
    public static final byte HK_SESSION_CONTROL = (byte)0x08;               //
    //地暖(0x24) 地暖
    /*
    byte1 0x01: onoffstate; byte2   0--off, 1--on,
            0x04:       temp;         1-30
            0x10:       lock;         0--unlock, 1--lock
    */
    public static final byte HK_WARM_STATUS = (byte)0x3C;                   //控制地暖状态
    public static final byte HK_HEATER_ONOFFSTATUS = (byte)0x01;            //0--off, 1--on,
    public static final byte HK_HEATER_TEMP = (byte)0x04;                   //1-30
    public static final byte HK_HEATER_LOCK = (byte)0x10;                   //0--unlock, 1--lock

    //新风(0x25)
    /*
      byte1 0x01: onoffstate; byte2   0--off, 1--on,
            0x02:  operation;         1--auto, 1--hand,
            0x04:  windspeed;         1--high, 2--med, 3--low,
            0x08: warm air quality;   hight--low:  0-5
            0x10:  hot swap ctrl;     0--off, 1--on,
            0x20:       lock;         0--unlock, 1--lock
            */
    public static final byte HK_NEW_WIND = (byte)0x40;                      //控制新风状态
    public static final byte HK_FAS_ONOFFSTATUS = (byte)0x01;               //0--off, 1--on
    public static final byte HK_FAS_OPERATION = (byte)0x02;                 //1--auto, 1--hand
    public static final byte HK_FAS_WINDSPEED = (byte)0x04;                 //1--high, 2--med, 3--low
    public static final byte HK_FAS_WARM_AIR_QUALITY = (byte)0x08;          //hight--low:  0-5
    public static final byte HK_FAS_HOT_SWAP_CTRL = (byte)0x10;             //0--off, 1--on
    public static final byte HK_FAS_LOCK = (byte)0x20;                      //0--unlock, 1--lock
    //背景音乐(0x26)
   /*
   byte1 0x01:   onoffstate;  byte2  0--off, 1--on,
            0x02:  song source;         0:MP3 1:DVD 2:AUX 3:USB 4:FM1 5:FM2
    0x03:    above one:
            0x04:       volume;         1~31
            0x05:     next one;
    0x08:  single song;        0--order ,1--cycle
    0x10:  choice song;        0--off, 1--on,
            0x20:         mute;        0--unmute, 1--mute
    0x40:   phone mute;        0--unmute, 1--mute
    0x80:         play;        0--stop, 1--play
    */
    public static final byte HK_BACKGROUND_MUSIC = (byte)0x44;              //控制背景音乐
    public static final byte HK_MUSIC_ONOFFSTATUS = (byte)0x01;             //0--off, 1--on,
    public static final byte HK_MUSIC_SONGSOURCE = (byte)0x02;              //0:MP3 1:DVD 2:AUX 3:USB 4:FM1 5:FM2
    public static final byte HK_MUSIC_VOLUME = (byte)0x04;                  //1~31
    public static final byte HK_MUSIC_NEXT = (byte)0x05;
    public static final byte HK_MUSIC_PLAY = (byte)0x80;                    //0--stop, 1--play


    //Risco安防(0x27)
    public static final byte RISCO_BASE = 0x01;
    public static final byte HK_RISCO_LINE_CONFIG = (byte)0x65;             //配置Risco入网
    public static final byte HK_RISCO_LINE_SUCESS = (byte)0x66;             //Risco入网成功
    public static final byte HK_RISCO_QUERY_AF_STATUS = (byte)0x67;         //查询安防转接模块状态
    public static final byte HK_RISCO_RETURN_AF_STATUS = (byte)0x68;        //安防转接模块状态返回 0x0000--撤防，0x0001--在家布防，0x0002--外出布防
    public static final byte HK_RISCO_CONTROL_AF = (byte)0x69;              //控制安防转接模块
    public static final byte HK_RISCO_REPORT_AF_STATUS = (byte)0x6A;        //安防转接模块报警状态主动汇报

    public static final byte HK_RISCO_CANCEL_SECURITY = (byte)0x00;         //撤防
    public static final byte HK_RISCO_HOME_SECURITY = (byte)0x01;           //在家布防
    public static final byte HK_RISCO_OUT_SECURITY = (byte)0x02;            //外出布防

    //dance(0x2A)
    public static final byte DANCE_DEV = (byte)0x2A;
    public static final byte DANCE_BASE = (byte)0x01;
    public static final byte DANCE_SYNC = (byte)0x79;

    //风
    public static final byte DEV_AIRCON_AutoCtrl_Wind = 0x1B;       //  0: -speed, 1: +speed
    //温度
    public static final byte DEV_AIRCON_AutoCtrl_Temp = 0x19;       // 0:-Temp, 1: +Temp

    // HK60 0x0100--0x01FF
    public static final byte CMD_LMP = (byte)0x01;

    /**
     * 灯\窗帘			// status val
     */

    //设备属性
    public static final byte CMD_LmpAllOn	 =0x05;                 // 灯窗帘全开，status val：0x32-灯全开，0x33-窗帘全开
    public static final byte CMD_LmpAllOff	 =0x06;                 // 灯窗帘全关，status val：0x32-灯全开，0x33-窗帘全开
    //属性状态
    public static final byte CMD_Lmp_Light = 0x32;
    public static final byte CMD_Lmp_Window = 0x33;

    /**
     * 场景
     */
    //设备属性
    public static final byte CMD_ScnBrd = 0x08;
    public static final byte CMD_Scene = 0x02;
    //属性状态
    public static final short STATUS_HOME = (short)38;
    public static final short STATUS_LEAVE_HOME = (short)39;
    public static final short STATUS_SCENE = (short)0;
    //
    /**
     * 通用
     */
    public static final byte GENERAL_UNKNOW = 0x00;
    public static final byte GENERAL_ALL = (byte)0xFF;

    /**
     *SS01 	0x0000--0x00FF		// status val
     */
    public static final byte SS01_BASE = 0x00;
    public static final byte DEV_ATTR_DOOR = 0x01;                  //门磁状态，下发：无参数，上报：0x0000--远离，x0001--靠近
    public static final byte DEV_ATTR_WATER = 0x02;                 //水浸状态，下发：无参数，上报：0x0000--无水?x0001--有水
    public static final byte DEV_ATTR_BATTERY_V = 0x08;             ////电池电压，下发：无参数，上报：电池电压
    public static final byte DEV_ATTR_GETVERSION = (byte)0xf0;      //版本号，下发：无参数，上报：版本号10byte
    public static final byte DEV_ATTR_RESET = (byte)0xf1;           //设备重启，上报、下发均无参数;
    public static final byte DEV_ATTR_NETWORKING = (byte)0xf2;      //0x0000 --退出组网

    public static final byte REPORT_TEM = (byte)0x01;               //温湿度
    public static final byte REPORT_ELECT = (byte)0x02;             //电量
}
