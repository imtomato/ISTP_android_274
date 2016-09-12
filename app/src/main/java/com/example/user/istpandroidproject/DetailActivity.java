package com.example.user.istpandroidproject;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.istpandroidproject.model.OwnedPokemonInfo;

public class DetailActivity extends AppCompatActivity {

    Resources mRes;
    String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRes = getResources();
        packageName = getPackageName();

        Intent srcIntent = getIntent();
        OwnedPokemonInfo data = srcIntent.getParcelableExtra("parcel");
    }

    void setView(OwnedPokemonInfo data) {

        ImageView detailImg =
                (ImageView)findViewById(R.id.detail_appearance_img);

        TextView nameText = (TextView)findViewById(R.id.name_text);
        TextView levelText = (TextView)findViewById(R.id.level_text);
        TextView currentHP = (TextView)findViewById(R.id.currentHP_text);
        TextView maxHP = (TextView)findViewById(R.id.maxHP_text);
        TextView type1Text = (TextView)findViewById(R.id.type_1_text);
        TextView type2Text = (TextView)findViewById(R.id.type_2_text);

        TextView[] skillsText = new TextView[OwnedPokemonInfo.maxNumSkills];
        for(int i = 0; i < OwnedPokemonInfo.maxNumSkills;i++) {
            int skillTextId = mRes.getIdentifier(String.format("skill_%d_text", i+1),
                    "id",
                    packageName);
            skillsText[i] = (TextView)findViewById(skillTextId);
        }

        ProgressBar hpBar = (ProgressBar)findViewById(R.id.HP_progressBar);

        


    }


}
