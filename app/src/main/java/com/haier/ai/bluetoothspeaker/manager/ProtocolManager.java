package com.haier.ai.bluetoothspeaker.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.ApplianceDefine;
import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.DeviceConst;
import com.haier.ai.bluetoothspeaker.UnisoundDefine;
import com.haier.ai.bluetoothspeaker.bean.ControlBean;
import com.haier.ai.bluetoothspeaker.bean.RecvControlBean;
import com.haier.ai.bluetoothspeaker.model.RecordModel;
import com.haier.ai.bluetoothspeaker.net.SocketClient;
import com.haier.ai.bluetoothspeaker.util.BytesUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.haier.ai.bluetoothspeaker.Const.DOMAIN_MUSIC_STATUS;


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

    private final int tmpLen = 512;
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
//        initDatas();
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
        initDatas();
        //拼装数据
        initControlObj();

        int ret = 0;  //ret:0 播放语音并发送协议  -1：不执行 2：只发送协议

        if(operands.equals(Const.DOMAIN_AC)){ // 空调
            //handlerAC();
        }else if(operands.equals(Const.DOMAIN_DEVICE)){ //载体
            ret = handlerDevice();
        }else if(operands.equals(Const.DOMAIN_MUSIC_STATUS)){ //音乐
            ret = handlerDeviceMusic();
        }

        if (0 == ret) {
            formProtocol();
            sendData2Gateway();

            RecordModel.getInstance().playTTS("好的");
        }else if(2 == ret) {
            formProtocol();
            sendData2Gateway();
        }
    }

    private void initControlObj(){
        if(control == null){
            control = new ControlBean();
        }
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
     * 载体控制
     */
    public int handlerDevice(){
        int ret = 0;

        if(operator.equals(UnisoundDefine.ACT_OPS)){//开关控制
            if(TextUtils.isEmpty(value)){
                return -2;
            }

            if(value.equals(UnisoundDefine.ACT_OPEN)){
                ret = operatorOpen();
            }else if(value.equals(UnisoundDefine.ACT_CLOSE)){
                ret = operatorClose();
            }

        }else if(operator.equals(UnisoundDefine.ACT_ADJLIGHT)){
            ret = operatorAdjLight();
        }else if(operator.equals(UnisoundDefine.ACT_ADJVOICE)){
            ret = operatorAdjVoice();
        }else if(operator.equals(UnisoundDefine.ACT_DEVMODE)){
            ret = operatorDevMode();
        }

        return ret;
    }

    private int handlerDeviceMusic(){
        int ret = 0;
        if(operator.equals(UnisoundDefine.ACT_MUSICSYNC)){
            ret = operatorSyncMusic();
        }

        return ret;
    }

    final short low = 0;
    final short high = 1;
    final short min = 2;
    final short max = 3;
    private int operatorAdjLight(){
        short status = 0;

        switch (value){
            case UnisoundDefine.ACT_ADJHIGH:
                status = high;
                break;
            case UnisoundDefine.ACT_ADJLOW:
                status = low;
                break;
            case UnisoundDefine.ACT_MAXHIGH:
                status = max;
                break;
            case UnisoundDefine.ACT_MAXLOW:
                status = min;
                break;
            default:
                break;
        }

        control.setDevAttr(ApplianceDefine.MODE_LED_BRIGHTNESS);
        control.setAttrStatusShort(status);

        return 0;
    }

    private int operatorAdjVoice(){
        switch (value){
            case UnisoundDefine.ACT_ADJHIGH:
                if(DeviceConst.CURRENT_VOICE_LEVEL == DeviceConst.MAX_VOICE){
                    RecordModel.getInstance().playTTS("当前音量已经在最大");
                }else {
                    MusicPlayerManager.getInstance().adjustSystemVoiceHigh();
                }
                break;
            case UnisoundDefine.ACT_ADJLOW:
                MusicPlayerManager.getInstance().adjustSystemVoiceLow();
                break;
            case UnisoundDefine.ACT_MAXHIGH:
                if(DeviceConst.CURRENT_VOICE_LEVEL == DeviceConst.MAX_VOICE){
                    RecordModel.getInstance().playTTS("当前音量已经在最大");
                }else {
                    MusicPlayerManager.getInstance().setSystemVoiceMax();
                }
                break;
            case UnisoundDefine.ACT_MAXLOW:
                MusicPlayerManager.getInstance().setSystemVoiceMin();
                break;
            default:
                break;
        }

        if(MusicPlayerManager.getInstance().getMusicState() == Const.STATE_PAUSE){  //如果音乐处在暂停状态，调节完音量后继续播放
            MusicPlayerManager.getInstance().restartMusic();
        }

        RecordModel.getInstance().waitForWakeup();

        return -1;
    }

    private int operatorDevMode() {
        int ret = 0;
        short status = 0;

        switch (value){
            case UnisoundDefine.MODE_STANDARD:
                status = DeviceConst.LIGHT_MODE_STANDARD;
                break;
            case UnisoundDefine.MODE_READ:
                status = DeviceConst.LIGHT_MODE_READ;
                break;
            case UnisoundDefine.MODE_ROMANTIC:
                status = DeviceConst.LIGHT_MODE_ROMANTIC;
                break;
            case UnisoundDefine.MODE_SLEEP_LIGHT:
                status = DeviceConst.LIGHT_MODE_SLEEP;
                break;
            default:
                status = -1;
                break;
        }

        DeviceConst.LIGHT_STATUS = DeviceConst.LIGHT_STATUS_OPEN;
        //判断将设置的场景跟现在的情景是否相同，

        if(status == DeviceConst.CURRENT_LIGHT_MODE){
            RecordModel.getInstance().playTTS("灯光当前已经处于该模式");
            return -1;
        }else if(-1 == status){
            return -1;
        } else {
            DeviceConst.CURRENT_LIGHT_MODE  = status;
            control.setDevAttr(ApplianceDefine.MODE_LED_MODE);
            control.setAttrStatusShort(status);
        }

        return ret;
    }

    /**
     * 同步歌曲信息
     * @return
     */
    private int operatorSyncMusic(){
        switch (value){
            case UnisoundDefine.ACT_PLAYSTATUS:
                short status = -1;
                control.setDevAttr(ApplianceDefine.MODE_PLAY_MODE);
                if(TextUtils.isEmpty(time)){
                    return -1;
                }

                if(time.equals("play")){
                    status = 1;
                }else if(time.equals("pause")){
                    status = 0;
                }else if(time.equals("stop")){
                    status = 2;
                }

                if(-1 == status){
                    return -1;
                }
                control.setAttrStatusShort(status);
                break;
            case UnisoundDefine.ACT_SONGINFO:
                control.setDevAttr(ApplianceDefine.MODE_SONG_INFO);
                break;
            default:
                break;
        }

        return 2;
    }

    /**
     * 设置空调温度
     */
    public void operatorACSetTemp(){
        control.setDevAttr(ApplianceDefine.AIRCON_targetTemp);
        int index = value.indexOf("度");
        if(index != -1){
            value = value.substring(0, index);
        }

        control.setAttrStatusShort(Short.valueOf(value));
    }


    public void operatorACSetSpeed(){
        short status = 1;
        control.setDevAttr(ApplianceDefine.AIRCON_windSpeed);
        if(value.contains(UnisoundDefine.WIND_SPEED_LOW)){//低风
            status = 1;
        }else if(value.contains(UnisoundDefine.WIND_SPEED_MEDIUM)) {//中风
            status = 2;
        }
        else if(value.contains(UnisoundDefine.WIND_SPEED_HIGH)) {//高风
            status = 3;
        }
        else if(value.contains(UnisoundDefine.WIND_SPEED_AUTO)) {//自动
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
    public int operatorOpen(){
        if(DeviceConst.LIGHT_STATUS == DeviceConst.LIGHT_STATUS_OPEN){
            RecordModel.getInstance().playTTS("当前灯光已经在打开状态。");
            return -1;
        }
        control.setAttrStatusShort((short) 1);
        control.setDevAttr(ApplianceDefine.MODE_ONOFF_STATUS);

        DeviceConst.LIGHT_STATUS = DeviceConst.LIGHT_STATUS_OPEN;

        return 0;
    }

    /**
     *  关闭动作
     */
    public int operatorClose(){
        if(DeviceConst.LIGHT_STATUS == DeviceConst.LIGHT_STATUS_CLOSE){
            RecordModel.getInstance().playTTS("当前灯光已经在关闭状态。 ");
            return -1;
        }

        control.setAttrStatusShort((short) 0);
        control.setDevAttr(ApplianceDefine.MODE_ONOFF_STATUS);
        DeviceConst.LIGHT_STATUS = DeviceConst.LIGHT_STATUS_CLOSE;

        return 0;
    }

    /**
     * 拼装协议
     */
    private void formProtocol(){
        arrayLen = 0;
        String domain = null;

        //header(2 byte)
        tmpDatas[arrayLen++] = header;
        tmpDatas[arrayLen++] = header;
        //命令序列号(1 byte)
        tmpDatas[arrayLen++] = (byte) serialNum++;

        //命令类型 (1 byte)
        /*String operator = control.getOperator();
        if(operator.equals(UnisoundDefine.ACT_QUERY)){
            tmpDatas[arrayLen++] = ApplianceDefine.ORDER_QUERY;
        }else if(operator.equals(UnisoundDefine.ACT_OPS)
                ||operator.equals(UnisoundDefine.ACT_SETTEMP)
                ||operator.equals(UnisoundDefine.ACT_ADJTEMP)
                ||operator.equals(UnisoundDefine.ACT_SETSPEED)
                ||operator.equals(UnisoundDefine.ACT_SETMODE)
                ||operator.equals(UnisoundDefine.ACT_ADJLIGHT)
                ||operator.equals(UnisoundDefine.ACT_ADJVOICE)){
            tmpDatas[arrayLen++] = ApplianceDefine.ORDER_CONTROL;
        }*/

        tmpDatas[arrayLen++] = ApplianceDefine.ORDER_CONTROL; //控制

        //设备类型(1 byte)
        tmpDatas[arrayLen++] = ApplianceDefine.DEV_SPEAKER;

        //设备种类(1 byte)
        //origin_type = control.getOriginType();
        domain = control.getOperands();
        if(domain.equals(Const.DOMAIN_AC)){
            tmpDatas[arrayLen++] = ApplianceDefine.TYPE_AIRCONDITIONER;
        }else if(domain.equals(Const.DOMAIN_DEVICE)){
            tmpDatas[arrayLen++] = ApplianceDefine.TYPE_SPEAKER_LIGHT;
        }else if(domain.equals(Const.DOMAIN_MUSIC_STATUS)){
            tmpDatas[arrayLen++] = ApplianceDefine.TYPE_SPEAKER_MUSIC;
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
        if(domain.equals(Const.DOMAIN_AC)){//空调属性
            tmpDatas[arrayLen++] = ApplianceDefine.AIRCON_DEV;
        }else if(domain.equals(Const.DOMAIN_DEVICE)){
            tmpDatas[arrayLen++] = ApplianceDefine.SPEAKER_LIGHT_dev;
        }else if(domain.equals(Const.DOMAIN_MUSIC_STATUS)){
            tmpDatas[arrayLen++] = ApplianceDefine.SPEAKER_MUSIC_dev;
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

    /**
     * 收到的数据报解析
     * @param data
     * @param length
     * @return
     */
    public int parseProtocol(byte [] data, int length){
        Log.d(TAG, "parseProtocol: ");
        RecvControlBean recvControlBean = new RecvControlBean();
        //判读包头及包尾，确定是一个完整包
        if(data[0] !=header || data[1]!=header || data[length-1]!=end){
            Log.e("parse", "包不完整");
            return -1;
        }

        for(int i=2; i<length-1; i++){//dataPaylod
            //命令序列号(1 byte)
            recvControlBean.setSeq(data[i++]);
            //命令(1 byte)
            byte commandType = data[i++];
            recvControlBean.setbCommandType(commandType);


            //设备类型(1 byte)  0x00 未知 ;0x01 wifi家电; 0x02 60开关; 0x03 SmartCare
            byte devType = data[i++];
            if(devType == ApplianceDefine.DEV_WIFI){
                recvControlBean.setDevType("wifi家电");
            }
//            else if(devType == ApplianceDefine.DEV_60){
//                recvControlBean.setDevType("60开关");
//            }
            else if(devType == ApplianceDefine.DEV_SMARTCARE){
                recvControlBean.setDevType("SmartCare");
            }else if(devType == ApplianceDefine.DEV_UNKNOW){
                recvControlBean.setDevType("未知");
            }else if(devType == ApplianceDefine.DEV_INFRARED){
                recvControlBean.setDevType("InfraredAlarm");
            }else if(devType == ApplianceDefine.DEV_SPEAKER){
                recvControlBean.setDevType("speakerbox");
            }
            //设备种类(1 byte)  0x01 门磁; 0x02 水浸 ；0x10 空调; 0x21 灯光; 0x22 窗帘; 0x23
            byte devDetail = data[i++];
            if(devDetail == ApplianceDefine.TYPE_DOOR){
                recvControlBean.setDevDetail("门磁");
            }else if(devDetail == ApplianceDefine.TYPE_WATER){
                recvControlBean.setDevDetail("水浸");
            }else if(devDetail == ApplianceDefine.TYPE_AIRCONDITIONER){
                recvControlBean.setDevDetail("空调");
            }else if(devDetail == ApplianceDefine.TYPE_LIGHT){
                recvControlBean.setDevDetail("灯光");
            }else if(devDetail == ApplianceDefine.TYPE_CURTAIN){
                recvControlBean.setDevDetail("窗帘");
            }else if(devDetail == ApplianceDefine.TYPE_SCENE){
                recvControlBean.setDevDetail("模式");
            }else if(devDetail == ApplianceDefine.TYPE_RISCO){
                recvControlBean.setDevDetail("RISCO");
            }else if(devDetail == ApplianceDefine.TYPE_INFRARED){
                recvControlBean.setDevDetail("INFRARED");
            }else if(devDetail == ApplianceDefine.TYPE_WATER_HEATER){
                recvControlBean.setDevDetail("热水器");
            }else if(devDetail == ApplianceDefine.DANCE_DEV){
                recvControlBean.setDevDetail("dance");
            }
            //设备位置(1 byte)  0x01 客厅; 0x02 卧室; 0x03 厨房;......0xFF表示全部，0x00表示不确定
            byte devPositio = data[i++];
            if(devPositio == 0x01){
                recvControlBean.setDevPosition("客厅");
            }else if(devPositio == 0x02){
                recvControlBean.setDevPosition("卧室");
            }else if(devPositio == 0x03){
                recvControlBean.setDevPosition("厨房");
            }else if(devPositio == 0x00){
                recvControlBean.setDevPosition("未知");
            }else if(devPositio == 0xff){
                recvControlBean.setDevPosition("全部");
            }
            ////设备序号(1 byte)
            recvControlBean.setDevSequence(data[i++]);
            //mac + 预留 (16byte)
            int k=0;
            for(k=0; k<16; k++){
                i++;
            }
            //设备属性个数(1byte)
            byte count = data[i++];
            //
            byte [] tmp = new byte[4];
            for(k=0; k<count;k++){
                //设备属性 2Byte
                RecvControlBean.PropertyItem item = new RecvControlBean.PropertyItem();
                tmp[0] = data[i++];
                tmp[1] = data[i++];
                tmp[2] = data[i++];
                tmp[3] = data[i++];
                recvControlBean.setDevAttr1(tmp[0]);
                recvControlBean.setDevAttr2(tmp[1]);
                recvControlBean.setAttrStatus1(tmp[2]);
                recvControlBean.setAttrStatus2(tmp[3]);

                recvControlBean.propertyList.add(item);

            }
            //设备昵称长
            int nameLen = data[i++];
            String nickName = null;
            if(nameLen > 0) {
                //设备昵称
                byte[] bName = new byte[nameLen];
                System.arraycopy(data, i, bName, 0, nameLen);
                nickName = new String(bName);
                recvControlBean.setNickName(nickName);
            }
            //包尾

            handleCommand(recvControlBean);

        }
        return 0;
    }

    public void handleCommand(RecvControlBean recvControlBean){
        byte cmd = recvControlBean.getbCommandType();
        switch(cmd){
            case ApplianceDefine.ORDER_REPORT_STATE:
                if(recvControlBean.getDevType().equals("speakerbox")){

                    byte devattr1 = recvControlBean.getDevAttr1();
                    byte devattr2 = recvControlBean.getDevAttr2();

                    byte [] status = new byte[2];
                    status[0] = recvControlBean.getAttrStatus1();
                    status[1] = recvControlBean.getAttrStatus2();

                    short attrStatus = BytesUtil.getShort(status);

                    switch (devattr2){
                        case ApplianceDefine.MODE_VOLUME://控制音量
                            if(0 == attrStatus){//减小
                                MusicPlayerManager.getInstance().adjustSystemVoiceLow();
                            }else if(1 == attrStatus){//增加
                                MusicPlayerManager.getInstance().adjustSystemVoiceHigh();
                            }
                            break;
                        case ApplianceDefine.MODE_PLAY_MODE:
                            Log.d(TAG, "handleCommand: playmusic:" + attrStatus);
                            if(1 == attrStatus){//
                                if(DeviceConst.MUSIC_STATE == Const.STATE_PLAYING){
                                    MusicPlayerManager.getInstance().pauseMusic();
                                }else if(DeviceConst.MUSIC_STATE == Const.STATE_STOP) {
                                    MusicPlayerManager.getInstance().playRandomUrlMusic();
                                }
                                else if(DeviceConst.MUSIC_STATE == Const.STATE_PAUSE){
                                    MusicPlayerManager.getInstance().restartMusic();
                                }

                            }
                            break;
                        case ApplianceDefine.MODE_PLAY_CONTROL:
                            Log.d(TAG, "handleCommand: MODE_PLAY_CONTROL:" + attrStatus);
                            if(1 == attrStatus){// 上一首
                                MusicPlayerManager.getInstance().playRandomUrlMusic();
                            }else if(2 == attrStatus){//
                                MusicPlayerManager.getInstance().playRandomUrlMusic();
                            }
                            break;
                        case ApplianceDefine.MODE_PLAY_SONG:
                            String song = recvControlBean.getNickName();
                            Log.d(TAG, "handleCommand: song name:" + song);
                            //EventBus.getDefault().post(new LocalMusicEvent(song));
                            MusicPlayerManager.getInstance().playLocalMusic(song);
                            //todo 发送播放事件
                            //syncMusicStatus(0, "play");
                            break;
                    }
                }
                break;

        }
    }

    /**
     * 与app同步播放状态
     * @param type 0: 音乐状态 1 同步载体歌曲信息
     * @param name pause play  stop； 歌曲信息
     */
    public void syncMusicStatus(int type, String name){
        switch (type) {
            case 0:
                this.value = UnisoundDefine.ACT_PLAYSTATUS;
                this.time = name; //暂停，播放，停止
                break;
            case 1:
                this.value = UnisoundDefine.ACT_SONGINFO;
                this.device = name; //（nickname）
                break;
            default:
                break;
        }

        this.operands = DOMAIN_MUSIC_STATUS;  //音箱音乐
        this.operator = UnisoundDefine.ACT_MUSICSYNC;


        convertVoice2Data();
    }
}
