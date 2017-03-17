

package com.fachati.allUnitConverter.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fachati.allUnitConverter.R;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Preferences.getInstance(this).isLightTheme()) {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
    }


    protected void setToolbarTitle(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
    }

    protected void setToolbarHomeNavigation(boolean upAsHome) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(upAsHome);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
