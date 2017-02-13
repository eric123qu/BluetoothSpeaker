package com.haier.ai.bluetoothspeaker.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.ApplianceDefine;
import com.haier.ai.bluetoothspeaker.UnisoundDefine;
import com.haier.ai.bluetoothspeaker.bean.ControlBean;
import com.haier.ai.bluetoothspeaker.net.SocketClient;
import com.haier.ai.bluetoothspeaker.util.BytesUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by qx on 17-1-5.
 * 家电控制类
 */

public class ProtocolManager {
    private final String TAG = "ProtocolManager";
    private static ProtocolManager sProtocolManager;
    private static int serialNum = 1;
    private static boolean bQueryWmLeftTime = false;
    private ExecutorService executorService;

    private final int tmpLen = 64;
    private final int hwSingleTemp = 45;
    private final int hwTwoTemp = 60;
    private final int hwThreeTemp = 75;
    private final byte header = (byte) 0xFE;
    private final byte end = (byte)0xFD;

    private byte [] tmpDatas;
    private byte [] sendData;
    private byte [] hk60Data;
    private int arrayLen;//数组长度
    private int hwTemperature;

    private String operands;
    private String operator;
    private String value;
    private String origin; //控制家电类型
    private String time;
    private String device;//nickname
    private ControlBean control;
    private Context context;
    private boolean isDialog;


    public ProtocolManager(){
        if(executorService == null){
            executorService = Executors.newCachedThreadPool();
        }
    }

    public static ProtocolManager getInstance(){
        if (sProtocolManager == null){
            sProtocolManager = new ProtocolManager();
        }

        return sProtocolManager;
    }

    public ProtocolManager(Context context, String operands, String operator,String value){
        initDatas();
        this.context = context;
        this.operands = operands;
        this.operator = operator;
        this.value = value;
    }

    /**
     * 初始化语音信息
     * @param operands
     * @param operator
     * @param value
     */
    public void setProtocolInfo(String operands, String operator,String value, boolean isDialog){
        initDatas();
        this.operands = operands;
        this.operator = operator;
        this.value = value;
        this.isDialog = isDialog;
    }

    public void initDatas(){
        if(tmpDatas == null){
            tmpDatas = new byte[tmpLen];
        }

        for(int i=0; i<tmpLen; i++){
            tmpDatas[i] = (byte) 0x00;
        }

        hk60Data = new byte[2];

        control = new ControlBean();
    }

    /**
     * 语音数据转化成控制协议
     */
    public void convertVoice2Data(){
        //拼装数据
        initControlObj();

        if(operands.equals(UnisoundDefine.OBJ_AC)){ // 空调
            handlerAC();
        }

        /*if(operator.equals(UnisoundDefine.ACT_OPEN)){//打开
            operatorOpen();
        }else if(operator.equals(UnisoundDefine.ACT_CLOSE)){
//            operatorClose();
        }else if(operator.equals(UnisoundDefine.ACT_STOP)) {
//            operatorStop();
        }else if(operator.equals(UnisoundDefine.ACT_SET)){
//            operatorSet();
        }else if(operator.equals(UnisoundDefine.ACT_UNSET)) {//取消设置
//            operatorUnset();
        }else if(operator.equals(UnisoundDefine.ACT_START)){
//            operatorStart();
        }else if(operator.equals(UnisoundDefine.ACT_PAUSE)){
//            operatorPause();
        }else if(operator.equals(UnisoundDefine.ACT_DECREASE)){//减少
//            operatorDecrease();
        }else if(operator.equals(UnisoundDefine.ACT_INCREASE)){//增大
//            operatorIncrease();
        }else if(operator.equals(UnisoundDefine.ACT_QUERY)){//查询
//            operatorQuery();
        }else if(operator.equals(UnisoundDefine.ACT_STANDBY)){//待机

        }else if(operator.equals(UnisoundDefine.ACT_HIBERATE )){//休眠

        }else if(operator.equals(UnisoundDefine.ACT_NEXT)){//下一首歌
//            operatorNext();
        }else if(operator.equals(UnisoundDefine.ACT_PERCEIVE)){
//            operatorPerceive();
        }else if(operator.equals(UnisoundDefine.ACT_BATH)){
            //operatorBath();
        }*/


        formProtocol();
        sendData2Gateway();
    }

