package com.haier.ai.bluetoothspeaker.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.App;
import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.DeviceConst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * author: qu
 * date: 17-1-16
 * introduce: 音乐播放器管理类
 */

public class MusicPlayerManager implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener{
    private final String TAG = "MusicPlayerManager";

    public static MusicPlayerManager sMusicPlayerManager;
    private static AudioManager sAudioManager;
    private static MediaPlayer sMediaPlayer;
    private static final int minVoice = 0;

    private static Calendar sCalendar;
    private static String year;
    private static String month;
    private static String day;
    private static String week;
    private static int musicState = Const.STATE_STOP;       //音乐播放状态
    private List<String> netMusicList = null;               //云端歌曲列表
    private List<String> localMusicList = null;             //本地歌曲列表


    public MusicPlayerManager(){
        musicState = Const.STATE_STOP;

        getAudioInfo();

        getDateInfo();

        initLocalMusicList();
    }

    private void getDateInfo(){
        sCalendar = Calendar.getInstance();

        year = "" + sCalendar.get(Calendar.YEAR);
        month = "" + sCalendar.get(Calendar.MONTH);
        day = "" + sCalendar.get(Calendar.DAY_OF_MONTH);
        int weekIndex = sCalendar.get(Calendar.DAY_OF_WEEK);
        switch (weekIndex){
            case 1:
                week = "星期天";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
            default:
                break;
        }
    }

