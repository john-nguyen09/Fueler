package com.snowk49.android.fueler.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.presenter.FuelDetailsPagerAdapter;

public class FuelDetailsFragment extends Fragment {

    private Car car;
    private FuelDetailsPagerAdapter fuelDetailsPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuel_details_fragment, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fuel_details_pager);
        fuelDetailsPagerAdapter = new FuelDetailsPagerAdapter(getFragmentManager());
        fuelDetailsPagerAdapter.setCar(car);
        viewPager.setAdapter(fuelDetailsPagerAdapter);

        return view;
    }

    public void setCar(Car car) {
        this.car = car;

        if (fuelDetailsPagerAdapter != null) {
            fuelDetailsPagerAdapter.setCar(car);
        }
    }
}
