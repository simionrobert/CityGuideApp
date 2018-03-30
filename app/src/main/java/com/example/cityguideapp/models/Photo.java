package com.example.cityguideapp.models;

import android.graphics.Bitmap;

/**
 * Created by Baal on 3/30/2018.
 */

public class Photo {

    private String height;
    private String width;
    private String reference;
    private String html_attributions;
    private Bitmap image;

    public Photo(String height, String width, String reference, String html_attributions) {
        this.height = height;
        this.width = width;
        this.reference = reference;
        this.html_attributions = html_attributions;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String html_attributions) {
        this.html_attributions = html_attributions;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
