package com.example.user.istpandroidproject;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;


public class TestThreadActivity extends AppCompatActivity {

    Handler uiHandler;
    TextView timeInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread);

        timeInfoText = (TextView)findViewById(R.id.timeInfoText);

        uiHandler = new Handler(getMainLooper());
        uiHandler.post(updateTimeInfoToUITask); //first execute update_time_task

    }

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
