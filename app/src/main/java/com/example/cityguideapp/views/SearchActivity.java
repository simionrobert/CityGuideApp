package com.example.cityguideapp.views;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.services.googleAPI.GooglePlaceSearchAPI;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;

public class SearchActivity extends BaseLocationActivity
        implements GooglePlaceFragment.OnListFragmentInteractionListener,
        GooglePlaceSearchAPI.OnGoogleAPICallEnded {

    private static final String TAG = "SearchActivity";

    private String placeType = "";
    private GooglePlaceFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String url = getIntent().getExtras().getString("URL");
        switch (url) {
            case "museums":
                this.placeType = "museum";
                break;
            case "malls":
                this.placeType = "shopping_mall";
                break;
            case "parks":
                this.placeType = "park";
                break;
            case "places":
                this.placeType = "";
                break;
        }

        //start location service
        mRequestingLocationUpdates = true;

        fragment = GooglePlaceFragment.newInstance(1,null);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder,fragment);
        ft.commit();
    }


    @Override
    protected void onLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();

                stopLocationUpdates();
                mRequestingLocationUpdates = false;
                Log.i(TAG, "Location: " + mCurrentLocation.getLongitude() + " " + mCurrentLocation.getLatitude());

                invokeGooglePlacesAPIService();
            }
        };
    }

    private void invokeGooglePlacesAPIService() {

        Location location = new Location("SearchActivity");
        location.setLatitude(mCurrentLocation.getLatitude());
        location.setLongitude(mCurrentLocation.getLongitude());

        // Initialise api with all values
        GooglePlaceSearchAPI api = new GooglePlaceSearchAPI(this,this, location, this.placeType);
        api.execute();
    }

    @Override
    public void onGoogleAPICallEnded(ArrayList<GooglePlace> listItems) {

        if (listItems.size() == 0) {
            // Sth went wrong
            Toast toast = Toast.makeText(this, R.string.internet_down, Toast.LENGTH_SHORT);
            toast.show();
        } else {

            fragment.populateView(listItems);
        }
    }


    @Override
    public void onListFragmentInteraction(GooglePlace item) {
        Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);

        Bundle b =new Bundle();
        b.putString("placeID",item.getPlaceid());
        intent.putExtras(b);

        startActivity(intent);
    }

    public void onClickSearchView(View v) {
        Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("Parent", "Search");
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
