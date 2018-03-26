package com.example.cityguideapp.models;


import java.util.ArrayList;

/**
 * Created by Baal on 3/6/2018.
 */

public class GooglePlace {
    String name;
    String iconURL;
    String id;
    String placeid;
    String rating;
    String vicinity;
    String open_now;
    Double latitude, longitude;
    ArrayList<Photo> photoArrayList;
    ArrayList<String> typesList;

    public GooglePlace(String name, String id, String placeid, String iconURL, String rating,
                       String vicinity, String open_now,  ArrayList<String> typesList, Double latitude, Double longitude, ArrayList<Photo> photoArrayList) {
        this.name = name;
        this.id = id;
        this.placeid = placeid;
        this.iconURL=iconURL;
        this.rating = rating;
        this.vicinity = vicinity;
        this.open_now = open_now;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoArrayList = photoArrayList;
        this.typesList = typesList;
    }

    public String getName() {
        return name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public String getId() {
        return id;
    }

    public String getPlaceid() {
        return placeid;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public ArrayList<Photo> getPhotoArrayList() {
        return photoArrayList;
    }

    public ArrayList<String> getTypesList() {
        return typesList;
    }
}


