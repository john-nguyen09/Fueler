package com.snowk49.android.fueler.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.singleton.SettingPreferences;

import java.util.Map;

public class MainSettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.main_settings_screen);

        SharedPreferences sharedPreferences = SettingPreferences.getInstance()
                .getSharedPreferences();

        Map<String, ?> maps = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : maps.entrySet()) {
            Preference preference = findPreference(entry.getKey());

            if (preference instanceof ListPreference) {
                preference.setSummary(((ListPreference) preference).getEntry());
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);

        if (preference instanceof ListPreference) {
            preference.setSummary(((ListPreference) preference).getEntry());
        }
    }
}
