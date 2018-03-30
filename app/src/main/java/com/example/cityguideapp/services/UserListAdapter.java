package com.example.cityguideapp.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.UserList;

import java.util.ArrayList;

/**
 * Created by Baal on 3/6/2018.
 */

public class UserListAdapter extends ArrayAdapter<UserList> {

    ArrayList<UserList> items;
    private int layout;

    public UserListAdapter(Context context, int resource, ArrayList<UserList> items) {
        super(context, resource, items);

        layout = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        View v = vi.inflate(layout, null);

        v.setLayoutParams(new LinearLayout.LayoutParams(GridView.AUTO_FIT, 500));

        UserList list = getItem(position);

        if (list != null) {
            ImageView picture = (ImageView) v.findViewById(R.id.listImage);
            TextView name = (TextView) v.findViewById(R.id.listview_name);
            TextView nr = (TextView) v.findViewById(R.id.nr_elements);

            name.setText(list.getName());
            nr.setText(Integer.toString(list.getNrElements()) + " Places");

            if (list.getImage() != null)
                //TODO: set user list image
                picture.setImageBitmap(list.getImage());
        }

        return v;
    }
}



