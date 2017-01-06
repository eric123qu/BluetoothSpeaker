package com.haier.ai.airobot.model;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.haier.ai.airobot.Const;
import com.haier.ai.airobot.MyApplication;
import com.haier.ai.airobot.bean.food.FResponseBean;
import com.haier.ai.airobot.bean.food.FoodBean;
import com.haier.ai.airobot.bean.food.Para;
import com.haier.ai.airobot.bean.food.Result;
import com.haier.ai.airobot.data.FoodDbManager;
import com.haier.ai.airobot.event.ErrorEvent;
import com.haier.ai.airobot.event.NluEvent;
import com.haier.ai.airobot.event.ReconizeResultEvent;
import com.haier.ai.airobot.event.ReconizeStatusEvent;
import com.haier.ai.airobot.event.StartRecordEvent;
import com.haier.ai.airobot.manager.LocalMediaManager;
import com.haier.ai.airobot.net.RetrofitRequest;
import com.haier.ai.airobot.utils.NetWorkUtils;
import com.haier.ai.airobot.utils.SpeechJavaBeanUtils;
import com.haierubic.ai.AudioInput;
import com.haierubic.ai.AudioOutput;
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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author: qu
 * date: 16-8-30
 * introduce: 采用两种方式，一种是API方式，另一种是SDK方式，以下有注释
 */
public class RecordModel{
    private final String TAG = "RecordModel";
    private static final int TYPE_AIR = 0;
    private static final int  TYPE_BRIDGE = 1;
    private static RecordModel sRecordModel;
    private static IAsrRecorder mRecorder;
    private static ITtsPlayer mPlayer;
    private static HttpUtils httpUtils;
    @IntDef({TYPE_AIR, TYPE_BRIDGE}) @interface ControlType{}


    public static RecordModel getInstance(){
        if(sRecordModel == null){
            sRecordModel = new RecordModel();
        }

        return sRecordModel;
    }

    public RecordModel(){
        // 初始化SDK。可选的返回值通过ErrorCode获取。
        //initSdk();

        initNet();
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

        //log("start recorder begin.");
        if (mRecorder == null) {
            // 初始化录音机
            initRecorder();
        }

        int err;
        // 录音识别。
        // 通过start()方法开始录音识别。stop()方法结束并进行录音识别。cancel()方法结束不进行识别。
        // 目前只支持主动调用stop()接口的识别，即按住说。其它识别方式开发中。
        if ((err = mRecorder.start(null)) != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "startRecord: fail to set audio source filter: " + err);
            mRecorder = null;
            //UbicAI.release();
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

    private void initGetToken(String token) {
        /**
         * 确保app刚安装上，token为空的时候，去请求token,token长度为30
         */
        if (TextUtils.isEmpty(token) || token.length() != 30) {
//            NetRequest.getInstance().getToken();
            if (NetWorkUtils.isNetworkConnected(MyApplication.getInstance().mContext)){
                RetrofitRequest.getInstance().getToken();
            }else{
                Log.e(TAG, "initGetToken: 您的网络未连接，请连接网络，或您的网络无法连接外网");
            }
        }
    }

    public void sendReReconizeEvent(boolean bReReconize){
        StartRecordEvent event = new StartRecordEvent(bReReconize);
        EventBus.getDefault().post(event);
    }

    private void initSdk(){
        int err = UbicAI.init(null);
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
            //UbicAI.release();
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
                waitForWakeup();

                if(TextUtils.isEmpty(asrResult)){
                    EventBus.getDefault().post(new ReconizeResultEvent("语音接口返回识别错误状态"));

                }else {
                    EventBus.getDefault().post(new ReconizeResultEvent(asrResult));
                    //调用nlu接口，语义理解(空调)
                    /*String nlu = formatNluRequest(asrResult, TYPE_AIR);
                    getNluResult(nlu);*/

                    //冰箱
                    getNlpResult(asrResult);
                }
            }
        };

