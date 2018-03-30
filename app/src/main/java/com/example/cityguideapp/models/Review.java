package com.example.cityguideapp.models;

/**
 * Created by Baal on 3/29/2018.
 */

public class Review {
    private String author;
    private String authorURL;
    private String profilePhotoURL;
    private String rating;
    private String relativeTime;
    private String text;
    private Photo image;

    public Review(String author, String authorURL, String profilePhotoURL, String rating, String relativeTime, String text) {
        this.author = author;
        this.authorURL = authorURL;
        this.profilePhotoURL = profilePhotoURL;
        this.rating = rating;
        this.relativeTime = relativeTime;
        this.text = text;
        this.image = new Photo(null,null,null,null);
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorURL() {
        return authorURL;
    }

    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    public String getRating() {
        return rating;
    }

    public String getRelativeTime() {
        return relativeTime;
    }

    public String getText() {
        return text;
    }

    public Photo getImage() {
        return image;
    }

    public void setImage(Photo image) {
        this.image = image;
    }
}
