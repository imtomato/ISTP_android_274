package com.example.user.istpandroidproject.model;

import android.os.AsyncTask;
import android.util.Log;

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
        (new requestTask()).execute("http://140.112.30.42:5001/raw_data");
    }

    public static class requestTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            byte[] bytes = Utils.urlToBytes(params[0]);
            if (bytes == null)
                return null;
            return new String(bytes);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Pokemon Data", s);
        }
    }
}
