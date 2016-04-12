package com.snowk49.android.fueler.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.snowk49.android.fueler.MainActivity;
import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.model.FuelRecord;
import com.snowk49.android.fueler.model.FuelerDbHelper;
import com.snowk49.android.fueler.singleton.DatabaseFactory;
import com.snowk49.android.fueler.singleton.FragmentChanger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddFuelRecordFragment extends Fragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener{

    MainActivity context;
    Car car;
    Button dateButton;
    EditText totalCostEditText;
    EditText odometerValueEditText;
    EditText descriptionEditText;
    CheckBox partialFillupCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        this.context = (MainActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        context.setTitle(R.string.add_fuel_record);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fuel_record_fragment, container, false);

        dateButton = (Button) view.findViewById(R.id.change_date_button);
        totalCostEditText = (EditText) view.findViewById(R.id.total_cost_edit_text);
        odometerValueEditText = (EditText) view.findViewById(R.id.odometer_edit_text);
        descriptionEditText = (EditText) view.findViewById(R.id.description_edit_text);
        partialFillupCheckBox = (CheckBox) view.findViewById(R.id.partial_fillup_check_box);

        dateButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.action_done_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            Date date = Calendar.getInstance().getTime();
            float totalCost;
            float odometer;

            try {
                date = FuelRecord.getDateFormat().parse(dateButton.getText().toString());
            } catch (ParseException ignored) {
            }

            try {
                totalCost = Float.parseFloat(totalCostEditText.getText().toString());
                odometer = Float.parseFloat(odometerValueEditText.getText().toString());
            } catch (NumberFormatException e) {
                return true;
            }
            boolean partialFillup = partialFillupCheckBox.isChecked();
            String description = descriptionEditText.getText().toString();

            FuelRecord fuelRecord = new FuelRecord();
            fuelRecord.setDate(date);
            fuelRecord.setTotalCost(totalCost);
            fuelRecord.setOdometer(odometer);
            fuelRecord.setPartialFillup(partialFillup);
            fuelRecord.setDescription(description);
            fuelRecord.setCar(car);

            FuelerDbHelper dbHelper = DatabaseFactory.getInstance();

            dbHelper.getFuelRecordTable().insert(fuelRecord);

            FragmentChanger.getInstance().goBackFragment();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Calendar today = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                this,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar date = Calendar.getInstance();
        date.set(year, monthOfYear, dayOfMonth);

        if (date.get(Calendar.YEAR) == year &&
                date.get(Calendar.MONTH) == monthOfYear &&
                date.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
            dateButton.setText(getContext().getText(R.string.change_date_button));
        } else {
            date.set(year, monthOfYear, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat(
                    context.getString(R.string.date_display_format),
                    context.getResources().getConfiguration().locale);

            dateButton.setText(sdf.format(date));
        }
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
