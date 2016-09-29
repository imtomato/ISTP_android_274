package com.example.user.istpandroidproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonMapFragment extends SupportMapFragment {


    public PokemonMapFragment() {
        // Required empty public constructor
    }

    public static PokemonMapFragment newInstance() {

        Bundle args = new Bundle();

        PokemonMapFragment fragment = new PokemonMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