    private void getAudioInfo(){
        sAudioManager = (AudioManager) App.getInstance().getSystemService(Context.AUDIO_SERVICE);

        DeviceConst.MAX_VOICE = sAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public static MusicPlayerManager getInstance(){
        if(sMusicPlayerManager == null){
            sMusicPlayerManager = new MusicPlayerManager();
        }

        return sMusicPlayerManager;
    }

    /**
     * 播放云端音乐
     * @param url
     */
    public void playUrlMusic(String url){
        if(TextUtils.isEmpty(url)){
            return;
        }

        initMediaPlayer();

        try {
            sMediaPlayer.reset();
            // 设置数据源 "http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3"
            sMediaPlayer.setDataSource(url); // 设置数据源

            sMediaPlayer.prepare(); // prepare自动播放
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 暂停播放
     */
    public void pauseMusic(){
        if(sMediaPlayer == null)
            return;

//        int pos = sMediaPlayer.getCurrentPosition();
//        Log.d(TAG, "pauseMusic: pos:" + pos);
        if(sMediaPlayer.isPlaying()){
            sMediaPlayer.pause();
            musicState = Const.STATE_PAUSE;
        }
    }

    /**
     * 继续播放
     */
    public void restartMusic(){
        if(sMediaPlayer == null)
            return;

        sMediaPlayer.start();
        musicState = Const.STATE_PLAYING;
    }

    /**
     * 停止播放
     */
    public void stopMusic(){
        if(sMediaPlayer == null){
            return ;
        }

        if(sMediaPlayer.isPlaying()){
            sMediaPlayer.stop();
            sMediaPlayer.release();
            sMediaPlayer = null;
            musicState = Const.STATE_STOP;
        }
    }

    private void initMediaPlayer(){
        if(sMediaPlayer == null){
            sMediaPlayer = new MediaPlayer();
        }

        sMediaPlayer.setOnPreparedListener(this);
        sMediaPlayer.setOnBufferingUpdateListener(this);
        sMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, "onBufferingUpdate: percent:" + percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: music play complated");
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(sMediaPlayer != null){
            sMediaPlayer.start();
            musicState = Const.STATE_PLAYING;
        }
    }


    /**
     * 播放下一首
     */
    public void playNextMusic(){

    }

    /**
     * 播放上一首
     */
    public void playPreviousMusic(){

    }

    public int getMusicState(){
        return musicState;
    }

    /**
     * 播放本地音乐
     * @param song
     */
    public boolean playLocalMusic(String song){
        if(TextUtils.isEmpty(song)){
            return false;
        }

        int index = hasLocalMusic(song);
        if(index == -1){
            return false;
        }

        String songPath = getLocalMusicPath() + localMusicList.get(index);

        sMediaPlayer = new MediaPlayer();

        try {
            sMediaPlayer.setDataSource(songPath);
            sMediaPlayer.prepare();
            sMediaPlayer.start();
            musicState = Const.STATE_PLAYING;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 初始化本地音乐播放列表
     */
    private void initLocalMusicList(){
        if(localMusicList == null){
            localMusicList = new ArrayList<>();
        }

        //添加本地歌曲
        localMusicList.add("白桦林.mp3");
        localMusicList.add("彼岸花.mp3");
        localMusicList.add("彩云之南.mp3");
        localMusicList.add("但愿人长久.mp3");
        localMusicList.add("蝶恋.mp3");
        localMusicList.add("独角戏.mp3");
        localMusicList.add("饿狼传说.mp3");
        localMusicList.add("刚刚好.mp3");
        localMusicList.add("寒衣调.mp3");
        localMusicList.add("黄豆.mp3");
        localMusicList.add("回到拉萨.mp3");
        localMusicList.add("记事本.mp3");
        localMusicList.add("酒干倘卖无.mp3");
        localMusicList.add("酒醉的探戈.mp3");
        localMusicList.add("就是我.mp3");
        localMusicList.add("看月亮爬上来.mp3");
        localMusicList.add("老街.mp3");
        localMusicList.add("摩天轮.mp3");
        localMusicList.add("女人花.mp3");
        localMusicList.add("朋友.mp3");
        localMusicList.add("骑士.mp3");
        localMusicList.add("时间都去哪儿了.mp3");
        localMusicList.add("涛声依旧.mp3");
        localMusicList.add("听海.mp3");
        localMusicList.add("同桌的你.mp3");
        localMusicList.add("忘记你我做不到.mp3");
        localMusicList.add("吻别.mp3");
        localMusicList.add("下沙.mp3");
        localMusicList.add("映山红.mp3");
        localMusicList.add("雨蝶.mp3");
        localMusicList.add("雨夜花.mp3");
        localMusicList.add("终于等到你.mp3");
        localMusicList.add("最美的太阳.mp3");
    }

    /**
     * 播放列表中添加歌曲
     * @param songUrl
     */
    public void addNetMusic(String songUrl){
        if(TextUtils.isEmpty(songUrl)){
            return;
        }

        if (netMusicList == null){
            netMusicList = new ArrayList<>();
        }

        netMusicList.add(songUrl);
    }

    public String getLocalMusicPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/music/";
    }

    public int hasLocalMusic(String song){
        if(localMusicList == null){
            return -1;
        }

        for(String sel : localMusicList){
            if(sel.contains(song)){
                return localMusicList.indexOf(sel);
            }
        }

        return -1;
    }

    public void adjustSystemVoiceLow(){
        sAudioManager.adjustStreamVolume(
                AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_LOWER,
                AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);

        DeviceConst.CURRENT_VOICE_LEVEL = getCurrentVoice();
    }

    public void adjustSystemVoiceHigh(){
        sAudioManager.adjustStreamVolume(
                AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_RAISE,
                AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);

        DeviceConst.CURRENT_VOICE_LEVEL = getCurrentVoice();
    }

    public void setSystemVoiceMax(){
        sAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, DeviceConst.MAX_VOICE, 0);
        DeviceConst.CURRENT_VOICE_LEVEL = DeviceConst.MAX_VOICE;
    }

    public void setSystemVoiceMin(){
        sAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        DeviceConst.CURRENT_VOICE_LEVEL = 0;
    }

    private int getCurrentVoice(){
        if(sAudioManager == null){
            sAudioManager = (AudioManager) App.getInstance().getSystemService(Context.AUDIO_SERVICE);
        }

        return sAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public String getTodayDate(){
        return "今天是" + year + "年" + month + "月" + day + "号";
    }

    public String getTodayWeek(){
        return "今天" + week + "哦";
    }
}
