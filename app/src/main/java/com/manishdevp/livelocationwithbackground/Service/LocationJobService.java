package com.manishdevp.livelocationwithbackground.Service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.manishdevp.livelocationwithbackground.Activity.MainActivity;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LocationJobService extends JobService implements UpdatesLocation.ILocationProvider {

    private static final String TAG = LocationJobService.class.getSimpleName();
    public static final int LOCATION_MESSAGE = 9999;

    private Messenger mActivityMessenger;

    private UpdatesLocation updatesLocation;

    public LocationJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        updatesLocation.onStop();
        return false;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        updatesLocation = new UpdatesLocation(this);

        updatesLocation.onCreate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        if (intent != null) {
            mActivityMessenger = intent.getParcelableExtra(MainActivity.MESSENEGER_KAY);
        }
        updatesLocation.onStart();
        return START_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy....");
    }

    //send message to MainActivity
    private void sendMessage(int messageID, Location location) {
        if (mActivityMessenger == null) {
            return;
        }
        Message m = Message.obtain();
        m.what = messageID;
        m.obj = location;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error to send MainActivity");
        }
    }

    @Override
    public void onLocationUpdate(Location location) {
        sendMessage(LOCATION_MESSAGE, location);
    }
}