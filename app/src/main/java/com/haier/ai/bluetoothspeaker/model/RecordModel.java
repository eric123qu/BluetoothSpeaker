package com.haier.ai.bluetoothspeaker.model;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.haier.ai.bluetoothspeaker.App;
import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.audio.AudioInput;
import com.haier.ai.bluetoothspeaker.audio.AudioOutput;
import com.haier.ai.bluetoothspeaker.bean.box.boxNluBean;
import com.haier.ai.bluetoothspeaker.event.DialogEvent;
import com.haier.ai.bluetoothspeaker.event.ErrorEvent;
import com.haier.ai.bluetoothspeaker.event.NluEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeResultEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeStatusEvent;
import com.haier.ai.bluetoothspeaker.event.StartRecordEvent;
import com.haier.ai.bluetoothspeaker.manager.ProtocolManager;
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
    @IntDef({TYPE_AIR, TYPE_FRIDGE}) @interface ControlType{}


    public static RecordModel getInstance(){
        if(sRecordModel == null){
            sRecordModel = new RecordModel();
        }

        return sRecordModel;
    }

    public RecordModel(){
        // 初始化SDK。可选的返回值通过ErrorCode获取。
        initSdk();

        initNet();

        //add 初始化录音机
        initRecorder();
    }

    public void initNet(){
        if(httpUtils == null){
            httpUtils = new HttpUtils();
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
        String config = null;
        if ((err = mRecorder.start(config)) != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "startRecord: fail to set audio source filter: " + err);
            return;
        }

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
                //sendReReconizeEvent(true);
                waitForWakeup();
            }

            @Override
            public void onResult(int paramInt, String paramString) {
                // 返回识别结果。
                final String msg = String.format("onResult(): errcode = %d, msg = %s", paramInt, paramString);
                String asrResult = getAsrResult(paramString);
                //// TODO: 16-9-29 状态显示语音识别成功
                EventBus.getDefault().post(new ReconizeStatusEvent("识别结束"));
                //waitForWakeup();

                Log.e(TAG, "onResult: asrresult:" + asrResult );
                if(TextUtils.isEmpty(asrResult)){
                    EventBus.getDefault().post(new ReconizeResultEvent("语音接口返回识别错误状态"));

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
        INlu nlu = UbicAI.createNlu(null);

        // 创建语义理解回调函数。
        INluCallback cb = new INluCallback() {

            @Override
            public void onError(int arg0, String arg1) {
                final String msg = String.format("nlu onError(): errcode = %d, msg = %s", arg0, arg1);
                EventBus.getDefault().post(new ReconizeResultEvent("语义理解错误"));
                //sendReReconizeEvent(true);
                waitForWakeup();
            }

            @Override
            public void onResult(int arg0, String arg1) {
                // 返回识别结果。
                final String msg = String.format("nlu onResult(): errcode = %d, msg = %s", arg0, arg1);
                Log.d(TAG, "onResult: nlu:" + msg);

                /* String tts = parseNluResult(arg1);

                if(TextUtils.isEmpty(tts)){
                    EventBus.getDefault().post(new NluEvent("语义理解错误"));
                    waitForWakeup();
                }else */

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
        nlu.attach(cb);

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

        return String.format("{\"LIBaseinfo\":{\"category\":" + "\"" +
                sType + "\"" +
                ",\"city\":\"青岛\",\"contextid\":\"123456\",\"latitude\":12.8," +
                "\"longitude\":123.5,\"query\":" + "\"" +
                query + "\"" +
                ",\"region\":\"李沧区\"},\"devices\":[{\"attrs\":{\"brand\":\"天意\",\"model\":\"蔚蓝\"},\"deviceModules\":[{\"moduleId\":\"C8934644F82\",\"moduleType\":\"wifimodule\"}],\"id\":\"C8934644F82\",\"name\":\"客厅空调\",\"typeInfo\":{\"typeId\":\"00000000000000008080000000041410\"}}],\"devicestate\":{\"curhumidity\":67,\"curtemp\":28,\"mode\":\"自动\",\"settemp\":26,\"windspeed\":2}}");
    }

   /* private String parseNluResult(String msg){
        if(TextUtils.isEmpty(msg)){
            return null;
        }

        //根据nlu返回生成tts播报内容
        return SpeechJavaBeanUtils.parseAirSpeechString(SpeechJavaBeanUtils.parseAirControl(msg));
    }*/


    public void playTTS(String content){
        if(TextUtils.isEmpty(content)){
            return;
        }

        if (mPlayer == null)
        {
            initPlayer();
        }

        int err;
        //String config = null;
        if ((err = mPlayer.start(null)) != ErrorCode.UAI_ERR_NONE)
        {
            Log.e(TAG, "playTTS: " +  String.format("tts player start failed = %d", err));
        }

        mPlayer.play(content, TtsPlayerMode.TTS_PLAYER_MODE_NEW);

        Log.d(TAG, "playTTS: tts player start end");

        waitForWakeup();
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

        if(isDialog){
            //// TODO: 17-2-14 根据response 播放提示音，开启录音
            EventBus.getDefault().post(new DialogEvent("new dialog"));
        }else {
            handlerSingleCommand(resp);
        }
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
        playTTS("好的");
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
}
