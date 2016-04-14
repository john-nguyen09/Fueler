package com.snowk49.android.fueler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.snowk49.android.fueler.singleton.DatabaseFactory;
import com.snowk49.android.fueler.singleton.FragmentChanger;
import com.snowk49.android.fueler.singleton.SettingPreferences;
import com.snowk49.android.fueler.view.MainSettingsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_car) {
            FragmentChanger.getInstance().getMainFragment().addCar();

            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            FragmentChanger.getInstance().changeFragment(new MainSettingsFragment());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        DatabaseFactory.close();
        FragmentChanger.close();
        SettingPreferences.close();

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        DatabaseFactory.init(this);
        FragmentChanger.init(this, R.id.main_frame);
        SettingPreferences.init(this);

        SettingPreferences settingPreferences = SettingPreferences.getInstance();

        super.onStart();
    }
}
