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

        ArrayList<String> pokemonNames = new ArrayList<>();
        for(OwnedPokemonInfo ownedPokemonInfo : ownedPokemonInfos) {
            pokemonNames.add(ownedPokemonInfo.name);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                pokemonNames
        );

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

    }
}
