package com.snowk49.android.fueler.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.Date;

public final class FuelRecord {

    private Car car;
    private int id;
    private Date date;
    private float totalCost;
    private float litre;
    private float odometer;
    private boolean partialFillup;
    private String description;

    public FuelRecord() {

    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getLitre() {
        return litre;
    }

    public void setLitre(float litre) {
        this.litre = litre;
    }

    public float getOdometer() {
        return odometer;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }

    public boolean isPartialFillup() {
        return partialFillup;
    }

    public void setPartialFillup(boolean partialFillup) {
        this.partialFillup = partialFillup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void retrieveData(Cursor cursor) {
        if (cursor != null) {
            int idIndex = cursor.getColumnIndexOrThrow(FuelEntry._ID);
            int dateIndex = cursor.getColumnIndex(FuelEntry.COLUMN_NAME_DATE);
            int totalCostIndex = cursor.getColumnIndex(FuelEntry.COLUMN_NAME_TOTAL_COST);
            int odometerIndex = cursor.getColumnIndex(FuelEntry.COLUMN_NAME_ODOMETER);
            int partialFillupIndex = cursor.getColumnIndex(FuelEntry.COLUMN_NAME_PARTIAL_FILLUP);
            int descriptionIndex = cursor.getColumnIndex(FuelEntry.COLUMN_NAME_DESCRIPTION);

            id = cursor.getInt(idIndex);
            date = new Date();
            date.setTime(cursor.getLong(dateIndex));
            totalCost = cursor.getFloat(totalCostIndex);
            odometer = cursor.getFloat(odometerIndex);
            partialFillup = cursor.getInt(partialFillupIndex) == 1;
            description = cursor.getString(descriptionIndex);
        }
    }

    public static abstract class FuelEntry implements BaseColumns {

        public static final String TABLE_NAME = "FuelRecord";
        public static final String COLUMN_NAME_CAR_ID = "carId";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TOTAL_COST = "totalCost";
        public static final String COLUMN_NAME_LITRE = "litre";
        public static final String COLUMN_NAME_ODOMETER = "odometer";
        public static final String COLUMN_NAME_PARTIAL_FILLUP = "partialFillup";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}
