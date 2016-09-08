package com.example.user.istpandroidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.istpandroidproject.model.OwnedPokemonInfo;
import com.example.user.istpandroidproject.model.OwnedPokemonInfoDataManager;

import java.util.ArrayList;

public class PokemonListActivity extends AppCompatActivity implements OnPokemonSelectedChangeListener{

    PokemonListAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        Intent srcIntent = getIntent();
        int selectedOptionIndex = srcIntent.getIntExtra(MainActivity.selectedOptionIndexKey, 0);

        OwnedPokemonInfoDataManager dataManager =
                new OwnedPokemonInfoDataManager(this);

        dataManager.loadListViewData();

        ArrayList<OwnedPokemonInfo> ownedPokemonInfos =
                dataManager.getOwnedPokemonInfos();

        ArrayList<OwnedPokemonInfo> initThreePokemonInfos = dataManager.getInitThreePokemonInfos();
        OwnedPokemonInfo selectedPokemon = initThreePokemonInfos.get(selectedOptionIndex);
        ownedPokemonInfos.add(0, selectedPokemon);

        arrayAdapter = new PokemonListAdapter(
                this,
                R.layout.row_view_of_pokemon_list,
                ownedPokemonInfos
        );
        arrayAdapter.listener = this;

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(arrayAdapter.selectedPokemonInfos.isEmpty()) {
            return false;
        }
        else {
            getMenuInflater()
                    .inflate(R.menu.selected_pokemon_list_action_bar, menu);
            return true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete) {
            for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemonInfos) {
                arrayAdapter.remove(ownedPokemonInfo);
            }
            arrayAdapter.selectedPokemonInfos.clear();
            invalidateOptionsMenu();
            return true;
        }
        else if(itemId == R.id.action_settings) {

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSelectedChanged(OwnedPokemonInfo data) {
        invalidateOptionsMenu();
    }
}
