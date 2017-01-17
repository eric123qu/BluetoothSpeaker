package com.haier.ai.bluetoothspeaker;

/**
 * author: qu
 * date: 17-1-16
 * introduce:
 */

public class lightControlJni {
    /**
     *
     * @param cmd
     *  // 1,蓝色闪烁20hz 配对模式
    // 2,红色闪烁20hz 网络断开
    // 3,绿色常亮 运行状态
    // 4,白色常亮 语音待命
    // 5,白灯闪烁 2hz 语言命令执行
     * @return
     */
    public native int notifyLightContrl(int cmd);//控制下发

    public native int notifyLightInit();//使用前先初始化

    public native int notifyLightClose();//使用后释放资源
}
