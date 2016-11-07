package com.haier.ai.bluetoothspeaker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.haier.ai.bluetoothspeaker.manager.BluetoothManager;
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
        AcceptThread thread = new AcceptThread(BluetoothManager.getInstance().getBluetoothAdapter());
        thread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
