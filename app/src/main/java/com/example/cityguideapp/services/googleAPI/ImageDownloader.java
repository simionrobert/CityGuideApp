package com.example.cityguideapp.services.googleAPI;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Baal on 3/26/2018.
 */
public class ImageDownloader extends AsyncTask<Object, Void, Bitmap> {

    Context context;
    private int weight;
    private int height;

    public ImageDownloader(Context context,int weight,int height) {
        this.context = context;
        this.weight=weight;
        this.height = height;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Object... arg0)
    {
        try{
            String reference = (String) arg0[0];
            if(!reference.contains("http")){
                ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = app.metaData;
                String GOOGLE_KEY =  bundle.getString("com.google.android.geo.API_KEY");

                URL url = new URL("https://maps.googleapis.com/maps/api/place/photo?maxwidth="+weight
                        +"&maxheight="+height
                        +"&photoreference="+ reference +"&key=" + GOOGLE_KEY);

                Bitmap bitmapIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bitmapIcon;
            } else {

                //reference is an url
                URL url = new URL(reference);

                Bitmap bitmapIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bitmapIcon;
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
