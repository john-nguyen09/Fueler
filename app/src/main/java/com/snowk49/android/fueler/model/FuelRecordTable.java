package com.snowk49.android.fueler.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.snowk49.android.fueler.model.FuelRecord.FuelEntry;

public class FuelRecordTable extends FuelerTable {

    public FuelRecordTable(SQLiteDatabase db) {
        super(db);
    }

    public void insert(FuelRecord fuelRecord) {
        ContentValues values = new ContentValues();

        values.put(FuelEntry.COLUMN_NAME_CAR_ID, fuelRecord.getCar().getId());
        values.put(FuelEntry.COLUMN_NAME_DATE, fuelRecord.getDate().getTime());
        values.put(FuelEntry.COLUMN_NAME_TOTAL_COST, fuelRecord.getTotalCost());
        values.put(FuelEntry.COLUMN_NAME_LITRE, fuelRecord.getLitre());
        values.put(FuelEntry.COLUMN_NAME_ODOMETER, fuelRecord.getOdometer());
        values.put(FuelEntry.COLUMN_NAME_PARTIAL_FILLUP, fuelRecord.isPartialFillup());
        values.put(FuelEntry.COLUMN_NAME_DESCRIPTION, fuelRecord.getDescription());

        db.insert(FuelEntry.TABLE_NAME, null, values);
    }

    public void get(Car car) {
        String[] projection = new String[] {
                "*"
        };
        String selection = FuelEntry.COLUMN_NAME_CAR_ID + "=?";
        String[] selectionArgs = new String[] {
               String.valueOf(car.getId())
        };
        String orderBy = FuelEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(FuelEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            FuelRecord[] fuelRecords = new FuelRecord[cursor.getCount()];
            int i = 0;

            do {
                fuelRecords[i] = new FuelRecord();
                fuelRecords[i].retrieveData(cursor);
                fuelRecords[i].setCar(car);

                i++;
            } while (cursor.moveToNext());

            cursor.close();
            car.setFuelRecords(fuelRecords);
        }
    }

    public void remove(Car car) {
        String delete = FuelEntry.COLUMN_NAME_CAR_ID + "=?";
        String[] ids = new String[] {
                String.valueOf(car.getId())
        };

        db.delete(FuelEntry.TABLE_NAME, delete, ids);
    }
}
