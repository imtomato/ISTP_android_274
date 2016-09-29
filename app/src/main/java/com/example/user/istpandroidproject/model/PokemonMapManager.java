package com.example.user.istpandroidproject.model;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by user on 2016/9/29.
 */
public class PokemonMapManager {
    GoogleMap googleMap;
    public PokemonMapManager(GoogleMap googleMap)
    {
        this.googleMap = googleMap;
    }

    public void requestPokemonServer()
    {

    }

    public static class requestTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
