package com.example.cityguideapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.cityguideapp.R;
import com.example.cityguideapp.views.account.AccountActivity;
import com.example.cityguideapp.views.account.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Baal on 3/12/2018.
 */

public class BaseNavigationActivity extends AppCompatActivity {

    protected BottomNavigationView navigation;
    public ProgressDialog mProgressDialog;

    protected void initialiseNavView(int index){

        // Set navigation bottom
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(index).setChecked(true);
    }

    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    if(getLocalClassName().compareTo(MainActivity.class.getSimpleName()) != 0){
                        intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    return true;
                case R.id.navigation_lists:
                    if(getLocalClassName().compareTo(UserListActivity.class.getSimpleName()) != 0){
                        intent = new Intent(getBaseContext(), UserListActivity.class);
                        startActivity(intent);
                    }
                    return true;
                case R.id.navigation_account:
                    if(getLocalClassName().compareTo(LoginActivity.class.getSimpleName()) != 0) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {

                            // User is signed in
                            intent = new Intent(getBaseContext(), AccountActivity.class);
                            startActivity(intent);
                        } else {

                            // No user is signed in
                            intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
