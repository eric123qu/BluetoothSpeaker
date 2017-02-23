package com.haier.ai.bluetoothspeaker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haier.ai.bluetoothspeaker.R;
import com.haier.ai.bluetoothspeaker.event.NluEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeResultEvent;
import com.haier.ai.bluetoothspeaker.event.ReconizeStatusEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity{
    private final String TAG = "MainActivity";
    private Button open;
    private Button close;
    private String ssid = "haierubic_rgzn";
    private String passwd = "haierubic123";
    private String url;


    TextView mTvStatusContact;
    TextView mTvResultContent;
    TextView mTvNlu;
    EditText mEtTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initView();

        /*boolean bret = SpeakerBluetoothManager.getInstance().openBluetooth();

//        SpeakerBluetoothManager.getInstance().enablingDiscoverability();

        List<String> list = SpeakerAlarmManager.getInstance().getRingtongList();

        Log.d(TAG, "onCreate: " + list.toString());

        WifiDevManager.getInstance().openWifi();

        ScanWifiBean bean = new ScanWifiBean();
        bean.setSsid(ssid);
        bean.setPasswd(passwd);
        bean.setSecurityType(2);
        WifiDevManager.getInstance().connectWifi(bean);*/


        //Uri uri = SpeakerAlarmManager.getInstance().getSelectRing("Ripple");

        //Log.e(",ain", "onClick: " + uri.toString());
        /*Intent wakeupService = new Intent(this, WakeupService.class);
        startService(wakeupService);

        Intent intent1 = new Intent(this, ReconizeService.class);
        this.startService(intent1);

        Intent intentScoket = new Intent(this, SocketService.class);
        this.startService(intentScoket);*/

        initShowInfo();

        EventBus.getDefault().register(this);
    }

    private void initShowInfo(){
        mTvStatusContact = (TextView) findViewById(R.id.tv_status_contact);
        mTvResultContent = (TextView) findViewById(R.id.tv_result_content);
        mTvNlu = (TextView) findViewById(R.id.tv_nlu);
        mEtTts = (EditText) findViewById(R.id.et_tts);
    }

   /* private void initView(){
        open = (Button) findViewById(R.id.button_open);
        close = (Button) findViewById(R.id.button_close);
        Button music = (Button) findViewById(R.id.btn_music);
        Button play = (Button) findViewById(R.id.btn_play);
        Button pause = (Button) findViewById(R.id.btn_pause);
        Button restart = (Button) findViewById(R.id.btn_restart);

        open.setOnClickListener(this);
        close.setOnClickListener(this);
        music.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        restart.setOnClickListener(this);
    }*/

   /* @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_open:
               // boolean bret = SpeakerBluetoothManager.getInstance().openBluetooth();
                //LightManager.getInstance().lightWakeup();
                //Log.d(",ain", "onClick: " + bret);

                break;
            case R.id.button_close:
                //SpeakerBluetoothManager.getInstance().closeBluetooth();
                LightManager.getInstance().netDisconnect();
                break;
            case R.id.btn_music:
                *//*AIApiService aiApiService = RetrofitApiManager.getAiApiService();
                RequestMusic requestMusic = new RequestMusic();
                requestMusic.setDomain("music");
                RequestMusic.KeywordsEntity keywordsEntity = new RequestMusic.KeywordsEntity();
                keywordsEntity.setSong("公路之歌");
                requestMusic.setKeywords(keywordsEntity);
                aiApiService.getMusicContent("", requestMusic).enqueue(new Callback<ResponseMusic>() {
                    @Override
                    public void onResponse(Call<ResponseMusic> call, Response<ResponseMusic> response) {
                        Log.d(TAG, "onResponse: ");
                        url = response.body().getData().getUrl();
                    }

                    @Override
                    public void onFailure(Call<ResponseMusic> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                    }
                });*//*
                MusicPlayerManager.getInstance().playLocalMusic("听海");

                break;
            case R.id.btn_play:
                Log.d(TAG, "onClick: url:" + url);
                MusicPlayerManager.getInstance().playUrlMusic(url);
                break;
            case R.id.btn_pause:
                //MusicPlayerManager.getInstance().pauseMusic();
                LightManager.getInstance().bootLightShow();
                break;
            case R.id.btn_restart:
                //MusicPlayerManager.getInstance().restartMusic();
                RecordModel.getInstance().startRecord();//sdk mode

                //RecordModel_bak.getInstance().startRecord(); //api mode
                ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
                scheduledThreadPool.schedule(new Runnable() {

                    @Override
                    public void run() {
                        RecordModel.getInstance().stopRecord(); //sdk mode

                    }
                }, 3, TimeUnit.SECONDS);
                break;
        }
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showReconizeStatus(ReconizeStatusEvent event) {
        if (!TextUtils.isEmpty(event.message)) {
            mTvStatusContact.setText(event.message);
        } else {
            mTvStatusContact.setText("");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showReconizeResult(ReconizeResultEvent event) {
        if (!TextUtils.isEmpty(event.message)) {
            mTvResultContent.setText(event.message);
        } else {
            mTvResultContent.setText("");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNluResult(NluEvent event) {
        if (!TextUtils.isEmpty(event.message)) {
            mTvNlu.setText(event.message);
        } else {
            mTvNlu.setText("");
        }
    }
}
