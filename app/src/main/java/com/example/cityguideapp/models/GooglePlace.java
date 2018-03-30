package com.example.cityguideapp.models;


import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Baal on 3/6/2018.
 */

public class GooglePlace {

    // For database values
    private int id;
    private String name;
    private String placeID;

    // For GooglePlaceSearchAPI
    private String rating;
    private String vicinity;
    private String open_now;
    private Double latitude, longitude;
    private ArrayList<Photo> photoArrayList;
    private ArrayList<String> typesList;


    // For GooglePlaceDetailsAPI
    private String formatted_address = null;
    private String international_phone_number = null;
    private ArrayList<String> openingTime = null;
    private String website = null;
    private String mapsURL = null;
    private ArrayList<Review> reviews = null;

    public GooglePlace(int id, String name, String placeID) {
        this.id = id;
        this.name = name;
        this.placeID = placeID;
    }

    public GooglePlace(String name, String placeID, String rating,
                       String vicinity, String open_now,  ArrayList<String> typesList, Double latitude, Double longitude, ArrayList<Photo> photoArrayList) {
        this.name = name;
        this.placeID = placeID;
        this.rating = rating;
        this.vicinity = vicinity;
        this.open_now = open_now;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoArrayList = photoArrayList;
        this.typesList = typesList;
    }

    public GooglePlace(String name, String placeID, String rating, Double latitude, Double longitude,
                       ArrayList<Photo> photoArrayList, ArrayList<String> typesList, String formatted_address,
                       String international_phone_number, ArrayList<String> openingTime, String website, String mapsURL, ArrayList<Review> reviews) {
        this.name = name;
        this.placeID = placeID;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoArrayList = photoArrayList;
        this.typesList = typesList;
        this.formatted_address = formatted_address;
        this.international_phone_number = international_phone_number;
        this.openingTime = openingTime;
        this.website = website;
        this.mapsURL = mapsURL;
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPlaceid() {
        return placeID;
    }

    public String getRating() {
        return rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public String getOpen_now() {

        return open_now;
    }

    public ArrayList<Photo> getPhotoArrayList() {
        return photoArrayList;
    }

    public ArrayList<String> getTypesList() {
        return typesList;
    }

    public String getPlaceID() {
        return placeID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public ArrayList<String> getOpeningTime() {
        return openingTime;
    }

    public String getWebsite() {
        return website;
    }

    public String getMapsURL() {
        return mapsURL;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}


