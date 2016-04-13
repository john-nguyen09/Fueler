package com.snowk49.android.fueler.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.snowk49.android.fueler.MainActivity;
import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.model.CarTable;
import com.snowk49.android.fueler.model.FuelRecordTable;
import com.snowk49.android.fueler.presenter.CarListViewAdapter;
import com.snowk49.android.fueler.singleton.DatabaseFactory;
import com.snowk49.android.fueler.singleton.FragmentChanger;

import java.util.ArrayList;

public class CarListViewFragment extends Fragment implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    private MainActivity context;
    private CarListViewAdapter carListViewAdapter;
    private ActionMode actionMode;

    public CarListViewFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        this.context = (MainActivity) context;

        super.onAttach(context);
    }

    @Override
    public void onResume() {
        context.setTitle(R.string.app_name);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_list_view_fragment, container, false);

        ListView carListView = (ListView) view.findViewById(R.id.car_list_view);
        carListViewAdapter = new CarListViewAdapter(context);
        carListView.setAdapter(carListViewAdapter);

        carListView.setOnItemClickListener(this);
        carListView.setOnItemLongClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (actionMode != null) {
            selectItem(position);
        } else {
            FuelDetailsFragment fuelDetailsFragment = new FuelDetailsFragment();
            fuelDetailsFragment.setCar(carListViewAdapter.getItem(position));

            FragmentChanger.getInstance().changeFragment(fuelDetailsFragment);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
        return true;
    }

    public void addCar() {
        AddCarFragment addCarFragment = new AddCarFragment();
        addCarFragment.setAdapter(carListViewAdapter);

        FragmentChanger.getInstance().changeFragment(addCarFragment);
    }

    void selectItem(int position) {
        carListViewAdapter.toggleSelection(position);
        boolean hasCheckedItems = carListViewAdapter.getSelectedCount() > 0;

        if (hasCheckedItems && actionMode == null) {
            actionMode = context.startSupportActionMode(new CarListViewActionMode());
        } else if (!hasCheckedItems && actionMode != null) {
            actionMode.finish();
        }

        if (actionMode != null) {
            String selected = context.getString(R.string.selected_count,
                    carListViewAdapter.getSelectedCount());
            actionMode.setTitle(selected);
        }
    }

    private class CarListViewActionMode implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.car_listview_context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    ArrayList<Integer> selectedCars = carListViewAdapter.getSelectedCars();
                    FuelRecordTable fuelRecordTable = DatabaseFactory.getInstance()
                            .getFuelRecordTable();
                    CarTable carTable = DatabaseFactory.getInstance().getCarTable();
                    Car[] deletedCars = new Car[selectedCars.size()];

                    for (int i = 0; i < selectedCars.size(); i++) {
                        deletedCars[i] = carListViewAdapter.getItem(selectedCars.get(i));
                        fuelRecordTable.remove(deletedCars[i]);
                    }

                    carListViewAdapter.removeAll(deletedCars);
                    carTable.delete(deletedCars);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            carListViewAdapter.removeSelection();
            actionMode = null;
        }
    }
}
