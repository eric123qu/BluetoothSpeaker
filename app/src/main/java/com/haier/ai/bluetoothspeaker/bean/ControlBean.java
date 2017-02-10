package com.haier.ai.bluetoothspeaker.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * author: qu
 * date: 15-8-10
 * introduce:
 */
public class ControlBean {
    private String operator;//命令
    private String device;//设备类型
    private String operands;//设备种类
    private String location;//设备位置
    private String sequence;//设备序号
    private byte devAttr;//设备属性
    private short attrStatus;// 属性状态(0关1开，温度则为温度数值)
    private byte attrStatusByte;//灯光，窗帘等单字节的参数
    private String value;//温度值
    private String nickName;
    private String originType; //控制家电领域
    private int time;   //预约时间
    private byte[] attrStatusBytes;//属性状态(60设备用)
    private List<DevInfo> devInfoList;
    private boolean isDialog;

    public ControlBean(){

    }
   /* public ControlBean(String operator, String device, String type, String location, String sequence){
        this.operator = operator;
        this.device = device;
        this.operands = operands;
        this.location = location;
        this.sequence = sequence;
    }*/

   public void setIsDialog(boolean isDialog){
       this.isDialog = isDialog;
   }

   public boolean getIsDialog(){
       return this.isDialog;
   }
    public void setOperator(String operator){
        this.operator = operator;
    }
    public String getOperator(){
        return this.operator;
    }
    public void setDevice(String device){
        this.device = device;
    }
    public String getDevice(){
        return this.device;
    }
    public void setOperands(String operands){
        this.operands = operands;
    }
    public String getOperands(){
        return this.operands;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }
    public void setSequence(String sequence){
        this.sequence = sequence;
    }
    public String getSequence(){
        return this.sequence;
    }
    public void setDevAttr(byte attr){
        this.devAttr = attr;
    }
    public byte getDevAttr(){
        return this.devAttr;
    }
    public void setAttrStatusShort(short status){
        this.attrStatus = status;
    }
    public short getAttrStatusShort(){
        return this.attrStatus;
    }
    public void setAttriStatusByte(byte status){
        this.attrStatusByte = status;
    }
    public byte getAttrStatusByte(){
        return this.attrStatusByte;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    public void setNickName(String name){
        this.nickName = name;
    }
    public String getNickName(){
        return this.nickName;
    }
    public void setOriginType(String originType){
        this.originType = originType;
    }
    public String getOriginType(){
        return this.originType;
    }
    public void setAttrStatusBytes(byte [] status){
        int len = status.length;
        this.attrStatusBytes = new byte[len];
        System.arraycopy(status, 0, this.attrStatusBytes, 0, len);
    }
    public byte [] getAttrStatusBytes(){
        return this.attrStatusBytes;
    }
    public void setTime(String sTime){
        if(!TextUtils.isEmpty(sTime)){
            if(sTime.contains("晚上")){
                String value = sTime.substring(2, sTime.length());
                time = calTime(0, value);
            }else if(sTime.contains("明天")){
                String value = sTime.substring(2, sTime.length());
                time = calTime(1, value);
            }else{
                time = calTime(1, sTime);
            }

        }else{
            time = -1;
        }
    }

    public int getTime(){
        return time;
    }

    public void setDevInfoList(List<DevInfo> list){
        if(list != null){
            this.devInfoList = list;
        }
    }

    public List<DevInfo> getDevInfoList(){
        return this.devInfoList;
    }

    private int calTime(int type, String time){
        int iTime = 0;
        if(type ==0){
            iTime += 12;
        }

        switch (time){
            case "一":
                iTime += 1;
                break;
            case "二":
                iTime += 2;
                break;
            case "三":
                iTime += 3;
                break;
            case "四":
                iTime += 4;
                break;
            case "五":
                iTime += 5;
                break;
            case "六":
                iTime += 6;
                break;
            case "七":
                iTime += 7;
                break;
            case "八":
                iTime += 8;
                break;
            case "九":
                iTime += 9;
                break;
            case "十":
                iTime += 10;
                break;
            case "十一":
                iTime += 11;
                break;
            case "十二":
                iTime += 12;
                break;
        }
        return iTime;
    }

    public static class DevInfo{
        private byte devAttr0;
        private byte devAttr1;
        private byte attrStatus0;
        private byte attrStatus1;

        public void setDevAttr0(byte attr){
            this.devAttr0 = attr;
        }

        public void setDevAtt1(byte attr){
            this.devAttr1 = attr;
        }

        public void setAttrStatus0(byte status){
            this.attrStatus0 = status;
        }

        public void setAttrStatus1(byte status){
            this.attrStatus1 = status;
        }

        public byte getDevAttr0(){
            return this.devAttr0;
        }

        public byte getDevAttr1(){
            return this.devAttr1;
        }

        public byte getAttrStatus0(){
            return this.attrStatus0;
        }

        public byte getAttrStatus1(){
            return this.attrStatus1;
        }
    }
}
