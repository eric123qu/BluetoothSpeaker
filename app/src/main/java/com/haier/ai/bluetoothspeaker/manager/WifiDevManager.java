package com.haier.ai.bluetoothspeaker.manager;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.haier.ai.bluetoothspeaker.App;
import com.haier.ai.bluetoothspeaker.bean.ScanWifiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: qu
 * date: 16-11-4
 * introduce: wifi操作类
 */

public class WifiDevManager {
    private final String TAG = "WifiDevManager";
    public static WifiDevManager sInstance;
    private static WifiManager sWifiManager;

    private static int SECURITY_NONE = 0;
    private static int SECURITY_WEP = 1;
    private static int SECURITY_WPA = 2;


    public WifiDevManager(){
        if(sWifiManager == null){
            sWifiManager = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        }
    }

    public static WifiDevManager getInstance(){
        if(sInstance == null){
            sInstance = new WifiDevManager();
        }

        return sInstance;
    }

    /**
     * 打开wifi
     * @return
     */
    public boolean openWifi(){
        boolean ret = true;
        if(!sWifiManager.isWifiEnabled()){
            return sWifiManager.setWifiEnabled(true);
        }

        return ret;
    }

    /**
     * 关闭wifi
     * @return
     */
    public boolean closeWifi(){
        if(sWifiManager.isWifiEnabled()){
            return sWifiManager.setWifiEnabled(false);
        }

        return false;
    }

    /**
     * 开始搜索可用wifi
     */
    public void startScan(){
        sWifiManager.startScan();
    }

    /**
     * 获取搜索到的wifi热点
     * @return
     */
    public List<ScanWifiBean> getWifiList(){
        List<ScanResult> results = sWifiManager.getScanResults();
        if(results.size() == 0){
            return null;
        }

        List<ScanWifiBean> wifiList = new ArrayList<>();

        for(ScanResult scanResult : results){
            ScanWifiBean bean = new ScanWifiBean();
            bean.setSsid(scanResult.SSID);
            bean.setSecurityType(getSecurityType(scanResult));
            wifiList.add(bean);
        }

        return wifiList;
    }

    /**
     * 获取加密类型
     * @param result
     * @return
     */
    private int getSecurityType(ScanResult result){
        String capabilities = result.capabilities;

        if(capabilities.contains("WPA") || capabilities.contains("wpa")){
            return SECURITY_WPA;
        }else if(capabilities.contains("WEP") || capabilities.contains("wep")){
            return SECURITY_WEP;
        }else{
            return SECURITY_NONE;
        }
    }

    /**
     * 连接到指定wifi网络
     * @param wifiInfo
     * @return false：失败  true：未必成功(还要再做判断)
     */
    public boolean connectWifi(ScanWifiBean wifiInfo){
        if(wifiInfo == null){
            return false;
        }

        if(TextUtils.isEmpty(wifiInfo.getSsid())){
            return false;
        }

        WifiConfiguration wifiConfiguration;
        wifiConfiguration = createWifiInfo(wifiInfo.getSsid(), wifiInfo.getPasswd(), wifiInfo.getSecurityType());
        boolean flag = addNetwork(wifiConfiguration);//连接网络

        return  flag;
    }


    private void disconnectWifi(){

    }

    private WifiConfiguration createWifiInfo(String SSID, String Password, int securityType) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        if (securityType == SECURITY_NONE) {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }

        if (securityType == SECURITY_WEP) {
            if (!TextUtils.isEmpty(Password)) {
                if (isHexWepKey(Password)) {
                    config.wepKeys[0] = Password;
                } else {
                    config.wepKeys[0] = "\"" + Password + "\"";
                }
            }
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }

        if (securityType == SECURITY_WPA) {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//          此处需要修改否则不能自动重联
//          config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    // 添加一个网络并连接
    public boolean addNetwork(WifiConfiguration wcg) {
        int wcgID = sWifiManager.addNetwork(wcg);
        boolean ret = sWifiManager.enableNetwork(wcgID, true);

        return ret;
    }

    private  boolean isHexWepKey(String wepKey) {
        final int len = wepKey.length();

//     WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        if (len != 10 && len != 26 && len != 58) {
            return false;
        }

        return isHex(wepKey);
    }

    private  boolean isHex(String key) {
        for (int i = key.length() - 1; i >= 0; i--) {
            final char c = key.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f')) {
                return false;
            }
        }

        return true;
    }
}
