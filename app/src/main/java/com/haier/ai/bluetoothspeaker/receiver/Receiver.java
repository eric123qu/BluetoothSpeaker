package com.haier.ai.bluetoothspeaker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.DeviceConst;
import com.haier.ai.bluetoothspeaker.manager.LightManager;
import com.haier.ai.bluetoothspeaker.manager.SpeakerAlarmManager;
import com.haier.ai.bluetoothspeaker.manager.WifiDevManager;
import com.haier.ai.bluetoothspeaker.service.BluetoothService;
import com.haier.ai.bluetoothspeaker.service.ReconizeService;
import com.haier.ai.bluetoothspeaker.service.SocketService;
import com.haier.ai.bluetoothspeaker.service.WakeupService;
import com.haier.ai.bluetoothspeaker.ui.ClockAlarmActivity;

import static com.baidu.speech.EventManagerFactory.TAG;

/**
 * author: qu
 * date: 16-11-4
 * introduce:
 */

public class Receiver extends BroadcastReceiver {
    private final String ACTION_BOOT_COMPLATE = "android.intent.action.BOOT_COMPLETED";
    private final String ACTION_WIFI_STATE_CHANGE = WifiManager.WIFI_STATE_CHANGED_ACTION;
    private final String ACTION_WIFI_SCAN_AVAILABLE = WifiManager.SCAN_RESULTS_AVAILABLE_ACTION;
    private final String ACTION_NETWORK_STATE_CHANGE = WifiManager.NETWORK_STATE_CHANGED_ACTION;
    private final String netACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private final String ACTION_ALARM = "com.loonggg.alarm.clock";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case ACTION_BOOT_COMPLATE:
                Intent iBluetoothService = new Intent(context, BluetoothService.class);
                context.startService(iBluetoothService);

                /**
                 * 唤醒服务
                 */
                Intent wakeupService = new Intent(context, WakeupService.class);
                context.startService(wakeupService);

                /**
                 * 语音识别
                 */
                Intent intent1 = new Intent(context, ReconizeService.class);
                context.startService(intent1);

                Intent intentScoket = new Intent(context, SocketService.class);
                context.startService(intentScoket);

                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                //首次唤醒
                Const.IS_FIRST_WAKEUP = true;


                initLightStatus();

                break;
            case ACTION_WIFI_STATE_CHANGE:
                //// TODO: 16-11-4  收到广播，搜索wifi
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

                switch (wifiState) {
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.d(TAG, "WiFi已启用" );
                        WifiDevManager.getInstance().startScan();
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.d(TAG, "Wifi已关闭" );
                        break;
                }

                //NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                break;
            case ACTION_NETWORK_STATE_CHANGE:
                //// TODO: 16-12-21  语音提示
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if(info.getState().equals(NetworkInfo.State.DISCONNECTED)){
                    Log.e(TAG, "wifi网络连接断开");
                    LightManager.getInstance().netDisconnect();

                    DeviceConst.DEVICE_NET_STATUS = DeviceConst.NET_STATUS_OFF;
                }
                else if(info.getState().equals(NetworkInfo.State.CONNECTED)){

                    WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    //获取当前wifi名称
                    Log.i(TAG, "连接到网络 " + wifiInfo.getSSID());
                    LightManager.getInstance().lightNormal();

                    DeviceConst.DEVICE_NET_STATUS = DeviceConst.NET_STATUS_ON;
                }
                break;

            case ACTION_WIFI_SCAN_AVAILABLE:
                //// TODO: 16-11-4 获取到搜索列表
                WifiDevManager.getInstance().getWifiList();
                break;
            case ACTION_ALARM:
                startAlarm(context, intent);
                break;
            case netACTION:
                if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)){
                    //代表网络断开
                    Log.e(TAG, "wifi网络连接断开");
                    LightManager.getInstance().netDisconnect();

                    DeviceConst.DEVICE_NET_STATUS = DeviceConst.NET_STATUS_OFF;

                    //MusicPlayerManager.getInstance().playNetError();
                }else{
                    //链接
                    Log.d(TAG, "onReceive: netACTION 网络已连接");
                    DeviceConst.DEVICE_NET_STATUS = DeviceConst.NET_STATUS_ON;
                    LightManager.getInstance().lightNormal();
                }
                break;
            default:
                break;
        }
    }

    private void startAlarm(Context context, Intent intent){
        String msg = intent.getStringExtra("msg");
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            SpeakerAlarmManager.getInstance().setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }

        int flag = intent.getIntExtra("soundOrVibrator", 0);
        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);
        clockIntent.putExtra("msg", msg);
        clockIntent.putExtra("flag", flag);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
    }

    /**
     * 开机初始化灯光状态
     */
    private void initLightStatus(){
        //打开灯光
        LightManager.getInstance().bootLightShow();

        //呼吸灯打开
        LightManager.getInstance().lightNormal();

        DeviceConst.LIGHT_STATUS = DeviceConst.LIGHT_STATUS_OPEN;

        DeviceConst.CURRENT_LIGHT_MODE = DeviceConst.LIGHT_MODE_STANDARD;
    }
}
