package com.example.user.istpandroidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.istpandroidproject.model.OwnedPokemonInfo;
import com.example.user.istpandroidproject.model.OwnedPokemonInfoDataManager;

import java.util.ArrayList;

public class PokemonListActivity extends CustomizedActivity implements OnPokemonSelectedChangeListener, AdapterView.OnItemClickListener{

    PokemonListAdapter arrayAdapter;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityName = this.getClass().getSimpleName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        Intent srcIntent = getIntent();
        int selectedOptionIndex = srcIntent.getIntExtra(MainActivity.selectedOptionIndexKey, 0);

        OwnedPokemonInfoDataManager dataManager =
                new OwnedPokemonInfoDataManager(this);

        dataManager.loadListViewData();

        ownedPokemonInfos =
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
        listView.setOnItemClickListener(this);
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

    public final static int detailActivityRequestCode = 1;
    public final static String ownedPokemonInfoKey = "parcelable";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OwnedPokemonInfo data = arrayAdapter.getItem(position);

        Intent intent = new Intent();
        intent.setClass(PokemonListActivity.this, DetailActivity.class);
        intent.putExtra(ownedPokemonInfoKey, data);

        startActivityForResult(intent, detailActivityRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == detailActivityRequestCode) {

            if(resultCode == DetailActivity.removeFromList) {
                OwnedPokemonInfo ownedPokemonInfo =
                        arrayAdapter.getItemWithName(data.getStringExtra(OwnedPokemonInfo.nameKey));

                arrayAdapter.remove(ownedPokemonInfo);
                return;
            }
            else if(resultCode == DetailActivity.levelUp) {

            }


        }

    }
}
