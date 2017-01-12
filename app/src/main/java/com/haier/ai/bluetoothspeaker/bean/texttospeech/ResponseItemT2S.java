/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.haier.ai.bluetoothspeaker.bean.texttospeech;

/**
 * Created by Joyson on 2016/8/8.
 * 用于主页面item显示的javabean
 * 实际上和ResponseT2S,区别是添加了downWavFilePath
 */
public class ResponseItemT2S {
    public ResponseT2S responseT2S;
    public String wavFilePath;

    public ResponseItemT2S(ResponseT2S responseT2S, String wavFilePath) {
        this.responseT2S = responseT2S;
        this.wavFilePath = wavFilePath;
    }
}
