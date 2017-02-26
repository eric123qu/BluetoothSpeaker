package com.haier.ai.bluetoothspeaker;

import com.haierubic.ai.TtsPlayerStatus;

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

    /**
     * 后台接口域
     */
    public static final String DOMAIN_NEWS = "com_news";        //新闻

    public static final String DOMAIN_LIMIT = "com_bus";        //限号查询

    public static final String DOMAIN_WEATHER = "com_weather";  //天气

    public static final String DOMAIN_MUSIC = "com_music";      //音乐

    public static final String DOMAIN_DEVICE = "music_device";  //载体本身 载体灯光模式

    public static final String DOMAIN_MUSIC_STATUS = "music_music_status";  //音箱音乐

    public static final String DOMAIN_AIR_MAGIC = "air_magic";  //空气魔方

    public static final String DOMAIN_AC = "Air_conditioner";   //空调

    public static final String DOMAIN_ALARM = "com_alarm";      //闹钟提醒

    public static final String DOMAIN_CONTELLATION = "com_constellation";   //星座

    public static final String DOMAIN_OIL = "com_oil";          //油价

    public static final String DOMAIN_STOCK = "com_shares";     //股票

    public static final String DOMAIN_WEEK = "com_week";        //星期

    public static final String DOMAIN_DAY = "com_day";          //几号

    public static final String DOMAIN_TRANSFER = "com_transfer";          //翻译

    public static final String DOMAIN_HOLIDAY = "com_holiday";    //节假日

    public static final String DOMAIN_HOTLINE = "com_hotline";    //热线

    public static final String DOMAIN_MOVIE = "com_movie";    //电影

    public static final String DOMAIN_CROSSTALK = "com_crosstalk";    //相声

    public static final String DOMAIN_CHILD = "com_child";    //儿歌

    public static final String DOMAIN_CALENDAR = "com_calendar";    //农历

    public static final String DOMAIN_CALCULATOR = "com_calculator";    //计算器

    public static final String DOMAIN_OTHER = "com_other";    //调侃资源

    public static final String DOMAIN_MUSIC_CONTROL = "com_music_control";    //音乐控制

    public static final String DOMAIN_GAME = "游戏";    //游戏

    public static final String DOMAIN_FOOD = "food_menu";    //菜谱

    public static final String DOMAIN_POETRY = "com_poetry";    //诗歌


    /**
     * tts 回复
     */
    public static final String TTS_REPLY_ERROR = "对不起，我没听清楚";

    public static final String TTS_REPLY_NO_RESOURCE1 = "抱歉，这项技能我还在学习中";

    public static final String TTS_REPLY_NO_RESOURCE2= "噢，我好累，咱们休息一下吧";

    public static final String TTS_REPLY_NO_RESOURCE3= "这个嘛。。。我还需要修炼修炼";

    /**
     * 音乐播放状态
     */
    public static final int STATE_STOP = 0;                //播放停止

    public static final int STATE_PLAYING = 1;             //正在播放

    public static final int STATE_PAUSE = 2;               //暂停播放

    public static final int STATE_BUFFING = 3;              //正在缓冲

    public static boolean IS_FIRST_WAKEUP;                  //是否首次唤醒

    public static int register_wakeup = 0;

    public static int TTS_PLAY_STATUS = TtsPlayerStatus.TTS_PLAYER_STATUS_IDLE; //tts 状态

    public static boolean ISDIALOG = false;                 //多伦对话

}
