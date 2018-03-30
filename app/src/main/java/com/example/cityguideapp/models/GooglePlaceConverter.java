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
        return parseResponse(JSON);
    }

    private static ArrayList<GooglePlace> parseResponse(String JSON) {
        ArrayList<GooglePlace> listPlaces = new ArrayList<>();

        if (JSON != null) {
            try {
                JSONObject jsonObject = new JSONObject(JSON);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                String name = null;
                String rating = null;
                String placeID = null;
                Double latitude = 0.0;
                Double longitude = 0.0;
                String vicinity = null;
                String open_now = null;
                ArrayList<String> typesList = null;
                ArrayList<Photo> photoArrayList = null;


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);

                    if (jo.has("rating"))
                        rating = jo.getString("rating");
                    if (jo.has("place_id"))
                        placeID = jo.getString("place_id");
                    if (jo.has("name"))
                        name = jo.getString("name");
                    if (jo.has("vicinity"))
                        vicinity = jo.getString("vicinity");

                    if (jo.has("opening_hours")) {
                        open_now = jo.getJSONObject("opening_hours").getBoolean("open_now") ? "Opened now" : "Closed now";
                    }

                    if (jo.has("geometry")) {
                        JSONObject geometryObject = jo.getJSONObject("geometry");
                        latitude = geometryObject.getJSONObject("location").getDouble("lat");
                        longitude = geometryObject.getJSONObject("location").getDouble("lng");
                    }

                    if (jo.has("types")) {
                        JSONArray typesArray = jo.getJSONArray("types");
                        typesList = new ArrayList<>(typesArray.length());
                        for (int j = 0; j < typesArray.length(); j++) {
                            String type = typesArray.getString(j);
                            typesList.add(type);
                        }
                    }

                    if (jo.has("photos")) {
                        JSONArray photosArray = jo.getJSONArray("photos");
                        photoArrayList = new ArrayList<>(photosArray.length());

                        for (int j = 0; j < photosArray.length(); j++) {
                            Photo photo = null;

                            JSONObject photoObject = photosArray.getJSONObject(j);
                            String height = photoObject.getString("height");
                            String width = photoObject.getString("width");
                            String reference = photoObject.getString("photo_reference");
                            String html_attributions = photoObject.getString("html_attributions");
                            photo = new Photo(height, width, reference, html_attributions);

                            photoArrayList.add(photo);
                        }
                    }

                    GooglePlace place = new GooglePlace(name, placeID, rating,
                            vicinity, open_now, typesList, latitude, longitude, photoArrayList);
                    listPlaces.add(place);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listPlaces;
    }

    public static GooglePlace convertDetails(String JSON) {
        if (JSON != null) {
            try {
                JSONObject jsonObject = new JSONObject(JSON);
                JSONObject jo = jsonObject.getJSONObject("result");

                String name = null;
                String formatted_address = null;
                String international_phone_number = null;
                Double latitude = 0.0;
                Double longitude = 0.0;
                ArrayList<String> openingTime = null;
                ArrayList<Photo> photoArrayList = null;
                String placeID = null;
                String rating = null;
                ArrayList<String> typesList = null;
                String website = null;
                String mapsURL = null;
                ArrayList<Review> reviews = null;

                if (jo.has("name"))
                    name = jo.getString("name");
                if (jo.has("formatted_address"))
                    formatted_address = jo.getString("formatted_address");
                if (jo.has("international_phone_number"))
                    international_phone_number = jo.getString("international_phone_number");
                if (jo.has("geometry")) {
                    JSONObject geometryObject = jo.getJSONObject("geometry");
                    latitude = geometryObject.getJSONObject("location").getDouble("lat");
                    longitude = geometryObject.getJSONObject("location").getDouble("lng");
                }
                if (jo.has("opening_hours")) {
                    openingTime = new ArrayList<>();

                    JSONArray openArray =jo.getJSONObject("opening_hours").getJSONArray("weekday_text");
                    for (int j = 0; j < openArray.length(); j++) {
                        openingTime.add(openArray.getString(j));
                    }
                }
                if (jo.has("rating"))
                    rating = jo.getString("rating");
                if (jo.has("place_id"))
                    placeID = jo.getString("place_id");
                if (jo.has("website"))
                    website = jo.getString("website");
                if (jo.has("url"))
                    mapsURL = jo.getString("url");

                if (jo.has("reviews")) {
                    reviews = new ArrayList<>();
                    JSONArray reviewsArray = jo.getJSONArray("reviews");

                    for (int j = 0; j < reviewsArray.length(); j++) {
                        JSONObject obj  = reviewsArray.getJSONObject(j);
                        String author = obj.getString("author_name");
                        String authorURL = obj.getString("author_url");
                        String profileURL = obj.getString("profile_photo_url");
                        String ratingNote = obj.getString("rating");
                        String time = obj.getString("relative_time_description");
                        String text = obj.getString("text");

                        Review review = new Review(author,authorURL,profileURL,ratingNote,time,text);
                        reviews.add(review);
                    }
                }


                if (jo.has("types")) {
                    JSONArray typesArray = jo.getJSONArray("types");
                    typesList = new ArrayList<>(typesArray.length());
                    for (int j = 0; j < typesArray.length(); j++) {
                        typesList.add(typesArray.getString(j));
                    }
                }

                if (jo.has("photos")) {
                    JSONArray photosArray = jo.getJSONArray("photos");
                    photoArrayList = new ArrayList<>(photosArray.length());

                    for (int j = 0; j < photosArray.length(); j++) {
                        JSONObject photoObject = photosArray.getJSONObject(j);
                        String height = photoObject.getString("height");
                        String width = photoObject.getString("width");
                        String reference = photoObject.getString("photo_reference");
                        String html_attributions = photoObject.getString("html_attributions");

                        Photo photo = new Photo(height, width, reference, html_attributions);
                        photoArrayList.add(photo);
                    }
                }

                GooglePlace place = new GooglePlace(name, placeID, rating,
                        latitude, longitude, photoArrayList, typesList, formatted_address,
                        international_phone_number,openingTime,website,mapsURL,reviews);

                return place;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
