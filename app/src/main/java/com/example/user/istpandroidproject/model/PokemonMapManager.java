package com.example.user.istpandroidproject.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by user on 2016/9/29.
 */
public class PokemonMapManager implements RequestCallback {
    GoogleMap googleMap;
    HashMap<String, PokemonMarkerInfo> markerInfos = new HashMap<>();


    public PokemonMapManager(GoogleMap googleMap)
    {
        this.googleMap = googleMap;
    }

    public void requestPokemonServer()
    {
        (new requestTask(this)).execute("http://140.112.30.42:5001/raw_data");
    }

    @Override
    public void callback(JSONArray gyms, JSONArray pokemons, JSONArray stops) {
        addMarkerInfoFromJsonArray(gyms, PokemonMarkerInfo.PokemonMarkerType.GYM);
        addMarkerInfoFromJsonArray(pokemons, PokemonMarkerInfo.PokemonMarkerType.POKEMON);
        addMarkerInfoFromJsonArray(stops, PokemonMarkerInfo.PokemonMarkerType.STOP);
    }

    public void addMarkerInfoFromJsonArray(JSONArray jsonArray, PokemonMarkerInfo.PokemonMarkerType type)
    {
        for (int i = 0; i < jsonArray.length() ; i++)
        {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PokemonMarkerInfo markerInfo = PokemonMarkerInfo.newInstanceWithJSONObject(jsonObject, type);
                if(markerInfos.get(markerInfo.id) == null) {
                    markerInfos.put(markerInfo.id, markerInfo);
                    markerInfo.addMarkerToGoogleMap(googleMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class requestTask extends AsyncTask<String, Void, String>{

        WeakReference<RequestCallback> requestCallbackWeakReference;
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
            RequestCallback requestCallback = requestCallbackWeakReference.get();
            if(requestCallback != null && s != null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray gymsJsonArray = jsonObject.getJSONArray("gyms");
                    JSONArray pokemonsJsonArray = jsonObject.getJSONArray("pokemons");
                    JSONArray stopsJsonArray = jsonObject.getJSONArray("pokestops");
                    requestCallback.callback(gymsJsonArray, pokemonsJsonArray, stopsJsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        public requestTask(RequestCallback requestCallback)
        {
            requestCallbackWeakReference = new WeakReference<RequestCallback>(requestCallback);
        }
    }
}

interface RequestCallback
{
    void callback(JSONArray gyms, JSONArray pokemons, JSONArray stops);
}
