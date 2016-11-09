package com.haier.ai.bluetoothspeaker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.haier.ai.bluetoothspeaker.manager.WifiDevManager;
import com.haier.ai.bluetoothspeaker.service.BluetoothService;

/**
 * author: qu
 * date: 16-11-4
 * introduce:
 */

public class Receiver extends BroadcastReceiver {
    private final String ACTION_BOOT_COMPLATE = "android.intent.action.BOOT_COMPLETED";
    private final String ACTION_WIFI_STATE_CHANGE = WifiManager.WIFI_STATE_CHANGED_ACTION;
    private final String ACTION_WIFI_SCAN_AVAILABLE = WifiManager.SCAN_RESULTS_AVAILABLE_ACTION;
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case ACTION_BOOT_COMPLATE:
                Intent iBluetoothService = new Intent(context, BluetoothService.class);
                context.startService(iBluetoothService);
                break;
            case ACTION_WIFI_STATE_CHANGE:
                //// TODO: 16-11-4  收到广播，搜索wifi
                int wifiState = intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_ENABLED:
                        //Log.d(TAG, "WiFi已启用" + DateUtils.getCurrentTime());
                        WifiDevManager.getInstance().startScan();
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        //Log.d(TAG, "Wifi已关闭" + DateUtils.getCurrentTime());
                        break;
                }
                break;
            case ACTION_WIFI_SCAN_AVAILABLE:
                //// TODO: 16-11-4 获取到搜索列表
                WifiDevManager.getInstance().getWifiList();
                break;
            default:
                break;
        }
    }
}
