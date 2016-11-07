package com.haier.ai.bluetoothspeaker.manager;

import android.bluetooth.BluetoothAdapter;

import com.haier.ai.bluetoothspeaker.util.LogUtil;

/**
 * author: qu
 * date: 16-11-7
 * introduce:
 */

public class BluetoothManager {
    private final String TAG = "BluetoothManager";
    private static BluetoothManager sBluetoothManager;
    private static BluetoothAdapter sBluetoothAdapter;

    public BluetoothManager(){
        if(sBluetoothAdapter == null){
            sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
    }

    public static BluetoothManager getInstance(){
        if(sBluetoothManager == null){
            sBluetoothManager = new BluetoothManager();
        }

        return sBluetoothManager;
    }


    public BluetoothAdapter getBluetoothAdapter(){
        return sBluetoothAdapter;
    }

    public boolean openBluetooth(){
        if(sBluetoothAdapter == null){
            LogUtil.LogE(TAG, "本地蓝牙不可用");
            return false;
        }

        if(sBluetoothAdapter.isEnabled()){
            return true;
        }

        return sBluetoothAdapter.enable();
    }

    public boolean closeBluetooth(){
        if(sBluetoothAdapter == null){
            LogUtil.LogE(TAG, "本地蓝牙不可用");
            return false;
        }

        if(sBluetoothAdapter.isEnabled()){
            return sBluetoothAdapter.disable();
        }

        return true;
    }


}
