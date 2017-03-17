

package com.fachati.allUnitConverter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.fachati.allUnitConverter.R;
import com.fachati.allUnitConverter.fragments.PreferencesFragment;


public class PreferencesActivity extends BaseActivity {

    public static void start(Context context) {
        Intent i = new Intent(context, PreferencesActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        setupToolbar();
        setToolbarHomeNavigation(true);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, PreferencesFragment.newInstance())
                    .commit();
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
