package com.example.user.istpandroidproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener{

    static final String[] pokemonNames = {
            "小火龍",
            "傑尼龜",
            "妙蛙種子"
    };

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
        nameText.setOnEditorActionListener(this);
        nameText.setImeOptions(EditorInfo.IME_ACTION_DONE);

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

            String welcomeMessage = String.format(
                    "你好, 訓練家%s 歡迎來到神奇寶貝的世界, 你的第一個夥伴是%s",
                    nameOfTheTrainer,
                    pokemonNames[selectedOptionIndex]);

            infoText.setText(welcomeMessage);

        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            //dismiss virtual keyboard
            InputMethodManager inm =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            //simulate button clicked
            confirmBtn.performClick();

            return true;
        }

        return false;
    }
}
