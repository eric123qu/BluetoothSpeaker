package com.haier.ai.bluetoothspeaker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.haier.ai.bluetoothspeaker.R;
import com.haier.ai.bluetoothspeaker.bean.music.RequestMusic;
import com.haier.ai.bluetoothspeaker.bean.music.ResponseMusic;
import com.haier.ai.bluetoothspeaker.manager.LightManager;
import com.haier.ai.bluetoothspeaker.manager.MusicPlayerManager;
import com.haier.ai.bluetoothspeaker.manager.RetrofitApiManager;
import com.haier.ai.bluetoothspeaker.net.AIApiService;
import com.haier.ai.bluetoothspeaker.service.ReconizeService;
import com.haier.ai.bluetoothspeaker.service.WakeupService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "MainActivity";
    private Button open;
    private Button close;
    private String ssid = "haierubic_rgzn";
    private String passwd = "haierubic123";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

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
        Intent wakeupService = new Intent(this, WakeupService.class);
        startService(wakeupService);

        Intent intent1 = new Intent(this, ReconizeService.class);
        this.startService(intent1);
    }

    private void initView(){
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
    }

    @Override
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
                AIApiService aiApiService = RetrofitApiManager.getAiApiService();
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
                });
                break;
            case R.id.btn_play:
                Log.d(TAG, "onClick: url:" + url);
                MusicPlayerManager.getInstance().playUrlMusic(url);
                break;
            case R.id.btn_pause:
                MusicPlayerManager.getInstance().pauseMusic();
                break;
            case R.id.btn_restart:
                MusicPlayerManager.getInstance().restartMusic();
                break;
        }
    }
}
