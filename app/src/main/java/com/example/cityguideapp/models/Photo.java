package com.example.cityguideapp.models;

/**
 * Created by Baal on 3/21/2018.
 */



public class Photo{
    private String height;
    private String width;
    private String reference;
    private String html_attributions;

    public Photo(String height, String width,String reference, String html_attributions) {
        this.height = height;
        this.width = width;
        this.reference = reference;
        this.html_attributions = html_attributions;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getReference() {
        return reference;
    }

    public String getHtml_attributions() {
        return html_attributions;
    }
}