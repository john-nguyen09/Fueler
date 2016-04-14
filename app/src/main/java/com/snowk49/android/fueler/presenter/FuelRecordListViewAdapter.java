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
import com.snowk49.android.fueler.singleton.SettingPreferences;

import java.text.SimpleDateFormat;

public class FuelRecordListViewAdapter extends ArrayAdapter<FuelRecord> {

    private Car car;

    public FuelRecordListViewAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public FuelRecord getItem(int position) {
        return car.getFuelRecords()[position];
    }

    @Override
    public int getCount() {
        return car.getFuelRecords().length;
    }

    @Override
    public long getItemId(int position) {
        return car.getFuelRecords()[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FuelRecord fuelRecord = car.getFuelRecords()[position];

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
        SettingPreferences settingPreferences = SettingPreferences.getInstance();

        viewTag.date.setText(sdf.format(fuelRecord.getDate()));
        viewTag.totalCost.setText(context.getString(R.string.fuel_record_total_cost_value,
                settingPreferences.getCurrency(),
                fuelRecord.getTotalCost()));
        viewTag.odometer.setText(context.getString(R.string.fuel_record_odometer_value,
                fuelRecord.getOdometer(),
                settingPreferences.getDistanceUnit()));
        viewTag.partialFillup.setText(fuelRecord.isPartialFillup() ?
                context.getString(R.string.yes) : context.getString(R.string.no));
        viewTag.description.setText(context.getString(R.string.fuel_record_description_value,
                fuelRecord.getDescription()));

        return convertView;
    }

    public void setCar(Car car) {
        this.car = car;
        notifyDataSetChanged();
    }

    private static class ViewTag {
        public TextView date;
        public TextView totalCost;
        public TextView odometer;
        public TextView partialFillup;
        public TextView description;
    }
}
