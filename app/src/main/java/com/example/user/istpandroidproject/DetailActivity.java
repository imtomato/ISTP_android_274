package com.example.user.istpandroidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.istpandroidproject.model.OwnedPokemonInfo;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent srcIntent = getIntent();
        OwnedPokemonInfo data = srcIntent.getParcelableExtra("parcel");
    }
}
