package com.example.cityguideapp.views;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.models.UserList;
import com.example.cityguideapp.services.googleAPI.GooglePlacesAPI;
import com.example.cityguideapp.services.database.DatabaseAdapter;

import java.util.ArrayList;

public class PlacesActivity extends AppCompatActivity implements GooglePlaceFragment.OnListFragmentInteractionListener, GooglePlacesAPI.OnGoogleAPICallEnded{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        String listID = getIntent().getExtras().getString("list");

        UserList list = readUserListFromDB(listID);

        //TODO: Fetch place Descriptions FROM GoogleAPI and send them to fragment on  callback
        ArrayList<GooglePlace> places = list.getPlaces();
    }

    @Override
    public void onListFragmentInteraction(GooglePlace item) {

        Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);

        Bundle b =new Bundle();
        b.putString("placeID",item.getPlaceid());
        intent.putExtras(b);

        startActivity(intent);
    }

    public UserList readUserListFromDB(String id){
        DatabaseAdapter adapter = new DatabaseAdapter(this,1);
        adapter.startConnection();

        UserList listItems = adapter.selectUserListByID(id);

        adapter.closeConnection();
        return  listItems;
    }

    @Override
    public void onGoogleAPICallEnded(ArrayList<GooglePlace> listItems) {

        GooglePlaceFragment fragment = GooglePlaceFragment.newInstance(1,listItems);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_list_placeholder,fragment);
        ft.commit();
    }
}
