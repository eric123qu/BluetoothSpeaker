package com.haier.ai.bluetoothspeaker.manager;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: qu
 * date: 17-1-16
 * introduce: 音乐播放器管理类
 */

public class MusicPlayerManager implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener{
    private final String TAG = "MusicPlayerManager";

    public static MusicPlayerManager sMusicPlayerManager;
    private static MediaPlayer sMediaPlayer;
    private static int musicState = Const.STATE_STOP;       //音乐播放状态
    private List<String> netMusicList = null;               //云端歌曲列表
    private List<String> localMusicList = null;             //本地歌曲列表


    public MusicPlayerManager(){
        musicState = Const.STATE_STOP;
        initLocalMusicList();
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
    private void playUrlMusic(String url){
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
    private void pauseMusic(){
        if(sMediaPlayer == null)
            return;

        if(sMediaPlayer.isPlaying()){
            sMediaPlayer.pause();
            musicState = Const.STATE_PAUSE;
        }
    }

    /**
     * 继续播放
     */
    private void restartMusic(){
        if(sMediaPlayer == null)
            return;

        sMediaPlayer.start();
        musicState = Const.STATE_PLAYING;
    }

    /**
     * 停止播放
     */
    private void stopMusic(){
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
    private void playNextMusic(){

    }

    /**
     * 播放上一首
     */
    private void playPreviousMusic(){

    }

    private int getMusicState(){
        return musicState;
    }

    /**
     * 播放本地音乐
     * @param song
     * @param singer
     */
    private void playLocalMusic(String song ,String singer){

    }

    /**
     * 初始化本地音乐播放列表
     */
    private void initLocalMusicList(){
        if(localMusicList == null){
            localMusicList = new ArrayList<>();
        }

        //添加本地歌曲
    }

}
