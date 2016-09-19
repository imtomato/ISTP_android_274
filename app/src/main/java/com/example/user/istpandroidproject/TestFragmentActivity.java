package com.example.user.istpandroidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestFragmentActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        Button f1Btn = (Button)findViewById(R.id.fragmentBtn1);
        Button f2Btn = (Button)findViewById(R.id.fragmentBtn2);

        f1Btn.setOnClickListener(this);
        f2Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
