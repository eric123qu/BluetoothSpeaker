package com.haier.ai.bluetoothspeaker.model;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.haier.ai.bluetoothspeaker.App;
import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.DeviceConst;
import com.haier.ai.bluetoothspeaker.audio.AudioInput;
import com.haier.ai.bluetoothspeaker.audio.AudioOutput;
import com.haier.ai.bluetoothspeaker.bean.Oilprice.RequestOilprice;
import com.haier.ai.bluetoothspeaker.bean.Oilprice.ResponseOilprice;
import com.haier.ai.bluetoothspeaker.bean.box.boxNluBean;
import com.haier.ai.bluetoothspeaker.bean.calendar.RequestCalendar;
import com.haier.ai.bluetoothspeaker.bean.calendar.ResponseCalendar;
import com.haier.ai.bluetoothspeaker.bean.constellation.RequestConstellation;
import com.haier.ai.bluetoothspeaker.bean.constellation.ResponseConstellation;
import com.haier.ai.bluetoothspeaker.bean.holiday.RequestHoliday;
import com.haier.ai.bluetoothspeaker.bean.holiday.ResponseHoliday;
import com.haier.ai.bluetoothspeaker.bean.hotline.RequestHotline;
import com.haier.ai.bluetoothspeaker.bean.hotline.ResponseHotline;
import com.haier.ai.bluetoothspeaker.bean.limit.RequestLimit;
import com.haier.ai.bluetoothspeaker.bean.limit.ResponseLimit;
import com.haier.ai.bluetoothspeaker.bean.movie.RequestMovie;
import com.haier.ai.bluetoothspeaker.bean.movie.ResponseMovie;
import com.haier.ai.bluetoothspeaker.bean.music.RequestMusic;
import com.haier.ai.bluetoothspeaker.bean.music.ResponseMusic;
import com.haier.ai.bluetoothspeaker.bean.news.RequestNews;
import com.haier.ai.bluetoothspeaker.bean.news.ResponseNews;
import com.haier.ai.bluetoothspeaker.bean.stock.RequestStock;
import com.haier.ai.bluetoothspeaker.bean.stock.ResponseStock;
import com.haier.ai.bluetoothspeaker.bean.stock.ResponseStock1;
import com.haier.ai.bluetoothspeaker.bean.translation.RequestTrans;
import com.haier.ai.bluetoothspeaker.bean.translation.ResponseTrans;
import com.haier.ai.bluetoothspeaker.bean.weather.RequestAqi;
import com.haier.ai.bluetoothspeaker.bean.weather.RequestWeather;
import com.haier.ai.bluetoothspeaker.bean.weather.ResponseAqi;
import com.haier.ai.bluetoothspeaker.bean.weather.ResponseWeather;
import com.haier.ai.bluetoothspeaker.bean.ximalaya.RequestXimalaya;
import com.haier.ai.bluetoothspeaker.bean.ximalaya.ResponseXimalaya;
import com.haier.ai.bluetoothspeaker.event.ErrorEvent;
import com.haier.ai.bluetoothspeaker.event.NluEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeResultEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeStatusEvent;
import com.haier.ai.bluetoothspeaker.event.StartRecordEvent;
import com.haier.ai.bluetoothspeaker.event.UrlMusicEvent;
import com.haier.ai.bluetoothspeaker.manager.LightManager;
import com.haier.ai.bluetoothspeaker.manager.MusicPlayerManager;
import com.haier.ai.bluetoothspeaker.manager.ProtocolManager;
import com.haier.ai.bluetoothspeaker.manager.RetrofitApiManager;
import com.haier.ai.bluetoothspeaker.net.AIApiService;
import com.haier.ai.bluetoothspeaker.util.SpeechJavaBeanUtils;
import com.haierubic.ai.ErrorCode;
import com.haierubic.ai.IAsrRecorder;
import com.haierubic.ai.IAsrRecorderCallback;
import com.haierubic.ai.IFilter;
import com.haierubic.ai.INlu;
import com.haierubic.ai.INluCallback;
import com.haierubic.ai.ITtsPlayer;
import com.haierubic.ai.ITtsPlayerCallback;
import com.haierubic.ai.TtsPlayerMode;
import com.haierubic.ai.UbicAI;
import com.lidroid.xutils.HttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: qu
 * date: 16-8-30
 * introduce: 采用两种方式，一种是API方式，另一种是SDK方式，以下有注释
 */
public class RecordModel {
    private final String TAG = "RecordModel";
    private static final int TYPE_AIR = 0;
    private static final int  TYPE_FRIDGE = 1;
    private static RecordModel sRecordModel;
    private static IAsrRecorder mRecorder;
    private static ITtsPlayer mPlayer;
    private static HttpUtils httpUtils;
    private static INlu nlu;
    private static INluCallback cb;
    @IntDef({TYPE_AIR, TYPE_FRIDGE}) @interface ControlType{}
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    public static RecordModel getInstance(){
        if(sRecordModel == null){
            sRecordModel = new RecordModel();
        }

        return sRecordModel;
    }

    public RecordModel(){
        // 初始化SDK。可选的返回值通过ErrorCode获取。
        Log.d(TAG, "RecordModel: constract");
        initSdk();

        initNet();

        //add 初始化语音识别
        initRecorder();

        initNlu();
    }

    public void initNet(){
        if(httpUtils == null){
            httpUtils = new HttpUtils();
        }
    }

    private void initNlu(){
        // 创建语义理解对象。
        if(nlu == null){
            nlu = UbicAI.createNlu(null);
        }
        //INlu nlu  = UbicAI.createNlu(null);
        // 创建语义理解回调函数。
       // INluCallback cb = new INluCallback() {
        INluCallback cb = new INluCallback() {

            @Override
            public void onError(int arg0, String arg1) {
                final String msg = String.format("nlu onError(): errcode = %d, msg = %s", arg0, arg1);
                EventBus.getDefault().post(new ReconizeResultEvent("语义理解错误"));
                //sendReReconizeEvent(true);
                LightManager.getInstance().lightNormal();
                waitForWakeup();
            }

            @Override
            public void onResult(int arg0, String arg1) {
                // 返回识别结果。
                final String msg = String.format("nlu onResult(): errcode = %d, msg = %s", arg0, arg1);
                Log.d(TAG, "onResult: nlu:" + msg);

                LightManager.getInstance().lightNormal();
                /* String tts = parseNluResult(arg1);

                if(TextUtils.isEmpty(tts)){
                    EventBus.getDefault().post(new NluEvent("语义理解错误"));
                    waitForWakeup();
                }else */

                {
                    EventBus.getDefault().post(new NluEvent(arg1));
                    Gson gson = new Gson();
                    boxNluBean resp = gson.fromJson(arg1, boxNluBean.class);
                    if(resp.getRetCode().equals("00000")){
                        parseNlpResult(resp);
                    }else{
                        showTtsResult("对不起我没理解明白请再说一遍");
                    }
                }
            }

        };

        // 绑定语义理解回调函数。
        nlu.attach(cb);
    }

