package com.haier.ai.bluetoothspeaker.manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.haier.ai.bluetoothspeaker.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * author: qu
 * date: 16-11-9
 * introduce:
 */

public class SpeakerAlarmManager {
    private final String TAG = "SpeakerAlarmManager";
    public  final static String ALARM_ACTION = "com.loonggg.alarm.clock";

    public static SpeakerAlarmManager sAlarmManager;
    public static MediaPlayer mediaPlayer;

    public SpeakerAlarmManager(){

    }

    public static SpeakerAlarmManager getInstance(){
        if(sAlarmManager == null){
            sAlarmManager = new SpeakerAlarmManager();
        }

        return sAlarmManager;
    }

    public void setAlarmTime(Context context, long timeInMillis, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int interval = (int) intent.getLongExtra("intervalMillis", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        }
    }

    public void cancelAlarm(Context context, String action, int id) {
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }

    /**
     * @param flag            周期性时间间隔的标志,flag = 0 表示一次性的闹钟, flag = 1 表示每天提醒的闹钟(1天的时间间隔),flag = 2
     *                        表示按周每周提醒的闹钟（一周的周期性时间间隔）
     * @param hour            时
     * @param minute          分
     * @param id              闹钟的id
     * @param week            week=0表示一次性闹钟或者按天的周期性闹钟，非0 的情况下是几就代表以周为周期性的周几的闹钟
     * @param tips            闹钟提示信息
     * @param soundOrVibrator 2表示声音和震动都执行，1表示只有铃声提醒，0表示只有震动提醒
     */
    public void setAlarm(Context context, int flag, int hour, int minute, int id, int
            week, String tips, int soundOrVibrator) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long intervalMillis = 0;
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                (Calendar.DAY_OF_MONTH), hour, minute, 10);
        if (flag == 0) {
            intervalMillis = 0;
        } else if (flag == 1) {
            intervalMillis = 24 * 3600 * 1000;
        } else if (flag == 2) {
            intervalMillis = 24 * 3600 * 1000 * 7;
        }
        Intent intent = new Intent(ALARM_ACTION);
        intent.putExtra("intervalMillis", intervalMillis);
        intent.putExtra("msg", tips);
        intent.putExtra("id", id);
        intent.putExtra("soundOrVibrator", soundOrVibrator);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, calMethod(week, calendar.getTimeInMillis()),
                    intervalMillis, sender);
        } else {
            if (flag == 0) {
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            } else {
                am.setRepeating(AlarmManager.RTC_WAKEUP, calMethod(week, calendar.getTimeInMillis
                        ()), intervalMillis, sender);
            }
        }
    }


    /**
     * @param weekflag 传入的是周几
     * @param dateTime 传入的是时间戳（设置当天的年月日+从选择框拿来的时分秒）
     * @return 返回起始闹钟时间的时间戳
     */
    private long calMethod(int weekflag, long dateTime) {
        long time = 0;
        //weekflag == 0表示是按天为周期性的时间间隔或者是一次行的，weekfalg非0时表示每周几的闹钟并以周为时间间隔
        if (weekflag != 0) {
            Calendar c = Calendar.getInstance();
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (1 == week) {
                week = 7;
            } else if (2 == week) {
                week = 1;
            } else if (3 == week) {
                week = 2;
            } else if (4 == week) {
                week = 3;
            } else if (5 == week) {
                week = 4;
            } else if (6 == week) {
                week = 5;
            } else if (7 == week) {
                week = 6;
            }

            if (weekflag == week) {
                if (dateTime > System.currentTimeMillis()) {
                    time = dateTime;
                } else {
                    time = dateTime + 7 * 24 * 3600 * 1000;
                }
            } else if (weekflag > week) {
                time = dateTime + (weekflag - week) * 24 * 3600 * 1000;
            } else if (weekflag < week) {
                time = dateTime + (weekflag - week + 7) * 24 * 3600 * 1000;
            }
        } else {
            if (dateTime > System.currentTimeMillis()) {
                time = dateTime;
            } else {
                time = dateTime + 24 * 3600 * 1000;
            }
        }
        return time;
    }

    /**
     * 获取系统铃音列表
     * @return
     */
    public List<String> getRingtongList(){
        List<String> ringList = new ArrayList<String>();

        RingtoneManager manager = new RingtoneManager(App.getInstance());

        manager.setType(RingtoneManager.TYPE_ALARM);

        Cursor cursor = manager.getCursor();

        if(cursor.moveToFirst()){

            do{

                ringList.add(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));

            }while(cursor.moveToNext());

        }

        cursor.close();

        return ringList;
    }

    /**
     * 开始响铃
     */
    public void startAlarm() {
        //// TODO: 16-12-20 选择指定的铃音播放
        mediaPlayer = MediaPlayer.create(App.getInstance(), getSystemDefultRingtoneUri());
        mediaPlayer.setLooping(true);
        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    //获取系统默认铃声的Uri
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(App.getInstance(),
                RingtoneManager.TYPE_RINGTONE);
    }

    /**
     * 停止响铃
     */
    public void stopAlarm(){
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public Uri getSelectRing(String selRing){
        if(TextUtils.isEmpty(selRing)){
            return getSystemDefultRingtoneUri();
        }

        RingtoneManager manager = new RingtoneManager(App.getInstance());

        manager.setType(RingtoneManager.TYPE_ALARM);

        Cursor cursor = manager.getCursor();

        /*List<String> ringList = getRingtongList();

        if(ringList==null || ringList.size()==0){
            return getSystemDefultRingtoneUri();
        }*/

        if(cursor.moveToFirst()){

            do{
                String ring = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);

                if(selRing.equals(ring)){
                    int pos = cursor.getPosition();
                    return manager.getRingtoneUri(pos);
                }
            }while(cursor.moveToNext());

        }

        return getSystemDefultRingtoneUri();
    }

    /////////////////////
   /* private void setClock() {
        if (time != null && time.length() > 0) {
            String[] times = time.split(":");
            if (cycle == 0) {//是每天的闹钟
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            } if(cycle == -1){//是只响一次的闹钟
                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            }else {//多选，周几的闹钟
                String weeksStr = parseRepeat(cycle, 1);
                String[] weeks = weeksStr.split(",");
                for (int i = 0; i < weeks.length; i++) {
                    AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
                            .parseInt(times[1]), i, Integer.parseInt(weeks[i]), "闹钟响了", ring);
                }
            }
            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();
        }

    }*/
}
