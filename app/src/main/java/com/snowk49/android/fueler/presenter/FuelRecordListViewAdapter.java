package com.snowk49.android.fueler.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.model.FuelRecord;
import com.snowk49.android.fueler.model.FuelRecordTable;
import com.snowk49.android.fueler.model.FuelerDbHelper;
import com.snowk49.android.fueler.singleton.DatabaseFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class FuelRecordListViewAdapter extends ArrayAdapter<FuelRecord> {

    Car car;
    ArrayList<FuelRecord> fuelRecords;

    public FuelRecordListViewAdapter(Context context) {
        super(context, 0);

        fuelRecords = new ArrayList<>();
    }

    @Override
    public void addAll(FuelRecord... items) {
        fuelRecords.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    @Override
    public FuelRecord getItem(int position) {
        return fuelRecords.get(position);
    }

    @Override
    public int getCount() {
        return fuelRecords.size();
    }

    @Override
    public long getItemId(int position) {
        return fuelRecords.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FuelRecord fuelRecord = fuelRecords.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewTag viewTag = new ViewTag();

            convertView = layoutInflater.inflate(R.layout.fuel_record_list_view_item,
                    parent, false);
            viewTag.date = (TextView) convertView.findViewById(R.id.fuel_record_date);
            viewTag.totalCost = (TextView) convertView.findViewById(R.id.fuel_record_total_cost);
            viewTag.odometer = (TextView) convertView.findViewById(R.id.fuel_record_odometer);
            viewTag.partialFillup = (TextView) convertView
                    .findViewById(R.id.fuel_record_partial_fillup);
            viewTag.description = (TextView) convertView.findViewById(R.id.fuel_record_description);

            convertView.setTag(viewTag);
        }

        Context context = getContext();
        ViewTag viewTag = (ViewTag) convertView.getTag();
        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.date_display_format),
                context.getResources().getConfiguration().locale);

        viewTag.date.setText(sdf.format(fuelRecord.getDate()));
        viewTag.totalCost.setText(context.getString(R.string.fuel_record_total_cost_value,
                "$" ,fuelRecord.getTotalCost()));
        viewTag.odometer.setText(context.getString(R.string.fuel_record_odometer_value,
                fuelRecord.getOdometer(), "km"));
        viewTag.partialFillup.setText(fuelRecord.isPartialFillup() ?
                context.getString(R.string.yes) : context.getString(R.string.no));
        viewTag.description.setText(context.getString(R.string.fuel_record_description_value,
                fuelRecord.getDescription()));

        return convertView;
    }

    public void setCar(Car car) {
        this.car = car;
        populateFuelRecords();
    }

    void populateFuelRecords() {
        FuelerDbHelper dbHelper = DatabaseFactory.getInstance();
        FuelRecordTable fuelRecordTable = dbHelper.getFuelRecordTable();

        fuelRecordTable.get(car);
        addAll(car.getFuelRecords());
    }

    private static class ViewTag {
        public TextView date;
        public TextView totalCost;
        public TextView odometer;
        public TextView partialFillup;
        public TextView description;
    }
}
