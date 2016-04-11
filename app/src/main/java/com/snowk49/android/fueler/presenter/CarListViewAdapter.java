package com.snowk49.android.fueler.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.model.CarTable;
import com.snowk49.android.fueler.model.FuelerDbHelper;
import com.snowk49.android.fueler.singleton.DatabaseFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class CarListViewAdapter extends ArrayAdapter<Car> {

    public static final int MAXIMUM_NUMBER_CARS = 100;

    ArrayList<Car> cars;
    ArrayList<Integer> selectedCars;
    Drawable defaultBackground;

    public CarListViewAdapter(Context context) {
        super(context, 0);

        cars = new ArrayList<>();
        FuelerDbHelper fuelerDbHelper = DatabaseFactory.getInstance();
        CarTable carTable = fuelerDbHelper.getCarTable();
        Car[] cars = carTable.get(MAXIMUM_NUMBER_CARS);

        if (cars != null) {
            this.cars.addAll(Arrays.asList(cars));
        }

        selectedCars = new ArrayList<>();
    }

    @Override
    public void addAll(Car... cars) {
        this.cars.addAll(Arrays.asList(cars));
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        cars.clear();
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Car getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Car car = cars.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.car_summary_view, parent, false);
            ViewTag viewTag = new ViewTag();

            viewTag.carImageView = (ImageView) convertView.findViewById(R.id.car_image_view);
            viewTag.carNameTextView = (TextView) convertView.findViewById(R.id.car_name_text_view);
            convertView.setTag(viewTag);

            defaultBackground = convertView.getBackground();
        }

        ViewTag viewTag = (ViewTag) convertView.getTag();
        viewTag.carNameTextView.setText(car.getCarName());
        if (selectedCars.contains(position)) {
            Picasso.with(getContext())
                    .load(R.drawable.ic_done_black)
                    .into(viewTag.carImageView);
        } else {
            Picasso.with(getContext())
                    .load(car.getImagePath())
                    .into(viewTag.carImageView);
        }

        return convertView;
    }

    public void removeAll(Car[] cars) {
        this.cars.removeAll(Arrays.asList(cars));

        notifyDataSetChanged();
    }

    public void fetchAll() {
        FuelerDbHelper fuelerDbHelper = DatabaseFactory.getInstance();
        CarTable carTable = fuelerDbHelper.getCarTable();
        Car[] cars = carTable.get(MAXIMUM_NUMBER_CARS);

        if (cars != null) {
            for (Car newCar : cars) {
                if (!this.cars.contains(newCar)) {
                    this.cars.add(newCar);
                }
            }

            notifyDataSetChanged();
        }
    }

    public void toggleSelection(int position) {
        selectCar(position, !selectedCars.contains(position));
    }

    public int getSelectedCount() {
        return selectedCars.size();
    }

    public ArrayList<Integer> getSelectedCars() {
        return selectedCars;
    }

    public void removeSelection() {
        selectedCars.clear();
        notifyDataSetChanged();
    }

    void selectCar(int position, boolean value) {
        if (value) {
            selectedCars.add(position);
        } else {
            selectedCars.remove(Integer.valueOf(position));
        }

        notifyDataSetChanged();
    }

    private static class ViewTag {

        public ImageView carImageView;
        public TextView carNameTextView;
    }
}
