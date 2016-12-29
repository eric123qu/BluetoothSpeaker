package com.haier.ai.bluetoothspeaker.bean;

/**
 * author: qu
 * date: 16-11-4
 * introduce: 搜索到的wifi热点信息
 */

public class ScanWifiBean {
    private String ssid;
    private String passwd;
    private int securityType;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getSecurityType() {
        return securityType;
    }

    public void setSecurityType(int securityType) {
        this.securityType = securityType;
    }
}
