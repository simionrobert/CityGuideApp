package com.example.cityguideapp.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.services.ReviewAdapter;
import com.example.cityguideapp.services.googleAPI.GooglePlaceDetailsAPI;
import com.example.cityguideapp.services.googleAPI.ImageDownloader;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class DescriptionActivity extends BaseLocationActivity implements GooglePlaceDetailsAPI.OnGoogleAPICallEnded{

    private static final String TAG = "DescriptionActivity";
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle b = getIntent().getExtras();
        if(b!=null){
            String parent = b.getString("Parent");

            if(parent!=null){
                if (parent.compareTo("Main") == 0 || parent.compareTo("Search") == 0) {
                    mRequestingLocationUpdates = true; //start service
                    return;
                }
            }
        }

        // Fetch place Descriptions FROM GoogleAPI and send them to fragment on  callback
        String placeID = b.getString("placeID");
        GooglePlaceDetailsAPI api = new GooglePlaceDetailsAPI(this,this,placeID);
        api.execute();
    }

    @Override
    public void onGoogleAPICallEnded(GooglePlace place) {
        setContentView(R.layout.activity_description);

        renderPlaceDescription(place);
    }

    private void renderPlaceDescription(final GooglePlace googlePlace){
        // TODO: Display place or GooglePlace pretty

        setContentView(R.layout.activity_description);

        if(googlePlace!=null){
            View view =this.findViewById(R.id.description_layout);

            final ImageView picture = view.findViewById(R.id.imageView2);
            ImageDownloader downloader = new ImageDownloader(this,500,500) {
                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);

                    picture.setImageBitmap(result);
                }
            };

            downloader.execute(googlePlace.getPhotoArrayList().get(0).getReference());

            TextView name = view.findViewById(R.id.description_name);
            name.setText(googlePlace.getName());

            RatingBar ratingBar =  view.findViewById(R.id.ratingBar);
            ratingBar.setRating(Float.parseFloat(googlePlace.getRating()));

            TextView ratingNr = view.findViewById(R.id.rating_nr);
            ratingNr.setText(googlePlace.getRating());

            ImageButton button = findViewById(R.id.description_direction);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(googlePlace.getMapsURL()));
                    startActivity(intent);
                }
            });

            TextView address = view.findViewById(R.id.description_address);
            String addressText = googlePlace.getFormatted_address();
            addressText = addressText.replaceFirst(", ","\n");
            address.setText(addressText);

            TextView website = view.findViewById(R.id.description_website);
            website.setText(googlePlace.getWebsite());

            TextView phone = view.findViewById(R.id.description_phone);
            phone.setText(googlePlace.getInternational_phone_number());


            ImageButton button2 = findViewById(R.id.description_hours);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DescriptionActivity.this);
                    builder.setTitle(R.string.description_hours);

                    StringBuilder stringbuilder = new StringBuilder();
                    for(String s : googlePlace.getOpeningTime()) {
                        stringbuilder.append(s);
                        stringbuilder.append("\n");
                    }
                    builder.setMessage(stringbuilder.toString());

                    builder.show();
                }
            });


             RecyclerView mRecyclerView=  findViewById(R.id.reviewListView);
             mRecyclerView.setHasFixedSize(true);

             RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
             mRecyclerView.setLayoutManager(mLayoutManager);

             RecyclerView.Adapter mAdapter = new ReviewAdapter(this,googlePlace.getReviews());
             mRecyclerView.setAdapter(mAdapter);
        }
    }



    @Override
    protected void onLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                stopLocationUpdates();
                mRequestingLocationUpdates = false; //stop service

                mCurrentLocation = locationResult.getLastLocation();
                Log.i(TAG, "//////////////////////////////////////////////////////////////////////////////////////");
                startGoogleAutoCompleteActivity();
            }
        };
    }


    private void startGoogleAutoCompleteActivity() {

        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setCountry("RO")
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                    .build();

            Location location = new Location("DescriptionActivity");
            location.setLatitude(mCurrentLocation.getLatitude());
            location.setLongitude(mCurrentLocation.getLongitude());
            LatLngBounds latLongBounds= setBounds(location,5000);

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                    .setBoundsBias(latLongBounds).build(this);

            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());

                // Fetch place Descriptions FROM GoogleAPI and send them to fragment on  callback
                GooglePlaceDetailsAPI api = new GooglePlaceDetailsAPI(this,this,place.getId());
                api.execute();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i(TAG, status.getStatusMessage());

                finish();
            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.
                finish();
            }
        }
    }

    private LatLngBounds setBounds(Location location, int mDistanceInMeters ){
        double latRadian = Math.toRadians(location.getLatitude());

        double degLatKm = 110.574235;
        double degLongKm = 110.572833 * Math.cos(latRadian);
        double deltaLat = mDistanceInMeters / 1000.0 / degLatKm;
        double deltaLong = mDistanceInMeters / 1000.0 / degLongKm;

        double minLat = location.getLatitude() - deltaLat;
        double minLong = location.getLongitude() - deltaLong;
        double maxLat = location.getLatitude() + deltaLat;
        double maxLong = location.getLongitude() + deltaLong;

        Log.d(TAG,"Min: "+Double.toString(minLat)+","+Double.toString(minLong));
        Log.d(TAG,"Max: "+Double.toString(maxLat)+","+Double.toString(maxLong));

        LatLngBounds bounds = new LatLngBounds(new LatLng(minLat, minLong),new LatLng(maxLat, maxLong));
        return bounds;
    }
}
