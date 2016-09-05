package com.example.user.istpandroidproject.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by user on 2016/9/5.
 */
public class OwnedPokemonInfoDataManager {

    Context mContext;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;

    public OwnedPokemonInfoDataManager(Context context) {
        mContext = context;
    }

    public void loadListViewData() {
        ownedPokemonInfos = new ArrayList<>();

        BufferedReader reader;
        String line = null;
        String[] dataFields = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    mContext.getAssets().open("pokemon_data.csv")));

            while((line = reader.readLine()) != null) {
                dataFields = line.split(",");
                //TODO: construct a object from dataFields
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }



}
