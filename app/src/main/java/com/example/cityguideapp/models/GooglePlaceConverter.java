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

    public ArrayList<GooglePlace> convert(String JSON) {
        ArrayList<GooglePlace> listPlaces = new ArrayList<>();

        if (JSON != null) {
            try {
                JSONObject jsonObject = new JSONObject(JSON);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                String name = null;
                String rating= null;
                String iconURL= null;
                String id= null;
                String placeid= null;
                Double latitude = 0.0;
                Double longitude = 0.0;
                String vicinity= null;
                String open_now= null;
                ArrayList<String> typesList =null;
                ArrayList<Photo> photoArrayList = null;


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);

                    if(jo.has("icon")){
                        iconURL = jo.getString("icon");
                    }
                    if(jo.has("rating"))
                        rating = jo.getString("rating");
                    if(jo.has("id"))
                        id = jo.getString("id");
                    if(jo.has("place_id"))
                        placeid = jo.getString("place_id");
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

                    GooglePlace place = new GooglePlace( name,  id,  placeid,  iconURL,  rating,
                             vicinity, open_now, typesList, latitude,  longitude, photoArrayList);
                    listPlaces.add(place);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listPlaces;
        }

        return null;
    }
}
