package com.example.cityguideapp.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Baal on 3/26/2018.
 */

public class UserList {
    private int id;
    private String UID;
    private String name;
    private ArrayList<GooglePlace> places;

    public UserList(int id,String UID,String name) {
        this.id=id;
        this.UID = UID;
        this.name = name;
    }

    public UserList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNrElements() {
        if(places==null)
            return 0;

        return places.size();
    }

    public ArrayList<GooglePlace> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<GooglePlace> places) {
        this.places = places;
    }

    public Bitmap getImage(){

        //get image from cache
        return null;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
