package com.example.cityguideapp.views;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.cityguideapp.R;
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

public class DescriptionActivity extends BaseLocationActivity {

    private static final String TAG = "DescriptionActivity";

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            String parent = b.getString("Parent");

            if (parent.compareTo("Main") == 0 || parent.compareTo("Search") == 0) {
                mRequestingLocationUpdates = true; //start service
            }
        } else {
            setContentView(R.layout.activity_description);
        }
    }


    @Override
    protected void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

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

                mRequestingLocationUpdates = false; //stop service
                // TODO: Display place pretty

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i(TAG, status.getStatusMessage());

                finish();
                //Intent intent = new Intent(getBaseContext(), MainActivity.class);
                //startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.

                finish();
                //Intent intent = new Intent(getBaseContext(), MainActivity.class);
                //startActivity(intent);
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
