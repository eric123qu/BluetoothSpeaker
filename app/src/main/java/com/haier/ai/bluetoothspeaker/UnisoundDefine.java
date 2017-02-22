package com.haier.ai.bluetoothspeaker;

/**
 * author: qu
 * date: 15-8-17
 * introduce:云知声定义的语音识别控制类型
 */
public class UnisoundDefine {
    /**
     * operands 家电
     */
    //wifi
    public final static String OBJ_AC = "Air_conditioner";                           //空调
    public final static String OBJ_WIFI = "OBJ_WIFI";
    public final static String OBJ_AIRCLEANER = "OBJ_AIR_CLEANER";          //空气净化器
    public final static String OBJ_WATER_HEATER = "OBJ_WATER_HEATER";       //热水器
    public final static String OBJ_WASHER = "OBJ_WASHER";                   //洗衣机
    //60
    public final static String OBJ_FAS = "OBJ_FAS";                         //新风
    public final static String OBJ_LIGHT = "OBJ_LIGHT";                     //灯光
    public final static String OBJ_CURTAIN = "OBJ_CURTAIN";                 //窗帘
    public final static String OBJ_HEATER = "OBJ_HEATER";                   //地暖
    public final static String OBJ_OUTLET = "OBJ_OUTLET";                   //插座
    public final static String OBJ_MEDIA_PLAYER = "OBJ_MEDIA_PLAYER";       //背景音乐
    public final static String OBJ_TV = "OBJ_TV";                           //电视
    public final static String OBJ_CHANNEL = "OBJ_CHANNEL";                 //频道

    //public final static String ATTR_TEMPERATURE = "ATTR_TEMPERATURE";     //温度（增大，减少，设置，查询）
   // public final static String ATTR_MODE = "ATTR_MODE";                   //模式(设置，查询，取消设置)


    /**
     * operator 控制类型
     */
    //
    public final static String ACT_SET = "ACT_SET";
    public final static String ACT_OPEN = "打开";
    public final static String ACT_OPS = "ops";
    public final static String ACT_CLOSE = "关闭";
    public final static String ACT_PERCEIVE = "ACT_PERCEIVE";
    public final static String ACT_START = "ACT_START";
    public final static String ACT_PAUSE = "ACT_PAUSE";                      // 暂停
    public final static String ACT_UNSET = "ACT_UNSET";                      //取消设置
    public final static String ACT_DECREASE = "ACT_DECREASE";                //减少
    public final static String ACT_INCREASE = "ACT_INCREASE";                //增大
    public final static String ACT_QUERY = "ACT_QUERY";                      //减少
    public final static String ACT_STANDBY = "ACT_STANDBY";                  //待机
    public final static String ACT_HIBERATE = "ACT_HIBERATE";                //休眠
    public final static String ACT_SCENE = "ACT_SCENE";                      //模式
    public final static String ACT_SCENE_DEMO = "ACT_SCENE_DEMO";            //演示模式 回家模式、离家模式
    public final static String ACT_BATH = "ACT_BATH";                        //预约洗澡
    public final static String ATTR_TEMPERATURE = "ATTR_TEMPERATURE";        //温度
    public final static String ATTR_HUMIDITY = "ATTR_HUMIDITY";              //湿度
    public final static String ATTR_MODE = "ATTR_MODE";                      //模式
    public final static String ATTR_WIND_SPEED = "ATTR_WIND_SPEED";          //风速 （增大， 减少， 设置，查询）
    public final static String ATTR_WIND_DIRECTION = "ATTR_WIND_DIRECTION";  //上下摆风
    public final static String ATTR_AIR_QUALITY = "ATTR_AIR_QUALITY";
    public final static String ATTR_VOLUME = "ATTR_VOLUME";
    //空调
    public final static String ACT_SETTEMP= "set_tmp";                       //温度设定
    public final static String ACT_ADJTEMP= "adj_tmp";                       //温度调整
    public final static String ACT_SETSPEED= "set_speed";                    //风速设定
    public final static String ACT_SETMODE = "set_mode";                     //模式选择

    //载体
    public final static String ACT_ADJLIGHT = "adj_light";                   //亮度调整
    public final static String ACT_ADJVOICE = "adj_voice";                   //声音调整
    public final static String ACT_DEVMODE = "ops_mode";                     //灯光情景模式
    public final static String MODE_STANDARD = "标准";
    public final static String MODE_READ = "阅读";
    public final static String MODE_ROMANTIC = "浪漫";
    public final static String MODE_SLEEP_LIGHT = "睡眠";
    public final static String ACT_ADJHIGH = "high";                         //亮度，声音调高
    public final static String ACT_ADJLOW = "low";                           //亮度，声音调低
    public final static String ACT_MAXHIGH = "maxhigh";                      //最大
    public final static String ACT_MAXLOW = "maxlow";                        //最小


