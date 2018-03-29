package com.example.cityguideapp.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.models.UserList;
import com.example.cityguideapp.services.UserListAdapter;
import com.example.cityguideapp.services.database.DatabaseAdapter;
import com.example.cityguideapp.views.account.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class UserListActivity extends BaseNavigationActivity {

    FirebaseUser currentUser;
    private ArrayList<UserList>listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            // Take user to log in screen
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_lists);
            this.initialiseNavView(1);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserListActivity.this.showInputDialog();
                }
            });

            this.listItems = this.readUserListFromDB(currentUser.getUid());
            showLists(listItems);
        }

    }

    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.userList_dialogAdd);


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String listName = input.getText().toString();
                UserList list = new UserList(listName);
                UserListActivity.this.writeUserListToDB(list,currentUser.getUid());

                //refresh page
                UserListActivity.this.listItems = UserListActivity.this.readUserListFromDB(currentUser.getUid());
                showLists(listItems);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showDeleteListDialog(final UserList list,final AdapterView.OnItemClickListener listener){
        //unregister click listener
        final GridView gridview = findViewById(R.id.gridviewList);
        gridview.setOnItemClickListener(null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.userList_dialogDelete);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserListFromDB(list, currentUser.getUid());

                //refresh page
                UserListActivity.this.listItems = UserListActivity.this.readUserListFromDB(currentUser.getUid());
                showLists(listItems);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //reset click listener
                gridview.setOnItemClickListener(listener);
            }
        });

        builder.show();
    }

    private void showLists(ArrayList<UserList>listItems){
        final GridView gridview = findViewById(R.id.gridviewList);

        UserListAdapter loader = new UserListAdapter(this, R.layout.list_item_template, listItems);
        gridview.setAdapter(loader);

        final AdapterView.OnItemClickListener listener =  new AdapterView.OnItemClickListener() {

            //Take care if button is present: IS FOCUSABLE. If it, this, able focus for it
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                UserList list = (UserList)gridview.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString("list", Integer.toString(list.getId()));

                Intent intent = new Intent(getBaseContext(), PlacesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        gridview.setOnItemClickListener(listener);

        gridview.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        UserList list = (UserList)gridview.getItemAtPosition(i);
                        showDeleteListDialog(list,listener);

                        return false;
                    }
                }
        );


    }

    public ArrayList<UserList> readUserListFromDB(String userUid){
        DatabaseAdapter adapter = new DatabaseAdapter(this,1);
        adapter.startConnection();

        ArrayList<UserList>listItems = adapter.selectUserListByUID(userUid);

        adapter.closeConnection();
        return  listItems;
    }

    public void writeUserListToDB(UserList list, String userUid){
        DatabaseAdapter adapter = new DatabaseAdapter(this,1);
        adapter.startConnection();

        adapter.insertUserList(list,userUid);

        adapter.closeConnection();
    }

    public void deleteUserListFromDB(UserList list, String userUid){
        DatabaseAdapter adapter = new DatabaseAdapter(this,1);
        adapter.startConnection();

        adapter.deleteUserList(list,userUid);

        adapter.closeConnection();
    }

}
