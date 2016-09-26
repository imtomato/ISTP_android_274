package com.example.user.istpandroidproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.istpandroidproject.model.OwnedPokemonInfo;
import com.example.user.istpandroidproject.model.OwnedPokemonInfoDataManager;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/9/22.
 */
public class PokemonListFragment extends Fragment implements OnPokemonSelectedChangeListener, AdapterView.OnItemClickListener, DialogInterface.OnClickListener, FindCallback<OwnedPokemonInfo>{

    public static PokemonListFragment newInstance() {

        Bundle args = new Bundle();

        PokemonListFragment fragment = new PokemonListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    PokemonListAdapter arrayAdapter;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;
    AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ownedPokemonInfos = new ArrayList<>();
        prepareListViewData();
    }

    void loadFromCSV() {
        Intent srcIntent = getActivity().getIntent();
        int selectedOptionIndex = srcIntent.getIntExtra(MainActivity.selectedOptionIndexKey, 0);

        OwnedPokemonInfoDataManager dataManager =
                new OwnedPokemonInfoDataManager(getActivity());

        dataManager.loadListViewData();

        ArrayList<OwnedPokemonInfo> temp = dataManager.getOwnedPokemonInfos();
        for(OwnedPokemonInfo ownedPokemonInfo : temp) {
            ownedPokemonInfos.add(ownedPokemonInfo);
        }

        ArrayList<OwnedPokemonInfo> initThreePokemonInfos = dataManager.getInitThreePokemonInfos();
        OwnedPokemonInfo selectedPokemon = initThreePokemonInfos.get(selectedOptionIndex);
        ownedPokemonInfos.add(0, selectedPokemon);
    }

    public final static String recordIsInDBKey = "recordIsInDB";

    void prepareListViewData() {
        SharedPreferences preferences = getActivity().getSharedPreferences(
                PokemonListFragment.class.getSimpleName(),
                Context.MODE_PRIVATE);

        boolean recordIsInDB = preferences.getBoolean(recordIsInDBKey, false);

        if(!recordIsInDB) { //init
            loadFromCSV();
            OwnedPokemonInfo.initTable(ownedPokemonInfos);
            preferences.edit().putBoolean(recordIsInDBKey, true).commit();
        }
        else { //read from DB
            OwnedPokemonInfo.getQuery()
                    .fromPin(OwnedPokemonInfo.localDBTableName) //read from local DB
                    .findInBackground(this);

            OwnedPokemonInfo.getQuery()
                    .findInBackground(this); //default is read from cloud DB
        }

    }

    View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(fragmentView == null) {
            fragmentView = inflater.inflate(
                    R.layout.activity_pokemon_list,
                    null);

            arrayAdapter = new PokemonListAdapter(
                    getActivity(),
                    R.layout.row_view_of_pokemon_list,
                    ownedPokemonInfos
            );

            arrayAdapter.listener = this;

            ListView listView = (ListView)fragmentView.findViewById(R.id.listView);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(this);

            alertDialog = new AlertDialog
                    .Builder(getActivity())
                    .setTitle("警告")
                    .setMessage("你確定要刪除這些神奇寶貝嗎?")
                    .setPositiveButton("確認", this)
                    .setNegativeButton("取消", this)
                    .setCancelable(false)
                    .create();

            setHasOptionsMenu(true);
            setMenuVisibility(true);

        }

        return fragmentView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(!arrayAdapter.selectedPokemonInfos.isEmpty()) {
            inflater.inflate(R.menu.selected_pokemon_list_action_bar, menu);
        }
    }

    void deleteSelectedPokemons() {
        for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemonInfos) {
            arrayAdapter.remove(ownedPokemonInfo);
        }
        arrayAdapter.selectedPokemonInfos.clear();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete) {
            alertDialog.show();
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
        getActivity().invalidateOptionsMenu();
    }

    public final static int detailActivityRequestCode = 1;
    public final static String ownedPokemonInfoKey = "parcelable";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OwnedPokemonInfo data = arrayAdapter.getItem(position);

        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        intent.putExtra(ownedPokemonInfoKey, data);

        startActivityForResult(intent, detailActivityRequestCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(dialog.equals(alertDialog)) {
            if(which == AlertDialog.BUTTON_NEGATIVE) {
                Toast.makeText(getActivity(), "取消刪除", Toast.LENGTH_SHORT).show();
            }
            else if(which == AlertDialog.BUTTON_POSITIVE) {
                deleteSelectedPokemons();
            }
        }
    }

    //read from DB
    @Override
    public void done(List<OwnedPokemonInfo> objects, ParseException e) {
        if(e == null) {
            ownedPokemonInfos.clear();
            ownedPokemonInfos.addAll(objects);

            if(arrayAdapter != null) {
                arrayAdapter.notifyDataSetChanged(); //update listView
            }
        }
    }
}
