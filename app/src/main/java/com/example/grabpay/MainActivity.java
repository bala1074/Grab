package com.example.grabpay;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button pay;

    private static final int PERMISSION_REQUEST_CODE = 1;
    BroadcastReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pay = findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.SEND_SMS)
                            == PackageManager.PERMISSION_DENIED) {

                        Log.d("permission", "permission denied to SEND_SMS - requesting it");
                        String[] permissions = {Manifest.permission.SEND_SMS};

                        requestPermissions(permissions, PERMISSION_REQUEST_CODE);

                    }
                    else{
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("919220592205", null, "PM6PF", null, null);
                    }
                }

            }
        });



        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(100);
        smsReceiver = new BroadcastReceiver (){

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("onReceive","received");
            }
        };
        registerReceiver(smsReceiver, intentFilter);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }


}
