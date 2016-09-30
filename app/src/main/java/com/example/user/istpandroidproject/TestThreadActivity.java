package com.example.user.istpandroidproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.user.istpandroidproject.model.Utils;

import java.util.Calendar;


public class TestThreadActivity extends AppCompatActivity {

    Handler uiHandler;
    TextView timeInfoText;

    Handler bgThreadHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread);

        timeInfoText = (TextView)findViewById(R.id.timeInfoText);

        uiHandler = new Handler(getMainLooper());
        uiHandler.post(updateTimeInfoToUITask); //first execute update_time_task

        HandlerThread bgThread = new HandlerThread("bgThread");
        bgThread.start();
        bgThreadHandler = new Handler(bgThread.getLooper());
        bgThreadHandler.post(checkThreadIdTask);
        Log.d("threadTest", "main thread id:" + Thread.currentThread().getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiEventReceiver, intentFilter);
    }

    BroadcastReceiver wifiEventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String statusMsg = Utils.getConnectivityStatusString(context);
            Log.d("wifiEvent", statusMsg);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiEventReceiver);
    }

    Runnable checkThreadIdTask = new Runnable() {
        @Override
        public void run() {
            Log.d("threadTest", "thread id:" + Thread.currentThread().getId());
        }
    };

    int updateTimePeriodInSecs = 1;

    Runnable updateTimeInfoToUITask = new Runnable() {
        @Override
        public void run() {
            String timeInfo = Calendar.getInstance().getTime().toString();
            timeInfoText.setText(timeInfo);

            uiHandler.postDelayed(this, updateTimePeriodInSecs * 1000); //periodically execute this task
        }
    };

}
