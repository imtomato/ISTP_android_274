package com.example.user.istpandroidproject.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2016/9/29.
 */
public class PokemonMapManager implements RequestCallback {
    GoogleMap googleMap;
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
