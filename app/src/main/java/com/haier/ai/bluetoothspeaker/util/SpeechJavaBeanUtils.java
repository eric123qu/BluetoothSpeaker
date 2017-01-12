/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.haier.ai.bluetoothspeaker.util;

import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.bean.speechtotext.ResponseS2TNew;

/**
 * 文本转语音 工具类
 * Created by Joyson on 2016/8/8.
 */
public class SpeechJavaBeanUtils {


    /**
     * 语音-->文本 所需要的内容，解析
     * @param message 返回json内容
     * @return
     */
    public static String S2TgetText(String message){
        ResponseS2TNew responseS2TNew=JsonUtil.parseJsonToBean(message,ResponseS2TNew.class);
            //需要得到文本信息

        //判断返回码
        if(responseS2TNew.getRetCode().equals(Const.RET_CODE_SUCESS)){
            if(responseS2TNew.getData() == null){
                return null;
            }else
                return responseS2TNew.getData().getAsrResult().get(0).getRecogniationText();
        }else{
            return null;
        }

    }
}
