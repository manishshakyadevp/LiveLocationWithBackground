package com.manishdevp.livelocationwithbackground.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.manishdevp.livelocationwithbackground.R;
import com.manishdevp.livelocationwithbackground.Service.LocationJobService;
import com.manishdevp.livelocationwithbackground.Utils.MyUtils;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RUNTIME_PERMISSIONS_REQUEST_CODE = 44;
    public static final String MESSENEGER_KAY = "message_key";
    // handler for messages by service.
    private MessageHandler mHandler;

    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTextView = findViewById(R.id.locationTextView);
        mHandler = new MessageHandler();
        requestPermissions();
    }

    //    Permission Callback
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RUNTIME_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                finish();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // service start
                Intent sIntent = new Intent(MainActivity.this, LocationJobService.class);
                Messenger messengerIncoming = new Messenger(mHandler);
                sIntent.putExtra(MESSENEGER_KAY, messengerIncoming);
                startService(sIntent);
            } else {
                // denied.
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler = null;
    }

    class MessageHandler extends Handler {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LocationJobService.LOCATION_MESSAGE:
                    Location location = (Location) msg.obj;
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    String address = MyUtils.getFullAddress(location.getLatitude(), location.getLongitude(), MainActivity.this).get(0);
                    locationTextView.setText("Latitude :  " + location.getLatitude() + "\nLongitude : " + location.getLongitude() + "\nAddress : " + address + "\nTime : " + currentDateTimeString);
                    break;
            }
        }
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    RUNTIME_PERMISSIONS_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    RUNTIME_PERMISSIONS_REQUEST_CODE);
        }
    }
}
