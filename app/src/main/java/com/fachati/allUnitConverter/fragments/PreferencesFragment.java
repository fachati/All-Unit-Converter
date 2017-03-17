

package com.fachati.allUnitConverter.fragments;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.fachati.allUnitConverter.R;
import com.fachati.allUnitConverter.activity.Preferences;


public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    public static PreferencesFragment newInstance() {
        return new PreferencesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }

    @Override
    public void onResume() {
        super.onResume();
        Preferences.getInstance(getActivity()).getPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Preferences.getInstance(getActivity()).getPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }




    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Preferences.PREFS_THEME)) {
            getActivity().finish();
        }
    }
}
