package com.haier.ai.bluetoothspeaker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.haier.ai.bluetoothspeaker.R;
import com.haier.ai.bluetoothspeaker.manager.SpeakerBluetoothManager;
import com.haier.ai.bluetoothspeaker.service.ReconizeService;
import com.haier.ai.bluetoothspeaker.service.WakeupService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "MainActivity";
    private Button open;
    private Button close;
    private String ssid = "haierubic_rgzn";
    private String passwd = "haierubic123";

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

        open.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_open:
                boolean bret = SpeakerBluetoothManager.getInstance().openBluetooth();
                Log.d(",ain", "onClick: " + bret);
                break;
            case R.id.button_close:
                SpeakerBluetoothManager.getInstance().closeBluetooth();
                break;
        }
    }
}
