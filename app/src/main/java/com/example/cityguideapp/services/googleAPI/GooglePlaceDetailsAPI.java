package com.example.cityguideapp.services.googleAPI;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.models.GooglePlaceConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GooglePlaceDetailsAPI extends AsyncTask{

    private static String TAG = "Google Places API ";
    private OnGoogleAPICallEnded callback;
    private String GOOGLE_KEY="";
    private String response;
    private String placeID;

    public GooglePlaceDetailsAPI(OnGoogleAPICallEnded callback, Context context,String placeID) {
        this.callback = callback;
        this.placeID = placeID;

        try{
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            this.GOOGLE_KEY =  bundle.getString("com.google.android.geo.API_KEY");

        } catch (Exception e){
        }
    }


    public static String sendRequest(String urlToRead) {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();

            return result.toString();

        } catch (Exception e) {
            Log.i(TAG, "Place: " + e.toString());
            return null;
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="
                + this.placeID
                +"&key=" + this.GOOGLE_KEY;

        this.response = sendRequest(url);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        GooglePlace place = GooglePlaceConverter.convertDetails(response);

        callback.onGoogleAPICallEnded(place);
    }

    public interface OnGoogleAPICallEnded {
        void onGoogleAPICallEnded(GooglePlace place);
    }
}
