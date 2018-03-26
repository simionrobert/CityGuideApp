package com.example.cityguideapp.views;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.cityguideapp.R;
import com.example.cityguideapp.services.GooglePlacesAPI;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

public class SearchActivity extends BaseLocationActivity{

    private static final String TAG = "SearchActivity";

    private String placeType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // TODO: Use Google Places Web Service
        String url = getIntent().getExtras().getString("URL");
        switch(url){
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
    }

    @Override
    protected void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                mRequestingLocationUpdates = false;
                Log.i(TAG, "Location: " + mCurrentLocation.getLongitude() +" "+mCurrentLocation.getLatitude());

                invokeGooglePlacesAPIService();
            }
        };
    }

    private void invokeGooglePlacesAPIService() {
        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    //Take care if button is present: IS FOCUSABLE. If it, this, able focus for it
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                        Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);
                        startActivity(intent);
                    }
                }
        );

        Location location = new Location("SearchActivity");
        location.setLatitude(mCurrentLocation.getLatitude());
        location.setLongitude(mCurrentLocation.getLongitude());

        // Initialise api with all values
        GooglePlacesAPI api = new GooglePlacesAPI(this, R.layout.search_item_template, listView, location, this.placeType);
        api.execute();
    }

    public void onClickSearchView(View v){
        Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("Parent", "Search");
        intent.putExtras(bundle);

        startActivity(intent);
    }

}