        // 设置识别状态回调。
        // 使用attach接口的可以支持多个回调的定义。使用结束后通过detach接口取消设置。
        // 回调函数目前与录音线程在一起，应用层应当尽快返回。
        if ((err = mRecorder.attach(callback)) != ErrorCode.UAI_ERR_NONE) {
            Log.d(TAG, "initRecorder: fail to set audio source filter: " + err);
            mRecorder = null;
            //UbicAI.release();
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

    public void getNluResult(String asrData){
        // 创建语义理解对象。
        INlu nlu = UbicAI.createNlu(null);

        // 创建语义理解回调函数。
        INluCallback cb = new INluCallback() {

            @Override
            public void onError(int arg0, String arg1) {
                final String msg = String.format("nlu onError(): errcode = %d, msg = %s", arg0, arg1);
                EventBus.getDefault().post(new ReconizeResultEvent("语义理解错误"));
                sendReReconizeEvent(true);
                /*mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        log(msg);
                    }
                });*/
            }

            @Override
            public void onResult(int arg0, String arg1) {
                // 返回识别结果。
                final String msg = String.format("nlu onResult(): errcode = %d, msg = %s", arg0, arg1);
                Log.d(TAG, "onResult: nlu:" + msg);
                String tts = parseNluResult(arg1);

                if(TextUtils.isEmpty(tts)){
                    EventBus.getDefault().post(new NluEvent("语义理解错误"));
                    waitForWakeup();
                }else {
                    EventBus.getDefault().post(new NluEvent(tts));
                    //playTTS(tts);
                    playTTS_api(tts);
                }
                /*mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        log(msg);
                    }
                });*/
            }

        };

        // 绑定语义理解回调函数。
        nlu.attach(cb);

        // 开始识别。保留参数用于识别配置，目前不用填。
        nlu.start(null);

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
            case TYPE_BRIDGE:
                sType = "冰箱";
                break;
        }

        return String .format("{\"LIBaseinfo\":{\"category\":" + "\"" +
                sType + "\"" +
                ",\"city\":\"青岛\",\"contextid\":\"123456\",\"latitude\":12.8," +
                "\"longitude\":123.5,\"query\":" + "\"" +
                query + "\"" +
                ",\"region\":\"李沧区\"},\"devices\":[{\"attrs\":{\"brand\":\"天意\",\"model\":\"蔚蓝\"},\"deviceModules\":[{\"moduleId\":\"C8934644F82\",\"moduleType\":\"wifimodule\"}],\"id\":\"C8934644F82\",\"name\":\"客厅空调\",\"typeInfo\":{\"typeId\":\"00000000000000008080000000041410\"}}],\"devicestate\":{\"curhumidity\":67,\"curtemp\":28,\"mode\":\"自动\",\"settemp\":26,\"windspeed\":2}}");
    }

    private String parseNluResult(String msg){
        if(TextUtils.isEmpty(msg)){
            return null;
        }

        //根据nlu返回生成tts播报内容
        return SpeechJavaBeanUtils.parseAirSpeechString(SpeechJavaBeanUtils.parseAirControl(msg));
    }


    public void playTTS_api(String tts){
        if(!TextUtils.isEmpty(tts)) {
            RecordModel_bak.getInstance().T2SRequestForSemantics(tts);
        }

        waitForWakeup();
    }

    private void playTTS_api_l(String tts, MediaPlayer.OnCompletionListener listener){
        if(!TextUtils.isEmpty(tts)) {
            RecordModel_bak.getInstance().TTSWithStatus(tts, listener);
        }
    }

