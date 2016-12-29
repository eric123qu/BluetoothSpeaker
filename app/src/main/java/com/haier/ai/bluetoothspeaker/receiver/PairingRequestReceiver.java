package com.haier.ai.bluetoothspeaker.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by qx on 16-11-29.
 */

public class PairingRequestReceiver extends BroadcastReceiver{
    private final String TAG = "PairingRequestReceiver";
    private final String strPsw = "0000";
    private final String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
    private static BluetoothDevice remoteDevice = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_PAIRING_REQUEST)) {

            Log.d(TAG, "recv pairing request");
            BluetoothDevice device = intent
                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                try {
                    /*ClsUtils.setPin(device.getClass(), device, strPsw); // 手机和蓝牙采集器配对
                    // ClsUtils.cancelPairingUserInput(device.getClass(),
                    // device); //一般调用不成功，前言里面讲解过了*/
                    device.setPairingConfirmation(true);
                    abortBroadcast();
                    Log.d(TAG, "配对信息" + device.getName());

                    //SpeakerBluetoothManager.getInstance().connectA2dp(device);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.e(TAG, "请求连接错误...");
                }
            }
            // */
            // pair(device.getAddress(),strPsw);
        }
    }
}
