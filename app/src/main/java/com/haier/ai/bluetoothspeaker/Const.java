package com.haier.ai.bluetoothspeaker;

/**
 * author: qu
 * date: 16-11-7
 * introduce:
 */

public class Const {
    /*** blutooth uuid*/
    public static final String UUID = "00001101-0000-1000-8000-00805f9b34fb";

    //通信类型WiFi
    public static final int TYPE_WIFI = 0;

    //通信类型 闹钟
    public static final int TYPE_ALARM = 1;

    //通信类型 灯光控制
    public static final int TYPE_LIGHT = 2;

    //通信类型 载体设置
    public static final int TYPE_SETTING = 3;

    //语音识别间隔时间
    public static final int RECONIZE_INTERVAL = 4;


    public static  final String WAKEUP_TAG = "com.haier.ai.wait4wakeup";

    public static final String RERECONIZE_TAG = "com.haier.ai.rereconize";

    public static final String RET_CODE_SUCESS = "00000";

    public static final String BASE_URL = "http://120.27.157.19:9030/ai-access/";

    //内容请求接口
    public static final String URL_CONTENT = "content";

    public static final String DOMAIN_NEWS = "com_news";        //新闻

    public static final String DOMAIN_LIMIT = "com_bus";        //限号查询

    public static final String DOMAIN_WEATHER = "com_weather";  //天气

    public static final String DOMAIN_MUSIC = "com_music";      //音乐

    public static final String DOMAIN_DEVICE = "music_device";  //载体本身

    public static final String DOMAIN_AC = "Air_conditioner";   //空调

    public static final String DOMAIN_ALARM = "com_alarm";      //闹钟提醒

    public static final String DOMAIN_CONTELLATION = "com_constellation";   //星座

    public static final String DOMAIN_OIL = "com_oil";          //油价

    public static final String DOMAIN_STOCK = "com_shares";     //股票

    public static final String DOMAIN_WEEK = "com_week";        //星期

    public static final String DOMAIN_DAY = "com_day";          //几号

    public static final String DOMAIN_TRANSFER = "com_transfer";          //翻译

    public static final String DOMAIN_HOLIDAY = "com_constellation";    //节假日

    public static final String TTS_REPLY_ERROR = "对不起，我没听清楚";
    /**
     * 音乐播放状态
     */
    public static final int STATE_STOP = 0;                //播放停止

    public static final int STATE_PLAYING = 1;             //正在播放

    public static final int STATE_PAUSE = 2;               //暂停播放

    public static final int STATE_BUFFING = 3;              //正在缓冲

    public static boolean IS_FIRST_WAKEUP;                  //是否首次唤醒
}
