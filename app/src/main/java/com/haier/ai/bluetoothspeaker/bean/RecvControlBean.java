package com.haier.ai.bluetoothspeaker.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * author: qu
 * date: 15-8-21
 * introduce:
 */
public class RecvControlBean {
    private byte seq;
    private String commandType;
    private String devType;
    private String devDetail;//设备种类
    private String devPosition;
    private byte devSequence;
    public List<PropertyItem> propertyList;
    private byte bCommandType;
    private byte devAttr1;
    private byte devAttr2;//设备属性
    private byte attrStatus1;//属性状态
    private byte attrStatus2;
    private String nickName;

    public RecvControlBean(){
        if(propertyList == null){
            propertyList = new ArrayList<PropertyItem>();
        }
    }

    public void setSeq(byte seq){
        this.seq = seq;
    }
    public void setCommandType(String command){
        this.commandType = command;
    }
    public void setDevType(String devType){
        this.devType = devType;
    }

    public String getDevType(){
        return this.devType;
    }

    public void setDevDetail(String devDetail){
        this.devDetail = devDetail;
    }
    public void setDevPosition(String devPosition){
        this.devPosition = devPosition;
    }
    public void setDevAttr1(byte devattr){
        this.devAttr1 = devattr;
    }
    public void setDevSequence(byte devSequence){
        this.devSequence = devSequence;
    }
    public void setDevAttr2(byte devattr){
        this.devAttr2 = devattr;
    }
    public void setAttrStatus1(byte attrstatus){
        this.attrStatus1 = attrstatus;
    }
    public void setAttrStatus2(byte attrstatus){
        this.attrStatus2 = attrstatus;
    }
    public void setbCommandType(byte commandType){
        this.bCommandType = commandType;
    }
    public void setNickName(String name){
        this.nickName = name;
    }

    public byte getSeq(){
        return this.seq;
    }
    public String getCommandType(){
        return this.commandType;
    }
    public String getDevDetail(){
        return this.devDetail;
    }
    public String getDevPosition(){
        return this.devPosition;
    }
    public byte getDevSequence(){
        return this.devSequence;
    }
    public byte getDevAttr1(){
        return this.devAttr1;
    }
    public byte getDevAttr2(){
        return this.devAttr2;
    }
    public byte getAttrStatus1(){
        return this.attrStatus1;
    }
    public byte getAttrStatus2(){
        return this.attrStatus2;
    }
    public byte getbCommandType(){
        return this.bCommandType;
    }
    public String getNickName(){
        return this.nickName;
    }

    public static class PropertyItem{
        private String devProperty;
        private String propertyStatus;
        private String propertyStatus1;

        public PropertyItem(){

        }

        public void setDevProperty(String devProperty){
            this.devProperty = devProperty;
        }
        public String getDevProperty(){
            return this.devProperty;
        }
        public void setPropertyStatus(String propertyStatus){
            this.propertyStatus = propertyStatus;
        }
        public String getPropertyStatus(){
            return this.propertyStatus;
        }
        public void setPropertyStatus1(String propertyStatus){
            this.propertyStatus1 = propertyStatus;
        }
        public String getPropertyStatus1(){
            return this.propertyStatus1;
        }
    }
}
