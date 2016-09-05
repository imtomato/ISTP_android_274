package com.example.user.istpandroidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.istpandroidproject.model.OwnedPokemonInfo;
import com.example.user.istpandroidproject.model.OwnedPokemonInfoDataManager;

import java.util.ArrayList;

public class PokemonListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        OwnedPokemonInfoDataManager dataManager =
                new OwnedPokemonInfoDataManager(this);

        dataManager.loadListViewData();

        ArrayList<OwnedPokemonInfo> ownedPokemonInfos =
                dataManager.getOwnedPokemonInfos();


        PokemonListAdapter arrayAdapter = new PokemonListAdapter(
                this,
                R.layout.row_view_of_pokemon_list,
                ownedPokemonInfos
        );

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

    }
}
