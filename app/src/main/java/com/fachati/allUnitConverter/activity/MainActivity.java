package com.fachati.allUnitConverter.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.fachati.allUnitConverter.R;
import com.fachati.allUnitConverter.fragments.ConversionFragment;
import com.fachati.allUnitConverter.models.Conversion;
import com.fachati.allUnitConverter.util.Conversions;

public class MainActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private DrawerLayout mDrawerLayout;
    private Conversions mConversions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        Preferences.getInstance(this).getPreferences().registerOnSharedPreferenceChangeListener(this);
        mConversions = Conversions.getInstance();

        setContentView(R.layout.activity_main);
        setupToolbar();
        setToolbarHomeNavigation(true);
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        int conversion = Preferences.getInstance(this).getLastConversion();
        setToolbarTitle(mConversions.getById(conversion).getLabelResource());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
        setupDrawer(getMenuPositionOfConversion(conversion));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ConversionFragment.newInstance(conversion))
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Preferences.getInstance(this).getPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    private void setupDrawer(int state) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        navigationView.getMenu().getItem(state).setChecked(true);
        navigationView.setItemBackgroundResource(Preferences.getInstance(this).isLightTheme() ?
                R.drawable.navigation_item_background_light : R.drawable.navigation_item_background);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.drawer_settings:
                        PreferencesActivity.start(MainActivity.this);
                        return true;

                    default:
                        menuItem.setChecked(true);
                        int conversion = getConversionFromDrawer(menuItem.getItemId());
                        setToolbarTitle(mConversions.getById(conversion).getLabelResource());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, ConversionFragment.newInstance(conversion))
                                .commit();
                        return true;
                }
            }
        });
    }

    @Conversion.id
    private int getConversionFromDrawer(int itemId) {
        switch (itemId) {
            case R.id.drawer_area:
                return Conversion.AREA;

            case R.id.drawer_cooking:
                return Conversion.COOKING;

            case R.id.drawer_currency:
                return Conversion.CURRENCY;

            case R.id.drawer_storage:
                return Conversion.STORAGE;

            case R.id.drawer_energy:
                return Conversion.ENERGY;

            case R.id.drawer_fuel:
                return Conversion.FUEL;

            case R.id.drawer_length:
                return Conversion.LENGTH;

            case R.id.drawer_mass:
                return Conversion.MASS;

            case R.id.drawer_power:
                return Conversion.POWER;

            case R.id.drawer_pressure:
                return Conversion.PRESSURE;

            case R.id.drawer_speed:
                return Conversion.SPEED;

            case R.id.drawer_temperature:
                return Conversion.TEMPERATURE;

            case R.id.drawer_time:
                return Conversion.TIME;

            case R.id.drawer_torque:
                return Conversion.TORQUE;

            case R.id.drawer_volume:
                return Conversion.VOLUME;
        }

        return Conversion.AREA;
    }

    private int getMenuPositionOfConversion(@Conversion.id final int conversion) {
        switch (conversion) {
            case Conversion.AREA:
                return 0;
            case Conversion.COOKING:
                return 1;
            case Conversion.CURRENCY:
                return 2;
            case Conversion.STORAGE:
                return 3;
            case Conversion.ENERGY:
                return 4;
            case Conversion.FUEL:
                return 5;
            case Conversion.LENGTH:
                return 6;
            case Conversion.MASS:
                return 7;
            case Conversion.POWER:
                return 8;
            case Conversion.PRESSURE:
                return 9;
            case Conversion.SPEED:
                return 10;
            case Conversion.TEMPERATURE:
                return 11;
            case Conversion.TIME:
                return 12;
            case Conversion.TORQUE:
                return 13;
            case Conversion.VOLUME:
                return 14;
            default:
                return 0;
        }
    }

    private void hideKeyboard() {
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Preferences.PREFS_THEME)) {
            recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}