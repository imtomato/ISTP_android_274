package com.example.user.istpandroidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView infoText;
    EditText nameText;
    RadioGroup optionsGrp;
    Button confirmBtn;

    String nameOfTheTrainer;
    int selectedOptionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoText = (TextView)findViewById(R.id.infoText);
        nameText = (EditText)findViewById(R.id.nameText);
        optionsGrp = (RadioGroup)findViewById(R.id.optionsGroup);
        confirmBtn = (Button)findViewById(R.id.confirmButton);
        confirmBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.confirmButton) {

            nameOfTheTrainer = nameText.getText().toString();

            int selectedRadioButtonViewId = optionsGrp.getCheckedRadioButtonId();
            View selectedRadioButton = optionsGrp.findViewById(selectedRadioButtonViewId);
            selectedOptionIndex = optionsGrp.indexOfChild(selectedRadioButton);
            
        }

    }



}