    private void initControlObj(){
        control.setOperator(operator);
        control.setOperands(operands);
        control.setValue(value);
        control.setNickName(device);
        control.setOriginType(origin);
        control.setTime(time);
    }

    /**
     * 空调控制
     */
    public void handlerAC(){
        if(operator.equals(UnisoundDefine.ACT_OPS)){//开关控制
            if(TextUtils.isEmpty(value)){
                return;
            }

            if(value.equals(UnisoundDefine.ACT_OPEN)){
                operatorOpen();
            }else if(value.equals(UnisoundDefine.ACT_CLOSE)){
                operatorClose();
            }

        }else if(operator.equals(UnisoundDefine.ACT_SETTEMP)){
            operatorACSetTemp();
        }else if(operator.equals(UnisoundDefine.ACT_ADJTEMP)){
            operatorACAdjTemp();
        }else if(operator.equals(UnisoundDefine.ACT_SETSPEED)){
            operatorACSetSpeed();
        }else if(operator.equals(UnisoundDefine.ACT_SETMODE)){
            operatorACSetMode();
        }
    }

    /**
     * 设置空调温度
     */
    public void operatorACSetTemp(){
        control.setDevAttr(ApplianceDefine.AIRCON_targetTemp);
        int index = value.indexOf("度");
        if(index != -1){
            value = value.substring(0, index-1);
        }

        control.setAttrStatusShort(Short.valueOf(value));
    }


    public void operatorACSetSpeed(){
        short status = 1;
        control.setDevAttr(ApplianceDefine.AIRCON_windSpeed);
        if(value.equals(UnisoundDefine.WIND_SPEED_LOW)){//低风
            status = 1;
        }else if(value.equals(UnisoundDefine.WIND_SPEED_MEDIUM)) {//中风
            status = 2;
        }
        else if(value.equals(UnisoundDefine.WIND_SPEED_HIGH)) {//高风
            status = 3;
        }
        else if(value.equals(UnisoundDefine.WIND_SPEED_AUTO)) {//自动
            status = 4;
        }
        control.setAttrStatusShort(status);
    }

    public void operatorACSetMode(){
        short status = 1;

        if(value.equals(UnisoundDefine.MODE_AUTO)){//自动
            control.setDevAttr(ApplianceDefine.AIRCON_operation);
            status = 1;
        }else if(value.equals(UnisoundDefine.MODE_COOL)) {//制冷
            control.setDevAttr(ApplianceDefine.AIRCON_operation);
            status = 2;
        }
        else if(value.equals(UnisoundDefine.MODE_HEAT)) {//制热
            control.setDevAttr(ApplianceDefine.AIRCON_operation);
            status = 3;
        }
        else if(value.equals(UnisoundDefine.MODE_AIR_SUPPLY)) {//送风
            control.setDevAttr(ApplianceDefine.AIRCON_operation);
            status = 4;
        }else if(value.equals(UnisoundDefine.MODE_WETTED)) {//除湿
            control.setDevAttr(ApplianceDefine.AIRCON_operation);
            status = 5;
        }

        control.setAttrStatusShort(status);
    }

    public void operatorACAdjTemp(){
        control.setDevAttr(ApplianceDefine.AIRCON_tempAutoControl);
        short status = 0;

        if(TextUtils.isEmpty(value)){
            return;
        }

        if(value.equals("调高")){
            status = 1;
        }else if(value.equals("调低")){
            status = 0;
        }

        control.setAttrStatusShort(status);
    }

    /**
     * 打开动作
     */
    public void operatorOpen(){
        control.setAttrStatusShort((short) 1);
        control.setDevAttr(ApplianceDefine.AIRCON_status);
    }