    public void stopPlayTTs(){
        if(mPlayer != null){
            mPlayer.stop();
        }
    }

    public void startRecord(){
        Log.d(TAG, "startRecord: ");

        // 通过initOk()函数可以检测SDK的初始化状态。
        if (UbicAI.initOk() != ErrorCode.UAI_ERR_NONE){
            Log.d(TAG, "startRecord: sdk is not init");
            return;
        }

        int err;
        // 录音识别。
        // 通过start()方法开始录音识别。stop()方法结束并进行录音识别。cancel()方法结束不进行识别。
        // 目前只支持主动调用stop()接口的识别，即按住说。其它识别方式开发中。
        //String config = null;
        String config = "{\"vad\":\"true\"}";
        if ((err = mRecorder.start(config)) != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "startRecord: fail to set audio source filter: " + err);
            return;
        }

        LightManager.getInstance().lightRecognize();
        Log.d(TAG, "startRecord: start recorder end.");
    }

    public void stopRecord(){
        Log.d(TAG, "stopRecord: ");

        // 通过initOk()函数可以检测SDK的初始化状态。
        if (UbicAI.initOk() != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "stopRecord: sdk is not init");
            return;
        }

        if (mRecorder == null) {
            Log.d(TAG, "stopRecord: recorder does not work.");
            return;
        }

        int err;
        // 关闭语音识别录音机。
        // 因为非流式识别的原因，需要在录音结束后再上传数据，可能会造成识别时间比较长。
        try {
            if ((err = mRecorder.stop()) != ErrorCode.UAI_ERR_NONE) {
                Log.e(TAG, String.format("recorder stop failed = %d", err));

                //进入待唤醒
                waitForWakeup();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "stop recorder end.");
    }

    public void cancelRecord(){
        Log.d(TAG, "cancelRecord: ");

        // 通过initOk()函数可以检测SDK的初始化状态。
        if (UbicAI.initOk() != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "cancelRecord: sdk is not init");
            return;
        }

        if (mRecorder == null) {
            Log.d(TAG, "cancelRecord: recorder does not work.");
            return;
        }

        int err;
        if ((err = mRecorder.cancel()) != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "cancelRecord: " + String.format("recorder cancel failed = %d", err));
        }

        Log.d(TAG, "cancelRecord: cancel recorder end.");
    }

    /*private void initGetToken(String token) {
        *//**
         * 确保app刚安装上，token为空的时候，去请求token,token长度为30
         *//*
        if (TextUtils.isEmpty(token) || token.length() != 30) {
//            NetRequest.getInstance().getToken();
            if (NetWorkUtils.isNetworkConnected(MyApplication.getInstance().mContext)){
                RetrofitRequest.getInstance().getToken();
            }else{
                Log.e(TAG, "initGetToken: 您的网络未连接，请连接网络，或您的网络无法连接外网");
            }
        }
    }*/

    public void sendReReconizeEvent(boolean bReReconize){
        StartRecordEvent event = new StartRecordEvent(bReReconize);
        EventBus.getDefault().post(event);
    }

    private void initSdk(){
        String config = null;
        int err = UbicAI.init(config, App.getInstance());
        if (err != ErrorCode.UAI_ERR_NONE){
            Log.e(TAG, "initSdk: fail to init sdk.");
            EventBus.getDefault().post(new ErrorEvent("initSdk: fail to init sdk."));
        }
    }

    public void releaseSdk(){
        if (mRecorder != null)
        {
            mRecorder.cancel();
            mRecorder = null;
        }

        // 返初始化SDK，释放资源。
        //UbicAI.release();
    }

    /**
     * 初始化语音识别录音机。
     */
    private void initRecorder() {
        // 初始化音源。
        // 应用层可以通过继承IFilter接口，自定义音源获取方法。
        // 自定义音源接口成员函数应当满足通过设计要求。否则影响正常的状态转换，导致识别失败。
        IFilter audioSource = new AudioInput();

        // 创建录音机。参数保留用于控制生成的录音机。目前传null即可。
        mRecorder = UbicAI.createAsrRecorder(null);
        int err;
        if ((err = mRecorder.setInputFilter(audioSource)) != ErrorCode.UAI_ERR_NONE)
        {
            // 创建ASR录音机失败。
            Log.d(TAG, "initRecorder: fail to set audio source filter:" + err);
            mRecorder = null;
            UbicAI.release();
            return;
        }

        // 设置识别回调函数。
        IAsrRecorderCallback callback = new IAsrRecorderCallback() {
            @Override
            public void onEvent(int paramInt1, int paramInt2) {
                // 识别事件。VAD状态检测将通过该回调返回，目前不支持。
                Log.d(TAG, String.format("onEvent(): errcode = %d, param = %d", paramInt1, paramInt2));
            }

            @Override
            public void onError(int paramInt, String paramString) {
                // 识别时有错误发生。
                Log.d(TAG, String.format("onError(): errcode = %d, msg = %s", paramInt, paramString));
                EventBus.getDefault().post(new ReconizeResultEvent("语音识别错误"));
                LightManager.getInstance().lightNormal();
                waitForWakeup();
            }

            @Override
            public void onResult(int paramInt, String paramString) {
                // 返回识别结果。
                final String msg = String.format("onResult(): errcode = %d, msg = %s", paramInt, paramString);
                String asrResult = getAsrResult(paramString);
                //// TODO: 16-9-29 状态显示语音识别成功
                EventBus.getDefault().post(new ReconizeStatusEvent("识别结束"));

                Log.e(TAG, "onResult: asrresult:" + asrResult );
                if(TextUtils.isEmpty(asrResult)){
                    EventBus.getDefault().post(new ReconizeResultEvent("语音接口返回识别错误状态"));
                    playTTS("对不起我没听清楚");
                    LightManager.getInstance().lightNormal();
                }else {
                    //去掉标点
                    asrResult = asrResult.replace(",", "");
                    asrResult =asrResult.replace("。", "");
                    //asrResult.replaceAll("?", " ");
                    asrResult =asrResult.replace(".", "");
                    asrResult =asrResult.replace("，", "");
                    asrResult =asrResult.replace("，", "");
                    asrResult =asrResult.replace("？", "");
                    EventBus.getDefault().post(new ReconizeResultEvent(asrResult));
                    //调用nlu接口，语义理解(空调)sdk
                    String nlu = formatNluRequest(asrResult, TYPE_FRIDGE);
                    getNluResult(nlu);

                    //冰箱(api)
                    //getNlpResult(asrResult);
                }
            }

            @Override
            public void onVolume(double volume){
                //Log.d(TAG, "onVolume: " + String.format("volume=%f", volume));
            }
        };

        // 设置识别状态回调。
        // 使用attach接口的可以支持多个回调的定义。使用结束后通过detach接口取消设置。
        // 回调函数目前与录音线程在一起，应用层应当尽快返回。
        if ((err = mRecorder.attach(callback)) != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "initRecorder: fail to set audio source filter: " + err);
            mRecorder = null;
            UbicAI.release();
            return;
        }
    }

    /**
     * 获取asr结果
     * @param msg
     * @return
     */
    private String getAsrResult(String msg){
        if(TextUtils.isEmpty(msg)){
            return null;
        }

        return SpeechJavaBeanUtils.S2TgetText(msg);
    }

    /**
     * 创建语义理解
     * @param asrData
     */
    public void getNluResult(String asrData){
        // 创建语义理解对象。
        /*if(nlu == null){
            nlu = UbicAI.createNlu(null);
        }*/

        /*INlu nlu  = UbicAI.createNlu(null);
        // 创建语义理解回调函数。
        INluCallback cb = new INluCallback() {

            @Override
            public void onError(int arg0, String arg1) {
                final String msg = String.format("nlu onError(): errcode = %d, msg = %s", arg0, arg1);
                //EventBus.getDefault().post(new ReconizeResultEvent("语义理解错误"));
                //sendReReconizeEvent(true);
                waitForWakeup();
            }

            @Override
            public void onResult(int arg0, String arg1) {
                // 返回识别结果。
                final String msg = String.format("nlu onResult(): errcode = %d, msg = %s", arg0, arg1);
                Log.d(TAG, "onResult: nlu:" + msg);

                *//* String tts = parseNluResult(arg1);

                if(TextUtils.isEmpty(tts)){
                    EventBus.getDefault().post(new NluEvent("语义理解错误"));
                    waitForWakeup();
                }else *//*

                {
                    //EventBus.getDefault().post(new NluEvent(tts));
                    Gson gson = new Gson();
                    boxNluBean resp = gson.fromJson(arg1, boxNluBean.class);
                    if(resp.getRetCode().equals("00000")){
                        parseNlpResult(resp);
                    }else{
                        showTtsResult("对不起我没理解明白请再说一遍");
                    }


                    //playTTS(tts);
                    //playTTS_api(tts);
                }
            }

        };

        // 绑定语义理解回调函数。
        nlu.attach(cb);*/

        // 开始识别。保留参数用于识别配置，目前不用填。
        String config = "{\"domain\":\"box\"}";
        nlu.start(config);

        // 进行语义识别。语法格式不固定，先由应用层根据协议组包请求(示例固定)，之后固定后会制作工具类。
        nlu.recog(asrData);

        // 结束语义识别。
        nlu.stop();
    }

    /**
     * 组装nlu请求报文
     * @param query
     */
    public String formatNluRequest(String query, @ControlType int type){
        StringBuilder sb = new StringBuilder();
        String sType = null;

        if(TextUtils.isEmpty(query)){
            return null;
        }

        switch(type){
            case TYPE_AIR:
                sType = "空调";
                break;
            case TYPE_FRIDGE:
                sType = "冰箱";
                break;
        }

        String contextid = "";
        if (DeviceConst.FIRST_USE_NLU){
            contextid = "";
            DeviceConst.FIRST_USE_NLU = false;
        }else {
            contextid = "123456";
        }

        return String.format("{\"LIBaseinfo\":{\"category\":" + "\"" +
                sType + "\"" +
                ",\"city\":\"青岛\",\"contextid\":" + "\"" +
                contextid + "\"" +
                ",\"latitude\":12.8," +
                "\"longitude\":123.5,\"query\":" + "\"" +
                query + "\"" +
                ",\"region\":\"李沧区\"},\"devices\":[{\"attrs\":{\"brand\":\"天意\",\"model\":\"蔚蓝\"},\"deviceModules\":[{\"moduleId\":\"C8934644F82\",\"moduleType\":\"wifimodule\"}],\"id\":\"C8934644F82\",\"name\":\"客厅空调\",\"typeInfo\":{\"typeId\":\"00000000000000008080000000041410\"}}],\"devicestate\":{\"curhumidity\":67,\"curtemp\":28,\"mode\":\"自动\",\"settemp\":26,\"windspeed\":2}}");
    }


