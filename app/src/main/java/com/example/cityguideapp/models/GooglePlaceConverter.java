package com.example.cityguideapp.models;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GooglePlaceConverter {

    public static ArrayList<GooglePlace> convert(String JSON) {
    /*    ArrayList<GooglePlace> listPlaces = new ArrayList<>();

        GooglePlace placex = new GooglePlace( "Nume1",  "1",  "32",  "ur",  "2",
                "2", "true", null, 23.42,  32.21, null);
        GooglePlace place2 = new GooglePlace( "Nume2",  "2",  "32",  "ur",  "2",
                "2", "true", null, 23.42,  32.21, null);
        GooglePlace place3 = new GooglePlace( "Nume3",  "3",  "32",  "ur",  "2",
                "2", "true", null, 23.42,  32.21, null);
        GooglePlace place4 = new GooglePlace( "Nume3",  "3",  "32",  "ur",  "2",
                "2", "true", null, 23.42,  32.21, null);
        GooglePlace place5 = new GooglePlace( "Nume3",  "3",  "32",  "ur",  "2",
                "2", "true", null, 23.42,  32.21, null);
        GooglePlace place6 = new GooglePlace( "Nume3",  "3",  "32",  "ur",  "2",
                "2", "true", null, 23.42,  32.21, null);
        GooglePlace place7 = new GooglePlace( "Nume3",  "3",  "32",  "ur",  "2",
                "2", "true", null, 23.42,  32.21, null);

        listPlaces.add(placex);
        listPlaces.add(place2);
        listPlaces.add(place3);
        listPlaces.add(place4);
        listPlaces.add(place5);
        listPlaces.add(place6);
        listPlaces.add(place7);

        return listPlaces;*/



        return parseResponse(JSON);
    }

    private static ArrayList<GooglePlace> parseResponse(String JSON){
        ArrayList<GooglePlace> listPlaces = new ArrayList<>();

        if (JSON != null) {
            try {
                JSONObject jsonObject = new JSONObject(JSON);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                String name = null;
                String rating= null;
                String placeID= null;
                Double latitude = 0.0;
                Double longitude = 0.0;
                String vicinity= null;
                String open_now= null;
                ArrayList<String> typesList =null;
                ArrayList<Photo> photoArrayList = null;


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);

                    if(jo.has("rating"))
                        rating = jo.getString("rating");
                    if(jo.has("place_id"))
                        placeID = jo.getString("place_id");
                    if(jo.has("name"))
                        name = jo.getString("name");
                    if(jo.has("vicinity"))
                        vicinity = jo.getString("vicinity");

                    if(jo.has("opening_hours")){
                        open_now = jo.getJSONObject("opening_hours").getBoolean("open_now")?"Opened now":"Closed now";
                    }

                    if(jo.has("geometry"))
                    {
                        JSONObject geometryObject = jo.getJSONObject("geometry");
                        latitude = geometryObject.getJSONObject("location").getDouble("lat");
                        longitude = geometryObject.getJSONObject("location").getDouble("lng");
                    }

                    if(jo.has("types"))
                    {
                        JSONArray typesArray = jo.getJSONArray("types");
                        typesList = new ArrayList<>(typesArray.length());
                        for(int j=0;j<typesArray.length();j++){
                            String type = typesArray.getString(j);
                            typesList.add(type);
                        }
                    }

                    if(jo.has("photos"))
                    {
                        JSONArray photosArray = jo.getJSONArray("photos");
                        photoArrayList = new ArrayList<>(photosArray.length());

                        for(int j=0;j<photosArray.length();j++){
                            Photo photo = null;

                            JSONObject photoObject = photosArray.getJSONObject(j);
                            String height = photoObject.getString("height");
                            String width = photoObject.getString("width");
                            String reference = photoObject.getString("photo_reference");
                            String html_attributions = photoObject.getString("html_attributions");
                            photo = new Photo(height,width,reference,html_attributions);

                            photoArrayList.add(photo);
                        }
                    }

                    GooglePlace place = new GooglePlace( name,  placeID,  rating,
                            vicinity, open_now, typesList, latitude,  longitude, photoArrayList);
                    listPlaces.add(place);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listPlaces;
    }
}
