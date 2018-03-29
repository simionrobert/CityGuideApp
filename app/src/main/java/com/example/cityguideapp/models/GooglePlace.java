package com.example.cityguideapp.models;


import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Baal on 3/6/2018.
 */

public class GooglePlace {
    private int id;

    private String name;
    private String placeID;
    private String rating;
    private  String vicinity;
    private String open_now;
    private Double latitude, longitude;
    private ArrayList<Photo> photoArrayList;
    private ArrayList<String> typesList;

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

}