    //热水器
    public final static String ATTR_CURRENT_TEMP = "ATTR_CURRENT_TEMP";      //水温查询

    //新风
    public final static String MODE_HEAT_EXCHANGE = "MODE_HEAT_EXCHANGE";    //热交换功能
    public final static String MODE_MANUAL = "MODE_MANUAL";                  //手动模式


    //灯光
    public final static String ATTR_BRIGHTNESS = "ATTR_BRIGHTNESS";           //亮度(增大，减小，设置)
    public final static String ATTR_COLOR = "ATTR_COLOR";                     //颜色(设置)
   // public final static String ATTR_MODE = "ATTR_MODE";                     //模式(设置)
    //窗帘
    public final static String ATTR_STATUS = "ATTR_STATUS";                   //状态(设置)
    //场景
    public final static String ATTR_SCENE = "ATTR_SCENE";                     //场景
    //背景音乐
    public final static String ACT_NEXT = "ACT_NEXT";                         //下一首(/上一个频道)
    public final static String ACT_STOP = "ACT_STOP";                         //停止播放
    public final static String OBJ_VOLUMN = "OBJ_VOLUMN";                     //音量
    //电视
    public final static String ACT_PREV = "ACT_PREV";                         //下一个频道
    public final static String ACT_OPEN_CHANNEL = "ACT_OPEN_CHANNEL";         //打开频道
    //系统
    public final static String ATTR_ELEC_QTY = "ATTR_ELEC_QTY";              //剩余电量
    //
    public final static String COMMU_OK = "OK";
    public final static String COMMU_CANCEL = "CANCEL";
    public final static String COMMU_OK_LATER = "OK_LATER";
    public final static String ATTR_IP = "ATTR_IP";
    public final static String ATTR_MAC  = "ATTR_MAC";
    public final static String ATTR_UUID = "ATTR_UUID";
    /**
     * value  String  YES  温度取值：
     */
    //空调
    //风速取值：
    public final static String WIND_SPEED_LOW = "低风";             //低速风
    public final static String WIND_SPEED_MEDIUM = "中风";       //中速风
    public final static String WIND_SPEED_HIGH = "高风";           //高速风
    public final static String WIND_SPEED_STRONG = "自动";       //强劲，强劲风
    public final static String WIND_SPEED_AUTO = "WIND_SPEED_AUTO";           //自动风
    //风向取值：
    public final static String WIND_LEFT_RIGHT = "WIND_LEFT_RIGHT";           //左右摆风，左右摆动
    public final static String WIND_UP_DOWN = "WIND_UP_DOWN";                 //上下摆风
    public final static String WIND_LEFT = "WIND_LEFT";                       //向左吹，向左摆
    public final static String WIND_RIGHT = "WIND_RIGHT";                     //向右吹，向右摆
    public final static String WIND_UP = "WIND_UP";                           //向上吹，向上摆
    public final static String WIND_DOWN = "WIND_DOWN";                       //向下吹，向下摆
    public final static String WIND_LEFT_RIGHT_MOVE = "WIND_LEFT_RIGHT_MOVE"; //左右风点动
    public final static String WIND_UP_DOWN_MOVE = "WIND_UP_DOWN_MOVE";       //上下风点动
    public final static String WIND_BLOWS_PEOPLE = "WIND_BLOWS_PEOPLE";       //风吹人，风随人动
    public final static String WIND_AVOID_PEOPLE = "WIND_AVOID_PEOPLE";       //风避人，风逆人动
    //空调模式取值：
    public final static String MODE_COOL = "MODE_COOL";                       //制冷，制冷模式
    public final static String MODE_HEAT = "MODE_HEAT";                       //制热，制热模式
    public final static String MODE_WETTED = "MODE_WETTED";                   //抽湿模式，除湿
    public final static String MODE_AUTO = "MODE_AUTO";                       //自动模式
    public final static String MODE_AIR_SUPPLY  = "MODE_AIR_SUPPLY";         //送风模式，送风
    public final static String MODE_SILENCE = "MODE_SILENCE";                 //静音，静音模式
    public final static String MODE_ECO = "MODE_ECO";                         //省电模式，节能模式
    public final static String MODE_PURIFICATION = "MODE_PURIFICATION";       //净化模式
    public final static String MODE_AUTO_CLEAN = "MODE_AUTO_CLEAN";           //自清扫模式，自清洁模式
    public final static String MODE_HEALTH = "MODE_HEALTH";                   //健康模式
    public final static String MODE_VENTILATION = "MODE_VENTILATION";         //xin feng
    public final static String MODE_GOOD_SLEEP = "MODE_GOOD_SLEEP";           //舒睡，睡眠模式
    public final static String MODE_ELECTRIC_AUXILIARY_HEAT = "MODE_ELECTRIC_AUXILIARY_HEAT";//电辅热， 电加热
    public final static String MODE_SMART_WIND = "MODE_SMART_WIND";           //睿风
    public final static String MODE_DRY_WIND = "MODE_DRY_WIND";               //干燥
    public final static String MODE_HUMIDIFICATION = "MODE_HUMIDIFICATION";   //加湿
    public final static String MODE_REACTION = "MODE_REACTION";               //感应，光感睡眠(红外感人)
    public final static String MODE_SMART = "MODE_SMART";                     //智能模式

