package com.example.cityguideapp.services.googleAPI;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.cityguideapp.models.Photo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Baal on 3/26/2018.
 */
public class ImageDownloader extends AsyncTask<Photo, Void, Bitmap> {

    Context context;
    public ImageDownloader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Photo... arg0)
    {
        try{
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            String GOOGLE_KEY =  bundle.getString("com.google.android.geo.API_KEY");

            Photo p = arg0[0];
            URL url = new URL("https://maps.googleapis.com/maps/api/place/photo?maxwidth="+200
                    +"&maxheight="+200
                    +"&photoreference="+ p.getReference()+"&key=" + GOOGLE_KEY);


            Bitmap bitmapIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bitmapIcon;
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
