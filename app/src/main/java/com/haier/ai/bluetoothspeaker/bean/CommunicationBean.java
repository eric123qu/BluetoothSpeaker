package com.haier.ai.bluetoothspeaker.bean;

/**
 * Created by qx on 16-11-29.
 * 蓝牙通信json数据格式
 */

public class CommunicationBean {
    //数据类型(定义见const)
    private int type;

    //内容
    private String data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
