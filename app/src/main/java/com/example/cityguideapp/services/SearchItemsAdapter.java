package com.example.cityguideapp.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.GooglePlace;

import java.util.List;

/**
 * Created by Baal on 3/6/2018.
 */

public class SearchItemsAdapter extends ArrayAdapter<GooglePlace> {

    private int layout;

    public SearchItemsAdapter(Context context, int resource, List<GooglePlace> items) {
        super(context, resource, items);
        layout = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        View v = vi.inflate(layout, null);

        GooglePlace place = getItem(position);

        if (place != null) {
            final ImageView picture = (ImageView) v.findViewById(R.id.imageView);
            TextView title = (TextView) v.findViewById(R.id.titleTextView);
            TextView address = (TextView) v.findViewById(R.id.addressTextView);
            TextView type = (TextView) v.findViewById(R.id.typeTextView);

            TextView rating = (TextView) v.findViewById(R.id.ratingTextView);
            TextView open_now = (TextView) v.findViewById(R.id.open_nowTextView);

            title.setText(place.getName());
            address.setText(place.getVicinity());
            type.setText(place.getTypesList().get(0));
            rating.setText(place.getRating());

            open_now.setText(place.getOpen_now());

            //get image url and cache
            @SuppressLint("StaticFieldLeak") ImageDownloader downloader = new ImageDownloader(this.getContext()) {
                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);

                    picture.setImageBitmap(result);
                }
            };

            downloader.execute(place.getPhotoArrayList().get(0));
        }

        return v;
    }
}