    public void playTTS(String content){
        if(TextUtils.isEmpty(content)){
            return;
        }

        if (mPlayer == null)
        {
            initPlayer();
        }

        int err;
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
        MyApplication.getInstance().sendBroadcast(intent);

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


    //////////////////////////////////////////////////////////////////
    private final String base_url = "http://121.41.27.164:8888/aiTools/refridgeSemanticsQueryInfo?input=";
    public void getNlpResult(String param){
        String result = null;
        //waitForWakeup();

        if(TextUtils.isEmpty(param)){
            return;
        }

        initNet();

        String url = base_url + param;
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (responseInfo.result != null) {
                            Log.i(TAG, "onSuccess: result" + responseInfo.result);
                            Gson gson = new Gson();
                            FResponseBean resp = gson.fromJson(responseInfo.result, FResponseBean.class);
                            parseNlpResult(resp);
                        }

                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.e("网络连接失败,错误码--" + error.getExceptionCode() + "--错误信息：--" + msg);
                    }
                });
    }

    private void parseNlpResult(FResponseBean resp){
        fixResponseData(resp);

        nlpControl(resp);
    }

    private void fixResponseData(FResponseBean resp){
        Result result = resp.getResults().get(0);
        List<Para> params = result.getParas();

        for (Para param:params) {
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

    private static String song = null;
    private static String singer = null;
    private void nlpControl(FResponseBean resp){
        Result result = resp.getResults().get(0);
        List<Para> params = result.getParas();
        String response = result.getResponse();
        String domain = result.getDomain();
        String tts = null;


        if(TextUtils.isEmpty(domain)){
            showTtsResult(response);
            return;
        }

        if(domain.equals("Common_Music")){
            //String song = null;
//            String singer = null;

            for (Para param:params) {
                if(param.getKey().equals("music")){
                    song = param.getValue();
                }else if(param.getKey().equals("singer")){
                    singer = param.getValue();
                }
            }

            if(TextUtils.isEmpty(song) && TextUtils.isEmpty(singer)){
                showTtsResult("对不起，我没能理解您在说什么。");
                return;
            }

            StringBuilder nlp = new StringBuilder();
            if(!TextUtils.isEmpty(song)){
                nlp.append("歌曲：");
                nlp.append(song);
            }

            if(!TextUtils.isEmpty(singer)){
                nlp.append("   歌手： ");
                nlp.append(singer);
            }

            EventBus.getDefault().post(new NluEvent(nlp.toString()));
            //modify
            waitForWakeup();
            playTTS_api_l(nlp.toString(), new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    LocalMediaManager.getInstance().playMusicMedia(song, singer);
                    song = null;
                    singer = null;
                }
            });


        }else if(domain.equals("Common_Joke")){
            showTtsResult(response);
            //playTTS_api(response);
        }else if(domain.equals("Common_Story")){
            showTtsResult(response);
            //playTTS_api(response);
        }else if(domain.equals("Common_Weather")){
            showTtsResult(response);
            //playTTS_api(response);
        }else if(domain.equals("Common_Other")){
            showTtsResult(response);
        }
        else if(domain.equals("refrigerator")) {

            if (params.size() == 1) {
                String key = params.get(0).getKey();
                String value = params.get(0).getValue();
                switch (key) {
                    case "query":
                        if (value.equals("food")) {
                            tts = FoodDbManager.getInstance().queryAllFood();
                            showTtsResult(tts);
                        } else if (value.equals("address") || value.equals("UserAgreement")
                                || value.contains("service") || value.equals("binding") || value.equals("version")) {
                            showTtsResult(response);
                        }
                        break;
                    case "overtime":
                        break;
                    case "food_menu":
                        if(value.equals("西红柿炒鸡蛋")){
                            Log.d(TAG, "playTTSMedia: play 西红柿炒鸡蛋");
                            tts = "1、鸡蛋打散加入葱花和食盐搅拌均匀，番茄切块，锅中倒油烧至7成热倒入蛋液。2、蛋液凝固成型后用重新倒油烧热后倒入番茄煸炒。";
                        }else if(value.equals("干煸四季豆")){
                            tts = "蒜瓣切小颗粒，老姜切末，红椒切小粒；腌肉末：放姜末、少许生粉、一点点白糖、少许老抽（看自己喜欢颜色深浅），拌匀待用";
                        }else if(value.equals("可乐鸡翅")){
                            tts = "油温后，放入鸡翅。煎鸡翅的时候小心被见溅伤。将鸡翅两面都煎为金黄色，备用";
                        }else if(value.equals("醋溜白菜")){
                            tts = "将白菜梆洗净后从之间切开成两条，取其中一条，斜着用刀片成一片片；另外一条白菜也梆也片成片；干辣椒剪开，不吃辣的可以省去辣椒这一步骤";
                        }else if(value.equals("青椒肉丝")){
                            tts = "青椒切成丝，里脊肉顺纹理切成丝；把切好的肉丝放入碗内加淀粉、蛋清、少许盐抓均上浆、腌制一会。";
                        }else if(value.equals("拍黄瓜")){
                            tts = "热锅放入一匙芝麻油，随后加入干辣椒碎小火炒香，起锅的时候加入蒜蓉拌匀；31和2混合均匀即成腌渍料";
                        }else if(value.equals("老醋花生")){
                            tts = "锅里放油，冷油时就倒入花生米快速翻炒，以保证花生米能够均匀受热。3待花生米有炸开的响声后再炒一会儿，待有香味且差不多都裂开时起锅沥油";
                        }else if(value.equals("锅包肉")){
                            tts = "里脊肉切成片备用；把切好的里脊片放入碗中，加入1勺料酒，腌制片刻；取出后一片一片铺在淀粉上，两面都沾满淀粉；锅中加入炒菜油，建议选小锅，这样倒入的油有一定高度";
                        }else if(value.equals("鱼香茄子")){
                            tts = "猪肉馅加入调味料腌制20分钟，木耳提前用温水泡发后切丝，胡萝卜、冬笋切丝；锅中放半斤油，烧7成热，把茄子放入炸";
                        }else if(value.equals("宫保鸡丁")){
                            tts = "取嫩鸡胸肉，用刀把肉拍松，切成3毫米见方的十字花纹，再切成2厘米见方的小块，加盐、、湿淀粉拌匀。花生米事先炸好的。3炒锅上火，放底油，烧热";
                        }

                        showTtsResult(tts);
                        //LocalMediaManager.getInstance().playTTSMedia(key, value, RespListener);
                        break;
                    case "adjust_brightness":
                    case "adjust_voice":
                    case "switch_screen":
                        //LocalMediaManager.getInstance().playTTSMedia(key, value, RespListener);
                        showTtsResult(response);
                        //EventBus.getDefault().post(new NluEvent(response));
                        //waitForWakeup();
                        break;
                    case "add_address":
                        showTtsResult(response);
                        break;
                    case "edit_address":
                        showTtsResult(response);
                        break;
                    case "setmode":
                        showTtsResult(response);
//                        LocalMediaManager.getInstance().playTTSMedia(key, value, RespListener);
//                        EventBus.getDefault().post(new NluEvent(response));
                        break;
                    /*case "settemp":
                        showTtsResult(response);
                        break;*/
                    default:
                       // waitForWakeup();
                        showTtsResult("对不起我有点乱，请再说一遍");
                        break;
                }
            } else {
                String key0 = params.get(0).getKey();
                String key1 = params.get(1).getKey();
                String value0 = params.get(0).getValue();
                String value1 = params.get(1).getValue();

                switch (key0) {
                    case "query":
                        int count = FoodDbManager.getInstance().queryHasFoodByName(value1);
                        if (count > 0) {
                            tts = "你好，" + value1 + "在冰箱中，请尽快食用";
                        } else if (count == 0) {
                            tts = "冰箱中没有该食物";
                        }

                        showTtsResult(tts);
                        break;
                    case "add":
                        if (TextUtils.isEmpty(value1)) {

                        }

                        FoodBean food = new FoodBean();
                        food.setFoodName(value1);
                        food.setCreateDate(System.currentTimeMillis());
                        food.setSaveDay(0);
                        FoodDbManager.getInstance().addFood(food);
                        tts = "已为您添加好食物";
                        showTtsResult(tts);
                        break;
                    case "delete":
                        if (TextUtils.isEmpty(value1)) {

                        } else {
                            int ret = FoodDbManager.getInstance().deleteFood(value1);
                            if(ret == 0){
                                tts = "已为您删除该食物";
                            }else{
                                tts = "对不起冰箱内没有该食物";
                            }

                        }
                        showTtsResult(tts);
                        break;
                    case "adjust_tempure":
                        LocalMediaManager.getInstance().playTTSMedia(key0, value0, RespListener);
                        EventBus.getDefault().post(new NluEvent(response));
                        //waitForWakeup();
                        break;
                    case "time":
                        showTtsResult(response);
                        break;
                    case "settemp":
                        showTtsResult(response);
                        break;
                    case "food_menu":
                        if(value0.equals("query")){
                            showTtsResult("正在为您查询该食物的做法");
                        }
                        break;
                    default:
                        showTtsResult("对不起我有点乱，请再说一遍");
                        //waitForWakeup();
                        break;
                }
            }
        }
    }

    private void showTtsResult(String tts){
        if(TextUtils.isEmpty(tts)){
            EventBus.getDefault().post(new NluEvent("语义理解错误"));
            waitForWakeup();
        }else {
            EventBus.getDefault().post(new NluEvent(tts));
            //playTTS(tts);
            playTTS_api_l(tts, RespListener);
        }
    }

    private MediaPlayer.OnCompletionListener RespListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {
            waitForWakeup();
        }
    };
}
