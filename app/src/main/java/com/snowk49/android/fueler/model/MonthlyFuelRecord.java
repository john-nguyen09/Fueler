package com.snowk49.android.fueler.model;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthlyFuelRecord {

    private int counter = 0;
    private final FuelRecord[] fuelRecords;
    private ArrayList<Integer> separator;

    public MonthlyFuelRecord(FuelRecord[] fuelRecords) {
        separator = new ArrayList<>();
        this.fuelRecords = fuelRecords;

        groupRecordByMonth();
    }

    public FuelRecord[] popCurrentMonth() {
        int last = 0;

        if (counter == 0 && separator.size() == 0) {
            counter++;
            return this.fuelRecords;
        } else {
            if (counter >= separator.size()) {
                return null;
            }

            if (counter != 0) {
                last = separator.get(counter - 1);
            }

            int numberOfFuelRecords = separator.get(counter - last);
            FuelRecord[] fuelRecords = new FuelRecord[numberOfFuelRecords];

            System.arraycopy(this.fuelRecords, last, fuelRecords, 0, numberOfFuelRecords);

            return fuelRecords;
        }
    }

    public void reset() {
        counter = 0;
    }

    public FuelRecord[] getFuelRecords() {
        return fuelRecords;
    }

    // This assumes fuelRecords are sorted in descendant order
    void groupRecordByMonth() {
        Calendar currentDate = Calendar.getInstance();
        Calendar newDate = Calendar.getInstance();

        currentDate.setTime(fuelRecords[0].getDate());

        for (int i = 0; i < fuelRecords.length; i++) {
            newDate.setTime(fuelRecords[i].getDate());

            if (newDate.get(Calendar.YEAR) != currentDate.get(Calendar.YEAR) ||
                    newDate.get(Calendar.MONTH) != currentDate.get(Calendar.MONTH)) {
                separator.add(i);
            }
        }
    }
}