    /**
     *  关闭动作
     */
    public void operatorClose(){
        control.setAttrStatusShort((short) 0);
        control.setDevAttr(ApplianceDefine.AIRCON_status);
    }

    /**
     * 拼装协议
     */
    private void formProtocol(){
        arrayLen = 0;
        String origin_type = null;

        //header(2 byte)
        tmpDatas[arrayLen++] = header;
        tmpDatas[arrayLen++] = header;
        //命令序列号(1 byte)
        tmpDatas[arrayLen++] = (byte) serialNum++;

        //命令类型 (1 byte)
        String operator = control.getOperator();
        if(operator.equals(UnisoundDefine.ACT_QUERY)){
            tmpDatas[arrayLen++] = ApplianceDefine.ORDER_QUERY;
        }else if(operator.equals(UnisoundDefine.ACT_OPS)
                ||operator.equals(UnisoundDefine.ACT_SETTEMP)
                ||operator.equals(UnisoundDefine.ACT_ADJTEMP)
                ||operator.equals(UnisoundDefine.ACT_SETSPEED)
                ||operator.equals(UnisoundDefine.ACT_SETMODE)){
            tmpDatas[arrayLen++] = ApplianceDefine.ORDER_CONTROL;
        }

        //设备类型(1 byte)
        tmpDatas[arrayLen++] = ApplianceDefine.GENERAL_UNKNOW;

        //设备种类(1 byte)
        //origin_type = control.getOriginType();
        origin_type = control.getOperands();
        if(origin_type.equals(UnisoundDefine.OBJ_AC)){
            tmpDatas[arrayLen++] = ApplianceDefine.TYPE_AIRCONDITIONER;
        }

        //设备位置(1 byte)
        tmpDatas[arrayLen++] = ApplianceDefine.LOCATION_UNKONW;

        //设备序号(1 byte)
        tmpDatas[arrayLen++] = ApplianceDefine.GENERAL_UNKNOW;
        //设备ID + 预留 （16位0）
        for(int i=8; i<24; i++){
            tmpDatas[arrayLen++] = (byte)0x00;
        }
        //设备属性个数
        tmpDatas[arrayLen++] = (byte)0x01;
        //设备属性(2 byte)
        if(origin_type.equals(UnisoundDefine.OBJ_AC)){//空调属性
            tmpDatas[arrayLen++] = ApplianceDefine.AIRCON_DEV;
        }

        tmpDatas[arrayLen++] = control.getDevAttr();
        //属性状态(2 byte)
        byte [] status = BytesUtil.shortToByteArray(control.getAttrStatusShort());
        tmpDatas[arrayLen++] = status[0];
        tmpDatas[arrayLen++] = status[1];

        //设备昵称长(1 byte)
        if(TextUtils.isEmpty(control.getNickName())){
            tmpDatas[arrayLen++] = (byte)0x00;
        }else{
            tmpDatas[arrayLen++] = (byte)(control.getNickName().length()*3);
            // Log.d("form_protocol", "nickname len:"+control.getNickName().length());
            //设备昵称
            byte [] bName = control.getNickName().getBytes();
            for(int len=0; len<bName.length; len++){
                tmpDatas[arrayLen++] = bName[len];
            }
        }


        //end
        tmpDatas[arrayLen++] = end;

        //copy data
        sendData = new byte[arrayLen];

        for(int k=0; k<arrayLen; k++){
            sendData[k] = tmpDatas[k];
        }

        //System.arraycopy(tmpDatas, 0, sendData, 0, arrayLen);
        for(int i=0; i<tmpLen; i++){
            tmpDatas[i] = (byte) 0x00;
        }

        String sData = BytesUtil.byte2Hex(sendData);
        Log.d("protocol", "sendData:"+sData);
    }

    /**
     * 向网关发送数据
     */
    public void sendData2Gateway(){
        if(executorService == null){
            executorService = Executors.newCachedThreadPool();
        }

        executorService.execute(networkTask);
        //new Thread(networkTask).start();
    }

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            SocketClient socketClient = new SocketClient();
            socketClient.socketSend(sendData);
        }
    };
}
