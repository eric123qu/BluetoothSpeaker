package com.haier.ai.bluetoothspeaker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RecognizeService extends Service {
    public RecognizeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
