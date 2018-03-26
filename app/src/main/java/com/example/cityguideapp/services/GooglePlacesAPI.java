package com.example.cityguideapp.services;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.models.GooglePlaceConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GooglePlacesAPI extends AsyncTask{

    private static String TAG = "Google Places API ";
    private GooglePlaceConverter converter;

    private String GOOGLE_KEY="";
    private String response;
    private  Context context;
    private int resource;
    private ListView listView;
    private Location location;
    private String type;

    public GooglePlacesAPI(Context context, int resource, ListView listView,  Location location, String type) {
        this.context = context;
        this.resource = resource;
        this.listView = listView;
        this.location = location;
        this.type = type;

        try{
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            this.GOOGLE_KEY =  bundle.getString("com.google.android.geo.API_KEY");

        } catch (Exception e){

        }

        converter = new GooglePlaceConverter();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + this.location.getLatitude()+","+this.location.getLongitude()
                +"&radius=3000&type="+this.type
                +"&key=" + this.GOOGLE_KEY;
        this.response = sendRequest(url);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(response == null){
            // Sth went wrong
            Toast toast = Toast.makeText(context, R.string.internet_down, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            ArrayList<GooglePlace> listItems = converter.convert(response);

            // Show items in Search Activity
            SearchItemsAdapter loader = new SearchItemsAdapter(this.context, this.resource, listItems);
            this.listView.setAdapter(loader);

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
}
