package com.haier.ai.bluetoothspeaker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.manager.SpeakerBluetoothManager;
import com.haier.ai.bluetoothspeaker.thread.AcceptThread;

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
        Log.d(TAG, "BluetoothService is started");

        startAcceptThread();

        return START_STICKY;
    }

    private void startAcceptThread(){
        AcceptThread thread = new AcceptThread(SpeakerBluetoothManager.getInstance().getBluetoothAdapter());
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
