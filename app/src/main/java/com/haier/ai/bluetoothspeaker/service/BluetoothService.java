package com.haier.ai.bluetoothspeaker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.haier.ai.bluetoothspeaker.manager.BluetoothManager;
import com.haier.ai.bluetoothspeaker.thread.AcceptThread;
import com.haier.ai.bluetoothspeaker.util.LogUtil;

/**
 * author: qu
 * date: 16-11-7
 * introduce:
 */

public class BluetoothService extends Service {
    private final String TAG = "BluetoothService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.LogD(TAG, "BluetoothService is started");

        startAcceptThread();

        return START_STICKY;
    }

    private void startAcceptThread(){
        AcceptThread thread = new AcceptThread(BluetoothManager.getInstance().getBluetoothAdapter());
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
