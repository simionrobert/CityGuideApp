package com.example.cityguideapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.UserList;
import com.example.cityguideapp.services.UserListAdapter;

import java.util.ArrayList;

public class UserListActivity extends BaseNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        this.initialiseNavView(1);

        // Get UserList from SQLite and images from cache
        ArrayList<UserList>listItems = new ArrayList<>();
        UserList list = new UserList("Lista1",null);
        UserList list2 = new UserList("Lista2",null);
        listItems.add(list);
        listItems.add(list2);
        UserList list3 = new UserList("Lista3",null);
        UserList list4 = new UserList("Lista4",null);
        listItems.add(list3);
        listItems.add(list4);

        GridView gridview = findViewById(R.id.gridviewList);
        UserListAdapter loader = new UserListAdapter(this, R.layout.list_item_template, listItems);
        gridview.setAdapter(loader);
        gridview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    //Take care if button is present: IS FOCUSABLE. If it, this, able focus for it
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                        Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);
                        startActivity(intent);
                    }
                }
        );


    }
}
