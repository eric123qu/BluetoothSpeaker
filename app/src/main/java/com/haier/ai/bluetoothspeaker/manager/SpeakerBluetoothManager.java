package com.haier.ai.bluetoothspeaker.manager;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.App;

import java.lang.reflect.Method;

/**
 * author: qu
 * date: 16-11-7
 * introduce:
 */

public class SpeakerBluetoothManager {
    private final String TAG = "SpeakerBluetoothManager";
    private static SpeakerBluetoothManager sSpeakerBluetoothManager;
    private static BluetoothAdapter sBluetoothAdapter;
    private static BluetoothA2dp mBTA2DP;
    private static BluetoothDevice remoteDevice;

    public SpeakerBluetoothManager(){
        if(sBluetoothAdapter == null){
            sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            //a2dp
            sBluetoothAdapter.getProfileProxy(App.getInstance(), mProfileServiceListener, BluetoothProfile.A2DP);
        }
    }

    public static SpeakerBluetoothManager getInstance(){
        if(sSpeakerBluetoothManager == null){
            sSpeakerBluetoothManager = new SpeakerBluetoothManager();
        }

        return sSpeakerBluetoothManager;
    }


    public BluetoothAdapter getBluetoothAdapter(){
        return sBluetoothAdapter;
    }

    public boolean openBluetooth(){
        if(sBluetoothAdapter == null){
            Log.e(TAG, "本地蓝牙不可用");
            return false;
        }

        if(sBluetoothAdapter.isEnabled()){
            return true;
        }

        return sBluetoothAdapter.enable();
    }

    public boolean closeBluetooth(){
        if(sBluetoothAdapter == null){
            Log.e(TAG, "本地蓝牙不可用");
            return false;
        }

        if(sBluetoothAdapter.isEnabled()){
            return sBluetoothAdapter.disable();
        }

        return true;
    }

    public void enablingDiscoverability() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        // 0，自身设备始终可以被发现（意味着将十分消耗设备资源，如电源）
        // 第二个参数可设置的范围是0~3600秒，在此时间区间（窗口期）内可被发现
        // 任何不在此区间的值都将被自动设置成120秒。
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);

        App.getInstance().startActivity(discoverableIntent);
    }

    private BluetoothProfile.ServiceListener mProfileServiceListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            Log.d(TAG, "onServiceConnected: ");
            try {
                if (profile == BluetoothProfile.HEADSET) {
               /* bh = (BluetoothHeadset) proxy;
                if (bh.getConnectionState(device) != BluetoothProfile.STATE_CONNECTED){
                    bh.getClass()
                            .getMethod("connect", BluetoothDevice.class)
                            .invoke(bh, device);
                }*/
                } else if (profile == BluetoothProfile.A2DP) {
                    mBTA2DP = (BluetoothA2dp) proxy;
/*
                    if(remoteDevice == null){
                        return;
                    }

                    if (mBTA2DP.getConnectionState(remoteDevice) != BluetoothProfile.STATE_CONNECTED) {
                        mBTA2DP.getClass()
                                .getMethod("connect", BluetoothDevice.class)
                                .invoke(mBTA2DP, remoteDevice);
                    }*/
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(int profile) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    public void connectA2dp(BluetoothDevice device){
        remoteDevice = device;
        //sBluetoothAdapter.getProfileProxy(App.getInstance(), mProfileServiceListener, BluetoothProfile.A2DP);
        boolean bPlay = mBTA2DP.isA2dpPlaying(device);
        Class<? extends BluetoothA2dp> clazz = mBTA2DP.getClass();
        Method m2;
        try {
            Log.i(TAG,"use reflect to connect a2dp");
            m2 = clazz.getMethod("connect",BluetoothDevice.class);
            m2.invoke(mBTA2DP, device);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG,"error:" + e.toString());
        }
    }
}