    public final static String AIR_HOT = "AIR_HOT";
    public final static String AIR_TOO_HOT = "AIR_TOO_HOT";
    public final static String AIR_COLD = "AIR_COLD";
    public final static String AIR_TOO_COLD = "AIR_TOO_COLD";
    public final static String VOLUME_NOISY = "VOLUME_NOISY";
    public final static String VOLUME_TOO_NOISY = "VOLUME_TOO_NOISY";
    public final static String AIR_HUMIDITY_HIGH = "AIR_HUMIDITY_HIGH";
    public final static String AIR_QUALITY_POOR = "AIR_QUALITY_POOR";


    //热水器
    //public final static String ATTR_CURRENT_TEMP = "ATTR_CURRENT_TEMP";
    public final static String ATTR_TIME_LEFT_BATH = "ATTR_TIME_LEFT_BATH";
    public final static String ATTR_IS_BATH_READY = "ATTR_IS_BATH_READY";
    public final static String ATTR_TIME_BATH_READY = "ATTR_TIME_BATH_READY";
    public final static String ATTR_WATER_TEMPERATURE = "ATTR_WATER_TEMPERATURE";
    public final static String WATER_COLD = "WATER_COLD";
    //空气净化器
    public final static String MODE_SLEEP = "MODE_SLEEP";                     //睡眠模式

    //灯光
    public final static String BRIGHT_LOW = "BRIGHT_LOW";                     //低档
    public final static String BRIGHT_MIDDLE = "BRIGHT_MIDDLE";               //中档
    public final static String BRIGHT_HIGH = "BRIGHT_HIGH";                   //高档

    //窗帘
    public final static String STATUS_ALL_OPEN = "STATUS_ALL_OPEN";           //全开
    public final static String STATUS_ALL_CLOSE = "STATUS_ALL_CLOSE";         //全关
    public final static String STATUS_HALF_OPEN = "STATUS_HALF_OPEN";         //半开
    public final static String STATUS_HALF_CLOSE = "STATUS_HALF_CLOSE";       //半关

    //冰箱
    public final static String ATTR_FOOD_IN_FRIDGE = "ATTR_FOOD_IN_FRIDGE";
    public final static String ATTR_EXPIRED_FOOD = "ATTR_EXPIRED_FOOD";
    public final static String ATTR_NEARLY_EXPIRED_FOOD = "ATTR_NEARLY_EXPIRED_FOOD";
    public final static String ATTR_FRESH_FOOD = "ATTR_FRESH_FOOD";
    public final static String ATTR_FOOD_LIFE_DAY = "ATTR_FOOD_LIFE_DAY";
    public final static String ATTR_WHAT_IS_THIS = "ATTR_WHAT_IS_THIS";
    public final static String ATTR_MENU = "ATTR_MENU";
    public final static String OBJ_TOMATO = "OBJ_TOMATO";
    public final static String OBJ_APPLE = "OBJ_APPLE";
    public final static String OBJ_BANANA = "OBJ_BANANAs";
    public final static String OBJ_CABBAGE = "OBJ_CABBAGE";
    public final static String OBJ_ORANGE = "OBJ_ORANGE";

