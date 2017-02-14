package com.haier.ai.bluetoothspeaker.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.R;
import com.haier.ai.bluetoothspeaker.event.DialogEvent;
import com.haier.ai.bluetoothspeaker.event.NluEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeResultEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeStatusEvent;
import com.haier.ai.bluetoothspeaker.event.StartRecordEvent;
import com.haier.ai.bluetoothspeaker.event.WakeupEvent;
import com.haier.ai.bluetoothspeaker.manager.WakeupEventManager;
import com.haier.ai.bluetoothspeaker.model.RecordModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReconizeService extends Service {
    private final String TAG = "ReconizeService";
    private final int MAX_RERECONIZE_COUNT = 3;
    private static int reReconizeCount = 0;
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
    private static final int TYPE_WAKEUP = 0;
    private static final int TYPE_RERECONIZE = 1;
    private static final int TYPE_SLEEP = 2;
    private static final int TYPE_DING = 3;
    @IntDef({TYPE_WAKEUP, TYPE_RERECONIZE, TYPE_SLEEP, TYPE_DING}) @interface StateType{}
    private MediaPlayer.OnCompletionListener completionListener;
    private MediaPlayer player = null;
    private ReReconizeReceiver mReceiver;

    public ReconizeService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: start");
        EventBus.getDefault().register(this);

        //registerBroadcast();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //unregisterReceiver(mReceiver);
        /*if (mRecorder != null)
        {
            mRecorder.cancel();
            mRecorder = null;
        }*/


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 进入识别状态
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onStartRecordEvent(StartRecordEvent event){
        Log.d(TAG, "onStartRecord: restart record begin");
        Log.d(TAG, "onStartRecordEvent: event count :" + reReconizeCount);

        if(event.getReReconize()){
            handleRereconizeEvent();
        }else{
            startReconize();
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onWakeupEvent(WakeupEvent event){
        //关闭唤醒
        WakeupEventManager.getInstance(this).stopWakeup();
        WakeupEventManager.getInstance(this).unregisterEventListener();
        SystemClock.sleep(500);

        EventBus.getDefault().post(new ReconizeStatusEvent("唤醒成功"));
        EventBus.getDefault().post(new ReconizeResultEvent(""));
        EventBus.getDefault().post(new NluEvent(""));

        playLocalAudio(TYPE_WAKEUP, initWakeupListener());
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onDialogEvent(DialogEvent event){
        //playLocalAudio(TYPE_WAKEUP, initWakeupListener());
        playLocalAudio(TYPE_DING, null);

        EventBus.getDefault().post(new ReconizeStatusEvent("开始识别"));
        //开始识别
        RecordModel.getInstance().startRecord();//sdk mode

        //RecordModel_bak.getInstance().startRecord(); //api mode
        scheduledThreadPool.schedule(new Runnable() {

            @Override
            public void run() {
                /**
                 * sdk mode
                 */
                RecordModel.getInstance().stopRecord(); //sdk mode
                //RecordModel.getInstance().releaseSdk();

                /**
                 * api mode
                 */
                        /*String filename = RecordModel_bak.getInstance().stopRecord();
                        Log.d(TAG, "run: record filename:" + filename);
                        EventBus.getDefault().post(new ReconizeStatusEvent("识别结束"));
                        SystemClock.sleep(500);
                        RecordModel_bak.getInstance().uploadRecordData(filename);*/

            }
        }, 3, TimeUnit.SECONDS);

    }

    /**
     * 开始语音识别
     */
    public void startReconize(){
        //提示音
        playLocalAudio(TYPE_DING, null);
        //开始识别
        RecordModel.getInstance().startRecord();

        scheduledThreadPool.schedule(new Runnable() {

            @Override
            public void run() {
                RecordModel.getInstance().stopRecord();
            }
        }, 3, TimeUnit.SECONDS);
    }

    public void playWakeupSucess(MediaPlayer.OnCompletionListener listenr){
        MediaPlayer player = MediaPlayer.create(this, R.raw.wp_sucess);
        player.setOnCompletionListener(listenr);
        player.start();
    }

    /**
     * 播放提示音
     * @param type
     * @param listenr
     */
    public void playLocalAudio(@StateType int type, MediaPlayer.OnCompletionListener listenr){

        switch (type){
            case TYPE_WAKEUP:
                player = MediaPlayer.create(this, R.raw.wp_sucess);
                player.setOnCompletionListener(listenr);
                break;
            case TYPE_RERECONIZE:
//                player = MediaPlayer.create(this, R.raw.rereconize);
//                player.setOnCompletionListener(listenr);
//                break;
            case TYPE_SLEEP:
                if(player != null){
                    player.release();
                    player = null;
                }
                player = MediaPlayer.create(this, R.raw.sleep);
                break;
            case TYPE_DING:
                player = MediaPlayer.create(this, R.raw.start_tone);
                break;
        }

        player.start();
    }

    /**
     * 进入待唤醒
     */
    public void waitForWakeup(){
        Intent intent = new Intent(Const.WAKEUP_TAG);
        sendBroadcast(intent);

        EventBus.getDefault().post(new ReconizeStatusEvent("待唤醒"));
    }

    /**
     * 唤醒成功监听
     * @return
     */
    public MediaPlayer.OnCompletionListener  initWakeupListener(){
        return completionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp = null;
                //tisshi
                //CueSoundManager.getInstance().playCueSound(CueSoundManager.TYPE_DING);
                playLocalAudio(TYPE_DING, null);

                EventBus.getDefault().post(new ReconizeStatusEvent("开始识别"));
                //开始识别
                RecordModel.getInstance().startRecord();//sdk mode

                //RecordModel_bak.getInstance().startRecord(); //api mode
                scheduledThreadPool.schedule(new Runnable() {

                    @Override
                    public void run() {
                        /**
                         * sdk mode
                         */
                        RecordModel.getInstance().stopRecord(); //sdk mode
                        //RecordModel.getInstance().releaseSdk();

                        /**
                         * api mode
                         */
                        /*String filename = RecordModel_bak.getInstance().stopRecord();
                        Log.d(TAG, "run: record filename:" + filename);
                        EventBus.getDefault().post(new ReconizeStatusEvent("识别结束"));
                        SystemClock.sleep(500);
                        RecordModel_bak.getInstance().uploadRecordData(filename);*/

                    }
                }, 3, TimeUnit.SECONDS);
            }
        };
    }

    /**
     * 处理收到的消息
     * @return
     */
    private int handleRereconizeEvent(){
        int ret = 0;
        ++reReconizeCount;

        if(reReconizeCount > MAX_RERECONIZE_COUNT){
            //进入带唤醒状态
            Log.d(TAG, "onStartRecordEvent: 进入带唤醒状态");
            //// TODO: 16-9-5 进入带唤醒状态
            playLocalAudio(TYPE_SLEEP, null);
            waitForWakeup();
            reReconizeCount= 0;
            return -1;
        }

        //播放提示音
        playLocalAudio(TYPE_RERECONIZE, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startReconize();
            }
        });

        return 0;
    }

    private void goSleep(){
        playLocalAudio(TYPE_SLEEP, null);
        waitForWakeup();
    }
    /**
     * 暂不用broadcast
     */
    public void registerBroadcast(){
        mReceiver = new ReReconizeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.RERECONIZE_TAG);
        registerReceiver(mReceiver, filter);
    }

    class ReReconizeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case Const.RERECONIZE_TAG:
                    Log.d(TAG, "onReceive: recv ReReconizeReceiver");
                    StartRecordEvent event = new StartRecordEvent(true);
                    onStartRecordEvent(event);
                    break;
            }
        }
    }
}