//    String.format("{\"LIBaseinfo\":{\"category\":" + "\"" +
//    sType + "\"" +
//            ",\"city\":\"青岛\",\"contextid\":\"123456\",\"latitude\":12.8," +
//            "\"longitude\":123.5,\"query\":" + "\"" +
//    query + "\"" +
//            ",\"region\":\"李沧区\"},\"devices\":[{\"attrs\":{\"brand\":\"天意\",\"model\":\"蔚蓝\"},\"deviceModules\":[{\"moduleId\":\"C8934644F82\",\"moduleType\":\"wifimodule\"}],\"id\":\"C8934644F82\",\"name\":\"客厅空调\",\"typeInfo\":{\"typeId\":\"00000000000000008080000000041410\"}}],\"devicestate\":{\"curhumidity\":67,\"curtemp\":28,\"mode\":\"自动\",\"settemp\":26,\"windspeed\":2}}");

    public void playTTS(String content){
        Log.d(TAG, "playTTS: ");
        waitForWakeup();

        if(TextUtils.isEmpty(content)){
            return;
        }

        if (mPlayer == null)
        {
            initPlayer();
        }

        mPlayer.stop();

        int err;
        //String config = null;
        if ((err = mPlayer.start(null)) != ErrorCode.UAI_ERR_NONE)
        {
            Log.e(TAG, "playTTS: " +  String.format("tts player start failed = %d", err));
        }

        mPlayer.play(content, TtsPlayerMode.TTS_PLAYER_MODE_NEW);

        Log.d(TAG, "playTTS: tts player start end");

    }

    public void waitForWakeup(){
        Intent intent = new Intent(Const.WAKEUP_TAG);
        App.getInstance().sendBroadcast(intent);

        EventBus.getDefault().post(new ReconizeStatusEvent("待唤醒"));
//        recorder stop failed
    }

    public void stopTTS(){
        int err;
        if ((err = mPlayer.stop()) != ErrorCode.UAI_ERR_NONE)
        {
            Log.e(TAG, "stopTTS: " + String.format("tts player stop failed = %d", err));
        }

        Log.d(TAG, "stopTTS: tts player stop end");
    }

    private void initPlayer(){
        mPlayer = UbicAI.createTtsPlayer(null);

        IFilter output = new AudioOutput();
        mPlayer.setOutputFilter(output);

        ITtsPlayerCallback cb = new ITtsPlayerCallback() {

            @Override
            public void onError(int arg0, String arg1) {
                Log.e(TAG, "onError: " + String.format("onError(): errcode = %d, param = %s", arg0, arg1));
            }

            @Override
            public void onEvent(int arg0, int arg1) {
                Log.e(TAG, "onEvent: " + String.format("onEvent(): errcode = %d, param = %d", arg0, arg1));
                Const.TTS_PLAY_STATUS = arg0;
            }

            @Override
            public void onResult(int arg0, String arg1) {
                Log.d(TAG, "onResult: " + String.format("onResult(): errcode = %d, param = %s", arg0, arg1));
            }

        };

        mPlayer.attach(cb);
    }

    private void parseNlpResult(boxNluBean resp){
        fixResponseData(resp);

        nlpControl(resp);
    }

    private void fixResponseData(boxNluBean resp){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();

        if(params == null){
            return;
        }

        for (boxNluBean.DataBean.SemanticBean.ParasBean param:params) {
            String tmpKey = param.getKey();
            if(tmpKey.contains("{")){
                String key = tmpKey.substring(1, tmpKey.length()-1);
                param.setKey(key);
            }

            String tmpValue = param.getValue();
            if(tmpValue.contains("{")){
                String value = tmpValue.substring(1, tmpValue.length()-1);
                param.setValue(value);
            }
        }
    }

    /**
     * 控制
     * @param resp
     */
    private void nlpControl(boxNluBean resp){
        String operands = resp.getData().getSemantic().getDomain();
        boolean isDialog = resp.getData().getSemantic().isIs_dialog();
        String response = resp.getData().getSemantic().getResponse();

        if(TextUtils.isEmpty(operands)){
            if(TextUtils.isEmpty(response)){
                playTTS("对不起我没听清楚");
            }else{
                playTTS(response);
            }

            return;
        }

        handlerDomain(resp, operands);

        /*if(isDialog){
            //// TODO: 17-2-14 根据response 播放提示音，开启录音
            EventBus.getDefault().post(new DialogEvent("new dialog"));
        }else {
            handlerSingleCommand(resp);
        }*/
    }

    /**
     * 按照domain 处理
     * @param resp
     * @param domain
     */
    private void handlerDomain(boxNluBean resp, String domain){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = null;
        String value = "";

        switch (domain){
            case Const.DOMAIN_NEWS: //新闻查询
                params = resp.getData().getSemantic().getParas();
                if(params != null){
                    for(boxNluBean.DataBean.SemanticBean.ParasBean param : params){
                        if(param.getKey().equals("news_category")){
                            value = param.getValue();
                        }
                    }
                }

                getNewsContent(value);

                break;
            case Const.DOMAIN_LIMIT: //限号查询
                getLimitContent(null, null);
                break;
            case Const.DOMAIN_WEATHER:  //空气质量查询
                handlerWeather(resp);

                break;
            case Const.DOMAIN_MUSIC:
                playMusic(resp);
                //waitForWakeup();

                break;
            case Const.DOMAIN_DEVICE:
            case Const.DOMAIN_AC:
                handlerSingleCommand(resp);
                break;
            case Const.DOMAIN_ALARM:
                handlerAlarmEvent(resp);
                break;
            case Const.DOMAIN_OIL:
                handlerOilEvent(resp);
                break;
            case Const.DOMAIN_DAY:  //日期
                HandlerDayEvent();
                break;
            case Const.DOMAIN_WEEK:
                HandlerWeekEvent();
                break;
            case Const.DOMAIN_STOCK:
                HandlerStock(resp);
                break;
            case Const.DOMAIN_TRANSFER:
                HandlerTransfer(resp);
                break;
            case Const.DOMAIN_HOLIDAY:
                HandlerHolidayQuery(resp);
                break;
            case Const.DOMAIN_HOTLINE:
                HandlerHotline(resp);
                break;
            case Const.DOMAIN_MOVIE:
                HandlerMovie(resp);
                break;
            case Const.DOMAIN_CHILD:
            case Const.DOMAIN_CROSSTALK:
                HandlerXimalaya(resp);
                break;
            case Const.DOMAIN_CONTELLATION://星座
                HandlerContellation(resp);
                break;
            case Const.DOMAIN_CALENDAR:
                HandlerCalendar();
                break;
            case Const.DOMAIN_CALCULATOR:
                playTTS(resp.getData().getSemantic().getResponse());
                break;
            case Const.DOMAIN_OTHER:
                playTTS(resp.getData().getSemantic().getResponse());
                break;
//            case Const.DOMAIN_MODE:
//                break;
            default:
                playNoResourceTTS();
                break;
        }
    }


    private void playMusic(boxNluBean resp){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();
        String queryValue = null;
        String musicValue = null;
        String singerValue = null;
        String albumValue = null;

        for(boxNluBean.DataBean.SemanticBean.ParasBean param : params){
            if(param.getKey().equals("query")){
                queryValue = param.getValue();
            }else if(param.getKey().equals("music")){
                musicValue = param.getValue();
            }else if(param.getKey().equals("singer")){
                singerValue = param.getValue();
            }else if(param.getKey().equals("album")){
                albumValue = param.getValue();
            }
        }

        Log.d(TAG, "playMusic: musicinfo:" +queryValue +";" +musicValue + "; "+singerValue + "; "+albumValue);
        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        RequestMusic requestMusic = new RequestMusic();
        RequestMusic.KeywordsEntity keywordsEntity = new RequestMusic.KeywordsEntity();
        requestMusic.setDomain("music");

        if(!TextUtils.isEmpty(queryValue)){//随机播放

        }

        if(!TextUtils.isEmpty(musicValue)) {//歌曲
            keywordsEntity.setSong(musicValue);
        }

        if(!TextUtils.isEmpty(albumValue)) {//专辑
            keywordsEntity.setAlbum(albumValue);
        }

        if(!TextUtils.isEmpty(singerValue)){
            keywordsEntity.setSinger(singerValue);
        }

        /*else if(!TextUtils.isEmpty(musicValue)){//歌曲
            keywordsEntity.setSong(musicValue);
            if(!TextUtils.isEmpty(singerValue)){
                keywordsEntity.setSinger(singerValue);
            }
        }else if(!TextUtils.isEmpty(albumValue)){//专辑
            keywordsEntity.setAlbum(albumValue);
            if(!TextUtils.isEmpty(singerValue)){
                keywordsEntity.setSinger(singerValue);
            }
        }else*/

        requestMusic.setKeywords(keywordsEntity);

        aiApiService.getMusicContent("", requestMusic).enqueue(new Callback<ResponseMusic>() {
            @Override
            public void onResponse(Call<ResponseMusic> call, Response<ResponseMusic> response) {
                if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                    final String url = response.body().getData().getUrl();
                    Log.d(TAG, "music url :" + url);
                    if(TextUtils.isEmpty(url)){
                        playNoResourceTTS();
                    }else
                        EventBus.getDefault().post(new UrlMusicEvent(url));
                }else {
                    playNoResourceTTS();
                    Log.e(TAG, "onFailure: music:net error");
                }

            }

            @Override
            public void onFailure(Call<ResponseMusic> call, Throwable t) {
                Log.e(TAG, "onFailure: music");
                playNoResourceTTS();
            }
        });
    }
            /*if(value.equals("random")){
                AIApiService aiApiService = RetrofitApiManager.getAiApiService();
                RequestMusic requestMusic = new RequestMusic();
                requestMusic.setDomain("music");
                RequestMusic.KeywordsEntity keywordsEntity = new RequestMusic.KeywordsEntity();
                requestMusic.setKeywords(keywordsEntity);
                aiApiService.getMusicContent("", requestMusic).enqueue(new Callback<ResponseMusic>() {
                    @Override
                    public void onResponse(Call<ResponseMusic> call, Response<ResponseMusic> response) {
                        if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                            final String url = response.body().getData().getUrl();
                            Log.d(TAG, "music url :" + url);
                            EventBus.getDefault().post(new UrlMusicEvent(url));
                        }else {
                            playTTS("对不起没有找到相关资源");
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseMusic> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                        playTTS("对不起没有找到相关资源");
                    }
                });
            }*/




    /**
     * 获取新闻内容
     * @param type
     */
    private void getNewsContent(String type){
        if(TextUtils.isEmpty(type)){
            type = "头条";
        }

        RequestNews requestNews = new RequestNews();
        requestNews.setDomain("news");
        RequestNews.KeywordsBean bean = new RequestNews.KeywordsBean();
        bean.setNum("1");
        bean.setType(type);
        requestNews.setKeywords(bean);

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        aiApiService.getNewsContent("", requestNews).enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                    playTTS(response.body().getData().getNews().get(0).getContent());
                }else{
                    Log.e(TAG, "onResponse: getNewsContent,new error");
                    playNoResourceTTS();
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                playNoResourceTTS();
                Log.e(TAG, "onFailure: getNewsContent");
            }
        });
    }

    private void getLimitContent(String date, String num){
        RequestLimit requestLimit = new RequestLimit();

        requestLimit.setDomain("limit");
        RequestLimit.KeywordsBean keywordsBean = new RequestLimit.KeywordsBean();
        if(!TextUtils.isEmpty(date)){
            keywordsBean.setDate(date);
        }

        if(!TextUtils.isEmpty(num)){
            keywordsBean.setNumber(num);
        }

        requestLimit.setKeywords(keywordsBean);

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        aiApiService.getLimitContent("", requestLimit).enqueue(new Callback<ResponseLimit>() {
            @Override
            public void onResponse(Call<ResponseLimit> call, Response<ResponseLimit> response) {
                String tts = null;
                if(TextUtils.isEmpty(response.body().getData().getNumber())){ //非车牌号查询
                    tts = "限号尾号为" + response.body().getData().getLimit();
                }else{
                    if(response.body().getData().getIslimit().equalsIgnoreCase("No")){
                        tts = "该车牌不限号";
                    }else{
                        tts = "该车牌限号";
                    }
                }

                playTTS(tts);
            }

            @Override
            public void onFailure(Call<ResponseLimit> call, Throwable t) {
                playNoResourceTTS();
                Log.e(TAG, "onFailure: getLimitContent");
            }
        });
    }

    private void getWeatherInfo(String date, String city, final String intent){ //北京，青岛
        if(TextUtils.isEmpty(intent)){
            waitForWakeup();

            return;
        }

        if(TextUtils.isEmpty(city)){
            city = "青岛";
        }

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();

        if(intent.contains("质量")){ //查询空气质量
            RequestAqi aqi = new RequestAqi();
            RequestAqi.KeywordsBean keyBean = new RequestAqi.KeywordsBean();

            aqi.setDomain("aqi");
            if(!TextUtils.isEmpty(date)){
                keyBean.setDate(date);
            }

            keyBean.setCity(city);

            aqi.setKeywords(keyBean);
            aiApiService.getAqiResult("", aqi).enqueue(new Callback<ResponseAqi>() {
                @Override
                public void onResponse(Call<ResponseAqi> call, Response<ResponseAqi> response) {
                    if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                        ResponseAqi.DataBean data = response.body().getData();

                        StringBuilder builder = new StringBuilder();
                        builder.append(data.getCity());
                        builder.append("空气质量指数");
                        builder.append(data.getAqi());
                        builder.append(",");


                        builder.append("空气质量状况");
                        builder.append(data.getAqs());
                        builder.append(",");


                        if(!TextUtils.isEmpty(data.getPp())){
                            builder.append("首要污染物");
                            builder.append(data.getPp());
                            builder.append(",");
                        }

                        builder.append("PM2.5值");
                        builder.append(data.getPm25());
                        builder.append(",");

                        String tts = builder.toString();
                        Log.d(TAG, "onResponse: aqi:" + tts);
                        playTTS(tts);
                    }else{
                        playNoResourceTTS();
                        Log.e(TAG, "onResponse: getWeatherInfo: net error");
                    }
                }

                @Override
                public void onFailure(Call<ResponseAqi> call, Throwable t) {
                    playNoResourceTTS();
                    Log.e(TAG, "onFailure: getWeatherInfo");
                }
            });

        }else{ //天气预报
            RequestWeather requestWeather = new RequestWeather();
            RequestWeather.KeywordsBean keywordsBean = new RequestWeather.KeywordsBean();

            requestWeather.setDomain("weather");
            keywordsBean.setCity(city);
            if(TextUtils.isEmpty(date)){
                keywordsBean.setDate("");
            }else if(date.equals("今天")){
                keywordsBean.setDate("");
            }else if(date.equals("明天")){

            }else if(date.equals("后天")){

            }

            requestWeather.setKeywords(keywordsBean);
            aiApiService.getWeatherInfo("", requestWeather).enqueue(new Callback<ResponseWeather>() {
                @Override
                public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                    if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                        ResponseWeather.DataBean data = response.body().getData();

                        StringBuilder builder = new StringBuilder();
                        if(intent.contains("天气查询")) {
                            builder.append(data.getCity());
                            builder.append("温度");
                            builder.append(data.getTemperature());
                            builder.append("摄氏度,");

                            builder.append("湿度");
                            builder.append(data.getHumidity());
                            builder.append(",");

                            builder.append(data.getWinddirect());
                            builder.append(data.getWindpower());
                            builder.append(",");

                            builder.append(data.getZiwanxian());
                            builder.append(",");
                        }else if(intent.contains("穿衣指数")){
                            builder.append("穿衣指数");
                            builder.append(data.getChuanyi());
                            builder.append(",");
                        } else if(intent.contains("洗车指数")){
                            builder.append("洗车指数");
                            builder.append(data.getXiche());
                            builder.append(",");
                        } else if(intent.contains("运动指数")){
                            builder.append("运动指数");
                            builder.append(data.getYundong());
                            builder.append(",");
                        }else if(intent.contains("感冒指数")){
                            builder.append("感冒指数");
                            builder.append(data.getGanmao());
                            builder.append(",");
                        }else if(intent.contains("空调指数")){
                            builder.append("空调指数");
                            builder.append(data.getKongtiao());
                            builder.append(",");
                        }else if(intent.contains("污染指数")){
                            builder.append("污染指数");
                            builder.append(data.getWuran());
                            builder.append(",");
                        }



                        String ttsWeather = builder.toString();
                        Log.d(TAG, "onResponse: ttsweather:" + ttsWeather);
                        playTTS(ttsWeather);
                    }else{
                        playNoResourceTTS();
                        Log.e(TAG, "onFailure: getWeatherInfo(not aqi), net error");
                    }
                }

                @Override
                public void onFailure(Call<ResponseWeather> call, Throwable t) {
                    Log.e(TAG, "onFailure: weather");
                    playNoResourceTTS();
                }
            });
        }
    }

    private void handlerAlarmEvent(boxNluBean resp){
        String clock = null; //下午
        String day = null;   //明天
        String time = null;  //2点
        String event = null; //开会，睡觉
        String hour = null;
        String minute = null;
        String second = null;

        String program = null;  //收看电视节目 天下足球，新闻联播
        String response = resp.getData().getSemantic().getResponse();
        playTTS(response);
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();

        for(boxNluBean.DataBean.SemanticBean.ParasBean param : params){
            if(param.getKey().equalsIgnoreCase("clock")){
                clock = param.getValue();
            }

            if(param.getKey().equalsIgnoreCase("day")){
                day = param.getValue();
            }

            if(param.getKey().equalsIgnoreCase("time")){
                time = param.getValue();
            }

            if(param.getKey().equalsIgnoreCase("event")){
                event = param.getValue();
            }

            if(param.getKey().equalsIgnoreCase("hour")){
                hour = param.getValue();
            }

            if(param.getKey().equalsIgnoreCase("minute")){
                minute = param.getValue();
            }

            if(param.getKey().equalsIgnoreCase("second")){
                second = param.getValue();
            }

            if(param.getKey().equalsIgnoreCase("program")){
                program = param.getValue();
            }
        }

        if(!TextUtils.isEmpty(program)){//收看电视节目
            playTTS("闹钟已为您设置好");
            return;
        }
        //分为闹钟及提醒
        if(TextUtils.isEmpty(event)){//闹钟
            //// TODO: 17-2-16 现仅支持整点闹钟
            if (TextUtils.isEmpty(time)){
                playTTS(Const.TTS_REPLY_ERROR);
                return;
            }
        }else{ //提醒
            int seconds = 0;
            if(!TextUtils.isEmpty(hour)){
                seconds += Integer.parseInt(hour)*60*60;
            }

            if(!TextUtils.isEmpty(minute)){
                seconds += Integer.parseInt(minute) * 60;
            }

            if(!TextUtils.isEmpty(second)){
                seconds += Integer.parseInt(second);
            }

            Log.d(TAG, "handlerAlarmEvent: seconds=" + seconds);
            final String ttsEvent = event;

            if(seconds > 0){
                scheduledThreadPool.schedule(new Runnable() {

                    @Override
                    public void run() {
                        String tts = ttsEvent + "时间到了，请注意时间";
                        playTTS(tts);
                    }
                }, seconds, TimeUnit.SECONDS);
            }
        }
    }

    private void handlerWeather(boxNluBean resp){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();
        String intent = resp.getData().getSemantic().getIntent();
        String date = null;
        String city = null;

        if(params != null){
            for(boxNluBean.DataBean.SemanticBean.ParasBean param : params){
                if(param.getKey().equals("day")){
                    date = param.getValue();
                }

                if(param.getKey().equals("loc")){
                    city = param.getValue();
                }
            }


        }
        getWeatherInfo(date, city, intent);
    }
    /**
     * 油价
     * @param resp
     */
    private final String DEFALUT_OIL_CITY = "北京";//北京，山东
    private void handlerOilEvent(boxNluBean resp){
        final RequestOilprice requestOilprice = new RequestOilprice();
        requestOilprice.setDomain("oilprice");
        RequestOilprice.KeywordsBean keywordsBean = new RequestOilprice.KeywordsBean();
        keywordsBean.setArea(DEFALUT_OIL_CITY);
        keywordsBean.setType("");
        requestOilprice.setKeywords(keywordsBean);

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        aiApiService.getOilPrice("", requestOilprice).enqueue(new Callback<ResponseOilprice>() {
            @Override
            public void onResponse(Call<ResponseOilprice> call, Response<ResponseOilprice> response) {
                Log.d(TAG, "onResponse: oil:" + response.body());
                if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                    StringBuilder ttsOil = new StringBuilder();
                    List<ResponseOilprice.DataBeanX.DataBean> list = response.body().getData().getData();

                    for(ResponseOilprice.DataBeanX.DataBean bean : list){
                        ttsOil.append(bean.getType());
                        ttsOil.append(bean.getPrice());
                        ttsOil.append("元，");
                    }

                    String tts = ttsOil.toString();
                    Log.d(TAG, "onResponse: oilprice:" + tts);
                    playTTS(tts);
                }else{
                    playNoResourceTTS();
                    Log.e(TAG, "onFailure: handlerOilEvent:net error");
                }
            }

            @Override
            public void onFailure(Call<ResponseOilprice> call, Throwable t) {
                Log.e(TAG, "onFailure: handlerOilEvent");
                playNoResourceTTS();
            }
        });
    }

    private void HandlerStock(boxNluBean resp){
        //1.大盘指数 2.个股
        String queryValue = null;
        String shareValue = null;

        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();

        for(boxNluBean.DataBean.SemanticBean.ParasBean param : params) {
            if (param.getKey().equals("query")) {
                queryValue = param.getValue();
            }

            if(param.getKey().equals("shares")){
                shareValue = param.getValue();
            }
        }

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        RequestStock stock = new RequestStock();
        stock.setDomain("stock");
        RequestStock.KeywordsBean keyworksBean = new RequestStock.KeywordsBean();
        if(!TextUtils.isEmpty(queryValue)){
            keyworksBean.setType("0");//0代表上证指数，1代表深证指数
            keyworksBean.setGid("");
            stock.setKeywords(keyworksBean);


            aiApiService.getStockInfo("", stock).enqueue(new Callback<ResponseStock>() {
                @Override
                public void onResponse(Call<ResponseStock> call, Response<ResponseStock> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                    if(Const.RET_CODE_SUCESS.equals(response.body().getRetCode())){
                        ResponseStock.DataBean resp = response.body().getData();

                        StringBuilder ttsStock = new StringBuilder();
                        ttsStock.append(resp.getName());
                        ttsStock.append("当前指数");
                        ttsStock.append(resp.getNowpri());
                        ttsStock.append(",");

                        ttsStock.append("开盘指数");
                        ttsStock.append(resp.getOpenPri());
                        ttsStock.append(",");

                        ttsStock.append("最高点");
                        ttsStock.append(resp.getHighPri());
                        ttsStock.append(",");

                        ttsStock.append("最低点");
                        ttsStock.append(resp.getLowpri());
                        ttsStock.append(",");

                        Log.d(TAG, "ttsstock:" + ttsStock.toString());
                        playTTS(ttsStock.toString());
                    }


                }

                @Override
                public void onFailure(Call<ResponseStock> call, Throwable t) {
                    Log.e(TAG, "onFailure: HandlerStock");
                    playNoResourceTTS();
                }
            });
        }

        if(!TextUtils.isEmpty(shareValue)){
            keyworksBean.setType("");//0代表上证指数，1代表深证指数
            keyworksBean.setGid(shareValue);
            stock.setKeywords(keyworksBean);

            aiApiService.getStockInfo1("", stock).enqueue(new Callback<ResponseStock1>() {
                @Override
                public void onResponse(Call<ResponseStock1> call, Response<ResponseStock1> response) {
                    if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)) {
                        ResponseStock1.DataBean data = response.body().getData();

                        StringBuilder builder = new StringBuilder();
                        builder.append(data.getName());
                        builder.append("当前价格");
                        builder.append(data.getNowPic());
                        builder.append(",");

                        builder.append("开盘价");
                        builder.append(data.getTodayStartPri());
                        builder.append(",");

                        builder.append("今日最低价");
                        builder.append(data.getTodayMin());
                        builder.append(",");

                        builder.append("今日最高价");
                        builder.append(data.getTodayMax());
                        builder.append(",");

                        builder.append("成交量");
                        builder.append(data.getTraNumber());
                        builder.append(",");

                        builder.append("成交金额");
                        builder.append(data.getTraAmount());
                        builder.append(",");

                        String tts = builder.toString();
                        Log.d(TAG, "onResponse: stock1 tts:" + tts);
                        playTTS(tts);
                    }
                }

                @Override
                public void onFailure(Call<ResponseStock1> call, Throwable t) {
                    Log.e(TAG, "onFailure: HandlerStock1");
                    playTTS("没有查找到");
                }
            });
        }





    }

    private void HandlerTransfer(boxNluBean resp){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();

        for(boxNluBean.DataBean.SemanticBean.ParasBean param : params){
            if(param.getKey().equals("transfer")){
                String value = param.getValue();
                if(TextUtils.isEmpty(value)){
                    playTTS("对不起我没听清楚");
                }else{
                    RequestTrans requestTrans = new RequestTrans();
                    RequestTrans.KeywordsBean keyBean = new RequestTrans.KeywordsBean();

                    requestTrans.setDomain("translation");
                    keyBean.setFrom("auto");
                    keyBean.setTo("en");
                    keyBean.setQuery(value);

                    requestTrans.setKeywords(keyBean);
                    AIApiService aiApiService = RetrofitApiManager.getAiApiService();
                    aiApiService.getTransferResult("", requestTrans).enqueue(new Callback<ResponseTrans>() {
                        @Override
                        public void onResponse(Call<ResponseTrans> call, Response<ResponseTrans> response) {
                            if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                                String result = response.body().getData().getResult();
                                if(TextUtils.isEmpty(result)){
                                    playNoResourceTTS();
                                }else{
                                    playTTS(result);
                                }
                            }else{
                                playNoResourceTTS();
                                Log.e(TAG, "onResponse: HandlerTransfer: net error");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseTrans> call, Throwable t) {
                            Log.e(TAG, "onFailure: HandlerTransfer");
                            playNoResourceTTS();
                        }
                    });
                }
            }
        }
    }

    private void HandlerHolidayQuery(boxNluBean resp){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();
        if(params != null){
            if(params.get(0).getKey().equals("holiday")){
                String value = params.get(0).getValue();

                if(TextUtils.isEmpty(value)){
                    playTTS("对不起我没听清楚");
                }else{
                    RequestHoliday holiday = new RequestHoliday();
                    RequestHoliday.KeywordsBean keyBean = new RequestHoliday.KeywordsBean();

                    holiday.setDomain("holiday");
                    keyBean.setType(value);
                    holiday.setKeywords(keyBean);

                    AIApiService aiApiService = RetrofitApiManager.getAiApiService();
                    aiApiService.getHolidayInfo("", holiday).enqueue(new Callback<ResponseHoliday>() {
                        @Override
                        public void onResponse(Call<ResponseHoliday> call, Response<ResponseHoliday> response) {
                            if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                                playTTS(response.body().getData().getDesc());
                            }else {
                                Log.e(TAG, "onFailure: HandlerHolidayQuery: net error");
                                playNoResourceTTS();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseHoliday> call, Throwable t) {
                            Log.e(TAG, "onFailure: HandlerHolidayQuery");
                            playNoResourceTTS();
                        }
                    });
                }
            }
        }
    }

    private void HandlerHotline(boxNluBean resp){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();
        if(params != null){
            if(params.get(0).getKey().equals("number")){
                String value = params.get(0).getValue();
                if(TextUtils.isEmpty(value)){
                    playNoResourceTTS();
                }else{
                    RequestHotline hotline = new RequestHotline();
                    RequestHotline.KeywordsBean keyBean = new RequestHotline.KeywordsBean();

                    hotline.setDomain("hotline");
                    keyBean.setName(value);
                    hotline.setKeywords(keyBean);

                    AIApiService aiApiService = RetrofitApiManager.getAiApiService();
                    aiApiService.getHotlineInfo("", hotline).enqueue(new Callback<ResponseHotline>() {
                        @Override
                        public void onResponse(Call<ResponseHotline> call, Response<ResponseHotline> response) {
                            if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                                String tts = response.body().getData().getName() + response.body().getData().getPhone();
                                playTTS(tts);
                            }else {
                                playNoResourceTTS();
                                Log.e(TAG, "onResponse: HandlerHotline: net error");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseHotline> call, Throwable t) {
                            playNoResourceTTS();
                            Log.e(TAG, "onFailure: HandlerHotline");
                        }
                    });

                }
            }

        }
    }

    private void HandlerMovie(boxNluBean resp){
        //选前5部电影名称播报
        RequestMovie requestMovie = new RequestMovie();
        RequestMovie.KeywordsBean keywordsBean = new RequestMovie.KeywordsBean();

        requestMovie.setDomain("movies");
        keywordsBean.setCity("北京");
        requestMovie.setKeywords(keywordsBean);

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        aiApiService.getMovieInfo("", requestMovie).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                    ResponseMovie.DataBean data = response.body().getData();
                    if(data == null){
                        playNoResourceTTS();
                        return;
                    }

                    StringBuilder builder = new StringBuilder();
                    List<ResponseMovie.DataBean.ClassificationsBean> classList = data.getClassifications();
                    for(ResponseMovie.DataBean.ClassificationsBean bean : classList){
                        builder.append(bean.getName());
                        List<ResponseMovie.DataBean.ClassificationsBean.MoviesBean> movies = bean.getMovies();
                        for(ResponseMovie.DataBean.ClassificationsBean.MoviesBean movie : movies){
                            builder.append(movie.getTitle());
                            builder.append(",");
                        }
                    }

                    String ttsMovie = builder.toString();
                    Log.d(TAG, "onResponse: movies:" + ttsMovie);
                    playTTS(ttsMovie);
                }else {
                    playNoResourceTTS();
                    Log.e(TAG, "onFailure: HandlerMovie: net error");
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                playNoResourceTTS();
                Log.e(TAG, "onFailure: HandlerMovie");
            }
        });
    }

    private void HandlerXimalaya(boxNluBean resp){
        String intent = resp.getData().getSemantic().getIntent();
        String queryValue = null;

        if(TextUtils.isEmpty(intent)){
            playTTS("没有相关领域资源");
            return;
        }

        if(!intent.equals("相声") && !intent.equals("儿歌")){
            playTTS("没有相关领域资源");
            return;
        }

        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();
        if(params.get(0).getKey().equals("query")){
            if(intent.equals("相声")){
                queryValue = "来一段相声";
            }else if(intent.equals("儿歌")){
                queryValue = "来一首儿歌";
            }
        }else if(params.get(0).getKey().equals("name")){
            queryValue = params.get(0).getValue();
        }

        RequestXimalaya ximalaya = new RequestXimalaya();
        RequestXimalaya.KeywordsBean keyBean = new RequestXimalaya.KeywordsBean();
        ximalaya.setDomain("ximalaya");
        keyBean.setType(intent);
        keyBean.setNum("1");
        keyBean.setQuery(queryValue);
        ximalaya.setKeywords(keyBean);

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        aiApiService.getXimalayaInfo("", ximalaya).enqueue(new Callback<ResponseXimalaya>() {
            @Override
            public void onResponse(Call<ResponseXimalaya> call, Response<ResponseXimalaya> response) {
                if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                    String url = response.body().getData().getTracks().get(0).getUrl();
                    if(TextUtils.isEmpty(url)){
                        playNoResourceTTS();
                    }else{
                        EventBus.getDefault().post(new UrlMusicEvent(url));
                    }

                }else {
                    playNoResourceTTS();
                    Log.e(TAG, "onResponse: HandlerXimalaya: net error");
                }
            }

            @Override
            public void onFailure(Call<ResponseXimalaya> call, Throwable t) {
                playNoResourceTTS();
                Log.e(TAG, "onFailure: HandlerXimalaya");
            }
        });
    }

    private void HandlerContellation(boxNluBean resp){
        String date = "today";  //默认今天
        String constellation = null;

        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();
        if(params != null){
            for(boxNluBean.DataBean.SemanticBean.ParasBean param : params){
                if(param.getKey().equals("constellation")){
                    constellation = param.getValue();
                }
            }

            Log.d(TAG, "HandlerContellation: constellation:" + constellation);
            if(TextUtils.isEmpty(constellation)){
                playTTS("我没听清楚您说的");
            }else{
                RequestConstellation rest = new RequestConstellation();
                RequestConstellation.KeywordsBean keyBean = new RequestConstellation.KeywordsBean();
                rest.setDomain("constellation");
                keyBean.setType(date);
                keyBean.setConsName(constellation);
                rest.setKeywords(keyBean);

                AIApiService aiApiService = RetrofitApiManager.getAiApiService();
                aiApiService.getConstellation("", rest).enqueue(new Callback<ResponseConstellation>() {
                    @Override
                    public void onResponse(Call<ResponseConstellation> call, Response<ResponseConstellation> response) {
                        if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                            ResponseConstellation.DataEntity data = response.body().getData();
                            String tts = null;

                            if(data != null){
                                StringBuilder builder = new StringBuilder();
                                builder.append(data.getName());
                                builder.append("综合指数");
                                builder.append(data.getAll());
                                builder.append(",");

                                builder.append("幸运色");
                                builder.append(data.getColor());
                                builder.append(",");

                                builder.append("健康指数");
                                builder.append(data.getHealth());
                                builder.append(",");

                                builder.append("爱情指数");
                                builder.append(data.getLove());
                                builder.append(",");

                                builder.append("财运指数");
                                builder.append(data.getMoney());
                                builder.append(",");

                                builder.append("幸运数字");
                                builder.append(data.getNumber());
                                builder.append(",");

                                builder.append("速配星座");
                                builder.append(data.getQFriend());
                                builder.append(",");

                                builder.append("工作指数");
                                builder.append(data.getWork());
                                builder.append(",");

                                builder.append("今日运势概述");
                                builder.append(data.getSummary());
                                builder.append(",");

                                tts = builder.toString();
                                Log.d(TAG, "onResponse: HandlerContellation :" + tts);
                            }

                            playTTS(tts);
                        }else {
                            Log.e(TAG, "onResponse: HandlerContellation: net error");
                            playNoResourceTTS();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseConstellation> call, Throwable t) {
                        playNoResourceTTS();
                        Log.e(TAG, "onFailure: HandlerContellation");
                    }
                });
            }


        }
    }

    private void HandlerCalendar(){
        final RequestCalendar requestCalendar = new RequestCalendar();
        RequestCalendar.KeywordsBean keywordsBean = new RequestCalendar.KeywordsBean();
        requestCalendar.setDomain("calendar");
        requestCalendar.setKeywords(keywordsBean);

        AIApiService aiApiService = RetrofitApiManager.getAiApiService();
        aiApiService.getCalendarInfo("", requestCalendar).enqueue(new Callback<ResponseCalendar>() {
            @Override
            public void onResponse(Call<ResponseCalendar> call, Response<ResponseCalendar> response) {
                if(response.body().getRetCode().equals(Const.RET_CODE_SUCESS)){
                    ResponseCalendar.DataBean data = response.body().getData();
                    StringBuilder builder = new StringBuilder();
                    builder.append(data.getLunarYear());
                    builder.append(data.getLunar());
                    String tts = builder.toString();
                    Log.d(TAG, "onResponse: HandlerCalendar" + tts);
                    playTTS(tts);

                }else {
                    playNoResourceTTS();
                    Log.e(TAG, "onFailure: HandlerCalendar: net error");
                }
            }

            @Override
            public void onFailure(Call<ResponseCalendar> call, Throwable t) {
                Log.e(TAG, "onFailure: HandlerCalendar");
                playNoResourceTTS();
            }
        });
    }
    /**
     * 当前日期
     */
    private void HandlerDayEvent(){
        String day = MusicPlayerManager.getInstance().getTodayDate();
        playTTS(day);
    }

    /**
     * 当前星期
     */
    private void HandlerWeekEvent(){
        String week = MusicPlayerManager.getInstance().getTodayWeek();
        playTTS(week);
    }

    private void handlerSingleCommand(boxNluBean resp){
        List<boxNluBean.DataBean.SemanticBean.ParasBean> params = resp.getData().getSemantic().getParas();
        String operands = resp.getData().getSemantic().getDomain();
        String operator = null;
        String value = null;

        if (params.size() == 1) {
            operator = params.get(0).getKey();
            value = params.get(0).getValue();
        }

        ProtocolManager.getInstance().setProtocolInfo(operands, operator, value, false);
        ProtocolManager.getInstance().convertVoice2Data();
        //test
        //playTTS("好的");
    }

    public void showTtsResult(String tts){
        if(TextUtils.isEmpty(tts)){
            EventBus.getDefault().post(new NluEvent("语义理解错误"));
            waitForWakeup();
        }else {
            EventBus.getDefault().post(new NluEvent(tts));
            //stopTTS();
            playTTS(tts);
            //playTTS_api_l(tts, RespListener);
        }
    }

    private MediaPlayer.OnCompletionListener RespListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {
            waitForWakeup();
        }
    };

    /**
     * 未查找到资源播放tts
     */
    private void playNoResourceTTS(){
        Random random=new Random();
        int index = random.nextInt(3);
        String tts = null;

        switch (index){
            case 0:
                tts = Const.TTS_REPLY_NO_RESOURCE1;
                break;
            case 1:
                tts = Const.TTS_REPLY_NO_RESOURCE2;
                break;
            case 2:
                tts = Const.TTS_REPLY_NO_RESOURCE3;
                break;
            default:
                tts = Const.TTS_REPLY_NO_RESOURCE1;
                break;
        }

        playTTS(tts);
    }
}
