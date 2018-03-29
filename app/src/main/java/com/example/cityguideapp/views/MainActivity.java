package com.example.cityguideapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cityguideapp.R;

public class MainActivity extends BaseNavigationActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initialiseNavView(0);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.mainSearchView:
                bundle.putString("Parent", "Main");

                Intent intent = new Intent(getBaseContext(), DescriptionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return;
            case R.id.parks_text_view:
                bundle.putString("URL", "parks");
                break;
            case R.id.malls_text_view:

                bundle.putString("URL", "malls");
                break;
            case R.id.places_text_view:
                bundle.putString("URL", "places");
                break;
            case R.id.museums_text_view:
                bundle.putString("URL", "museums");
                break;
        }

        Intent intent = new Intent(getBaseContext(), SearchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
