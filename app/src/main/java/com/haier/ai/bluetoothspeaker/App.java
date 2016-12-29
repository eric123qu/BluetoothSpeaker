package com.haier.ai.bluetoothspeaker;

import android.app.Application;

/**
 * author: qu
 * date: 16-11-3
 * introduce:
 */

public class App extends Application {

    private static App sApp;

    public App(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }

    public static App getInstance(){
        return sApp;
    }
}
