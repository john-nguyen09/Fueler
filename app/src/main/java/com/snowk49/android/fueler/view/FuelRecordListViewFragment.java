package com.snowk49.android.fueler.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.snowk49.android.fueler.MainActivity;
import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.presenter.FuelRecordListViewAdapter;

public class FuelRecordListViewFragment extends Fragment {

    private Car car;
    private MainActivity context;
    private FuelRecordListViewAdapter fuelRecordListViewAdapter;

    @Override
    public void onAttach(Context context) {
        this.context = (MainActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        context.setTitle(R.string.fuel_record_list_view);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fuel_record_list_view_fragment, container, false);

        ListView fuelRecordListView = (ListView) view.findViewById(R.id.fuel_record_list_view);
        fuelRecordListViewAdapter = new FuelRecordListViewAdapter(context);
        fuelRecordListViewAdapter.setCar(car);
        fuelRecordListView.setAdapter(fuelRecordListViewAdapter);

        return view;
    }

    public void setCar(Car car) {
        this.car = car;

        if (fuelRecordListViewAdapter != null) {
            fuelRecordListViewAdapter.setCar(car);
        }
    }
}
