package com.example.cityguideapp.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Baal on 3/26/2018.
 */

public class UserList {
    private String name;
    private ArrayList<GooglePlace> places;

    public UserList(String name, ArrayList<GooglePlace> places) {
        this.name = name;
        this.places = places;
    }

    public String getName() {
        return name;
    }

    public int getNrElements() {
        return 2;
        //return  places.size();
    }

    public ArrayList<GooglePlace> getPlaces() {
        return places;
    }

    public Bitmap getImage(){

        //get image from cache
        return null;
    }
}
