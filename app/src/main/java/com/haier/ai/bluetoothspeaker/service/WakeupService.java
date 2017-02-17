package com.haier.ai.bluetoothspeaker.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.event.WakeupEvent;
import com.haier.ai.bluetoothspeaker.manager.WakeupEventManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.haier.ai.bluetoothspeaker.Const.register_wakeup;

public class WakeupService extends Service {
    private final String TAG = "WakeupService";
    //private EventManager mWpEventManager;
    private String filename;
    private Wait4WakeupRecevier recevier;
    private EventListener eventListener;

    public WakeupService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: wakeup service start");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: wakeup service start");

        registerWakeup();

        registerRewakeup();

        //initToken();
        
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unregisterReceiver(recevier);

        unregisterWakeup();
    }



    private void registerWakeup(){
        Log.e(TAG, "registerWakeup:========= ");
        ++register_wakeup;
        Log.d(TAG, "registerWakeup: count:" + Const.register_wakeup);
        if(Const.register_wakeup > 1){
            return;
        }

        // 唤醒功能打开步骤
        // 1) 创建唤醒事件管理器
       // mWpEventManager = EventManagerFactory.create(WakeupService.this, "wp");
       // WakeupEventManager wakeupEventManager = new WakeupEventManager(WakeupService.this);
        eventListener = new EventListener() {
            //mWpEventManager.registerListener(new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
                Log.d(TAG, String.format("event: name=%s, params=%s", name, params));
                try {
                    JSONObject json = new JSONObject(params);
                    if ("wp.data".equals(name)) { // 每次唤醒成功, 将会回调name=wp.data的时间, 被激活的唤醒词在params的word字段
                        String word = json.getString("word");
                        Log.d(TAG, "onEvent: 唤醒成功");
                        EventBus.getDefault().post(new WakeupEvent("Hello everyone!"));
                    } else if ("wp.exit".equals(name)) {
                        Log.d(TAG, "onEvent: 唤醒已经停止");
                    }
                } catch (JSONException e) {
                    throw new AndroidRuntimeException(e);
                }
            }
        };
        // 2) 注册唤醒事件监听器
        //WakeupEventManager.getInstance(WakeupService.this).getEventManager().registerListener(eventListener);
        WakeupEventManager.getInstance(WakeupService.this).registerEventListener(eventListener);
        // 3) 通知唤醒管理器, 启动唤醒功能
        HashMap params = new HashMap();
        params.put("kws-file", "assets:///WakeUp.bin"); // 设置唤醒资源, 唤醒资源请到 http://yuyin.baidu.com/wake#m4 来评估和导出
       // mWpEventManager.send("wp.start", new JSONObject(params).toString(), null, 0, 0);
        try {
            Log.i(TAG, "registerWakeup: start wake up");
            WakeupEventManager.getInstance(WakeupService.this).startWakeup(new JSONObject(params).toString());
        } catch (Exception e) {
            Toast.makeText(WakeupService.this, "请检测网络连接", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.e(TAG, "registerWakeup: 1111");
            //WakeupEventManager.getInstance(WakeupService.this).startWakeup(new JSONObject(params).toString());

        }
    }

    /*@Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReWakeupEvent(ReWakeupEvent event){
        Log.d(TAG, "OnRestartWakeupEvent: ");
        registerWakeup();
    }*/

    private void unregisterWakeup(){
        WakeupEventManager.getInstance(WakeupService.this).getEventManager().unregisterListener(eventListener);
    }

    private void registerRewakeup(){
//        EventBus.getDefault().register(WakeupService.this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.WAKEUP_TAG);
        recevier = new Wait4WakeupRecevier();
        registerReceiver(recevier, filter);
    }

    /*public void initToken(){
        String token = MyApplication.getInstance().getAccessToken();
        initGetToken(token);
    }

    private void initGetToken(String token) {
        *//**
         * 确保app刚安装上，token为空的时候，去请求token,token长度为30
         *//*
        Log.d(TAG, "initGetToken: init token");
        if (TextUtils.isEmpty(token) || token.length() != 30) {
//            NetRequest.getInstance().getToken();
            if (NetWorkUtils.isNetworkConnected(MyApplication.getInstance().mContext)){
                RetrofitRequest.getInstance().getToken();
            }else{
                Log.e(TAG, "initGetToken: 您的网络未连接，请连接网络，或您的网络无法连接外网");
            }
        }
    }*/
     class Wait4WakeupRecevier extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case Const.WAKEUP_TAG:
                    Log.d(TAG, "onReceive: recv wait for wakeup broadcast");
                    registerWakeup();
                    break;
            }
        }
    }
}
