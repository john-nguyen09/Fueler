package com.snowk49.android.fueler.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.view.FuelRecordListViewFragment;

public class FuelDetailsPagerAdapter extends FragmentStatePagerAdapter {

    Fragment[] fragments;

    public FuelDetailsPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments = new Fragment[] {
                new FuelRecordListViewFragment()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    public void setCar(Car car) {
        for (Fragment fragment : fragments) {
            if (fragment instanceof  FuelRecordListViewFragment) {
                ((FuelRecordListViewFragment) fragment).setCar(car);
            }
        }
    }
}
