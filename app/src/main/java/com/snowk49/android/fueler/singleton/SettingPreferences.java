package com.snowk49.android.fueler.singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.snowk49.android.fueler.R;

public class SettingPreferences {

    public static final String KEY_DISTANCE_UNIT = "distance_unit";
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_VOLUME_UNIT = "volume_unit";

    private static SettingPreferences sInstance;

    public static void init(Context context) {
        sInstance = new SettingPreferences(context);
    }

    public static SettingPreferences getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("SettingPreferences has not been inited");
        }

        return sInstance;
    }

    public static void close() {
        sInstance = null;
    }

    private Context context;
    private SharedPreferences sharedPreferences;

    public SettingPreferences(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        stringInitIfEmpty(KEY_DISTANCE_UNIT, R.string.default_distance_unit_value);
        stringInitIfEmpty(KEY_CURRENCY, R.string.default_currency_value);
        stringInitIfEmpty(KEY_VOLUME_UNIT, R.string.default_volume_unit);
        editor.apply();
    }

    public String getDistanceUnit() {
        return sharedPreferences.getString(KEY_DISTANCE_UNIT, null);
    }

    public String getCurrency() {
        return sharedPreferences.getString(KEY_CURRENCY, null);
    }

    public String getVolumeUnit() {
        return sharedPreferences.getString(KEY_VOLUME_UNIT, null);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private void stringInitIfEmpty(String key, int resId) {
        if (sharedPreferences.getString(key, null) == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(key, context.getString(resId));
            editor.apply();
        }
    }
}
