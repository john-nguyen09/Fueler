package com.snowk49.android.fueler;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.snowk49.android.fueler.singleton.DatabaseFactory;
import com.snowk49.android.fueler.singleton.FragmentChanger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Singleton initialize
        DatabaseFactory.init(this);
        FragmentChanger.init(this, R.id.main_frame);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_car) {
            FragmentChanger.getInstance().getMainFragment().addCar();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        DatabaseFactory.close();
        FragmentChanger.close();

        super.onDestroy();
    }
}
