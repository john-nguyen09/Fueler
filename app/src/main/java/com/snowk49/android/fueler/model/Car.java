package com.snowk49.android.fueler.model;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Car {

    private int id;
    private String carName;
    private Uri imagePath;
    private MonthlyFuelRecord monthlyFuelRecord;

    public Car(String carName, Uri imagePath) {
        this.carName = carName;
        this.imagePath = imagePath;
    }

    public Car() {}


    @Override
    public boolean equals(Object o) {
        return o instanceof Car && ((Car) o).getId() == id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

    public FuelRecord[] getFuelRecords() {
        return monthlyFuelRecord.getFuelRecords();
    }

    public void setFuelRecords(FuelRecord[] fuelRecords) {
        monthlyFuelRecord = new MonthlyFuelRecord(fuelRecords);
    }

    public float getLitrePerKm() {
        // TODO: Implementing calculation of litre/km here
        FuelRecord[] fuelRecords = monthlyFuelRecord.getFuelRecords();

        return 0.0f;
    }

    public float getTotalCostAverageEachMonth() {
        if (monthlyFuelRecord != null) {
            FuelRecord[] currentMonth;
            float totalCostEveryMonth = 0.0f;
            int numberOfMonths = 0;

            monthlyFuelRecord.reset();

            while ((currentMonth = monthlyFuelRecord.popCurrentMonth()) != null) {
                float totalCost = 0.0f;

                for (FuelRecord record : currentMonth) {
                    totalCost += record.getTotalCost();
                }

                totalCostEveryMonth += totalCost;
                numberOfMonths++;
            }

            return totalCostEveryMonth / numberOfMonths;
        }

        return 0.0f;
    }

    public void retrieveData(Cursor cursor) {
        if (cursor != null) {
            int idIndex = cursor.getColumnIndexOrThrow(CarEntry._ID);
            int carNameIndex = cursor.getColumnIndexOrThrow(CarEntry.COLUMN_NAME_CAR_NAME);
            int imagePathIndex = cursor.getColumnIndexOrThrow(CarEntry.COLUMN_NAME_IMAGE_PATH);

            id = cursor.getInt(idIndex);
            carName = cursor.getString(carNameIndex);
            imagePath = Uri.parse(cursor.getString(imagePathIndex));
        }
    }

    public static abstract class CarEntry implements BaseColumns {

        public static final String TABLE_NAME = "Car";
        public static final String COLUMN_NAME_CAR_NAME = "carName";
        public static final String COLUMN_NAME_IMAGE_PATH = "imagePath";
    }
}