    //洗衣机
    public final static String ATTR_TIME_LEFT_WASH = "ATTR_TIME_LEFT_WASH";
    public final static String ATTR_DETERGENT_LEFT = "ATTR_DETERGENT_LEFT";
    public final static String ATTR_SELECT_WASH_PROGRAM = "ATTR_SELECT_WASH_PROGRAM";
    public final static String MODE_CHILD_LOCK = "MODE_CHILD_LOCK";
    public final static String AUTO_DELIVERY_SOFTENER = "AUTO_DELIVERY_SOFTENER";   //柔顺剂自动投放
    public final static String AUTO_DELIVERY_DETERGENT = "AUTO_DELIVERY_DETERGENT"; //洗涤剂自动投放
    public final static String MODE_COTTON = "MODE_COTTON";                         //棉麻
    public final static String MODE_CHEMICAL_FIBER = "MODE_CHEMICAL_FIBER";         //化纤
    public final static String MODE_DOWN = "MODE_DOWN";                             //羽绒
    public final static String MODE_CURTAIN = "MODE_CURTAIN";                       //窗帘
    public final static String MODE_MIX = "MODE_MIX";                               //混合
    public final static String MODE_SHIRT = "MODE_SHIRT";                           //衬衣
    public final static String MODE_CHILDREN_CLOTHES = "MODE_CHILDREN_CLOTHES";     //童装
    public final static String MODE_SUPER_SOFT = "MODE_SUPER_SOFT";                 //超柔
    public final static String MODE_HOT = "MODE_HOT";                               //烫烫净
    public final static String MODE_OUTDOOR = "MODE_OUTDOOR";                       //户外
    public final static String MODE_HAND = "MODE_HAND";                             //手洗
    public final static String MODE_DRY_STRONG = "MODE_DRY_STRONG";                 //强烘
    public final static String MODE_DRY_WEAK = "MODE_DRY_WEAK";                     //弱烘
   // public final static String

    //模式
    public final static String MODE_HOME = "MODE_HOME";                       //在家
    public final static String MODE_OUT = "MODE_OUT";                         //离家

    /**
     * 控制小车行走
     */
    //operands
    public final static String OBJ_ROBOT = "OBJ_ROBOT";                       //

    //operator
    public final static String ACT_ARROW_FORWARD = "ACT_ARROW_FORWARD";       //前进
    public final static String ACT_ARROW_BACKWARD = "ACT_ARROW_BACKWARD";     //后退
    public final static String ACT_ARROW_LEFT = "ACT_ARROW_LEFT";             //左转
    public final static String ACT_ARROW_RIGHT = "ACT_ARROW_RIGHT";           //右转
    public final static String ACT_ARROW_CIRCLE = "ACT_ARROW_CIRCLE";         //转圈
    public final static String ACT_ARROW_STOP = "ACT_STOP";                   //停止
    public final static String ACT_DANCE = "ACT_DANCE";                       //dance
    public final static String ACT_STOP_DANCE = "ACT_STOP_DANCE";             //STOP DANCE

    /**
     * origin type
     */
    public final static String ORIGIN_SET = "cn.yunzhisheng.setting";                      //系统设置
    public final static String ORIGIN_AC = "cn.yunzhisheng.setting.ac";                    //空调
    public final static String ORIGIN_WATER_HEATER = "cn.yunzhisheng.setting.waterheater"; //热水器
    public final static String ORIGIN_AIR_CLEANER = "cn.yunzhisheng.setting.aircleaner";   //空气净化器                     //空气净化器
    public final static String ORIGIN_FAS = "cn.yunzhisheng.setting.fas";                  //新风系统
    public final static String ORIGIN_HEATER = "cn.yunzhisheng.setting.heater";            //地暖
    public final static String ORIGIN_CURTAIN = "cn.yunzhisheng.setting.curtain";          //窗帘
    public final static String ORIGIN_OUTLET = "cn.yunzhisheng.setting.outlet";            //插座
    public final static String ORIGIN_LIGHT = "cn.yunzhisheng.setting.light";              //灯光
    public final static String ORIGIN_MUSIC = "cn.yunzhisheng.setting.mp";                 //背景音乐
    public final static String ORIGIN_TV = "cn.yunzhisheng.setting.tv";                    //电视
    public final static String ORIGIN_ROBOT = "cn.yunzhisheng.setting.robot";              //控制机器人运动
    public final static String ORIGIN_FRIDGE = "cn.yunzhisheng.setting.fridge";            //冰箱
    public final static String ORIGIN_BARFRIDGE = "cn.yunzhisheng.setting.barfridge";      //冰吧
    public final static String ORIGIN_SCENE = "cn.yunzhisheng.setting.scene";              //场景
    public final static String ORIGIN_WASHER = "cn.yunzhisheng.setting.washer";            //洗衣机
}
