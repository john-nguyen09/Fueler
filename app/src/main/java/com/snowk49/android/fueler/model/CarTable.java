package com.snowk49.android.fueler.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.snowk49.android.fueler.model.Car.CarEntry;

public class CarTable extends FuelerTable{

    public CarTable(SQLiteDatabase db) {
        super(db);
    }

    public void insert(Car car) {
        ContentValues values = new ContentValues();

        values.put(CarEntry.COLUMN_NAME_CAR_NAME, car.getCarName());
        values.put(CarEntry.COLUMN_NAME_IMAGE_PATH, car.getImagePath().toString());

        db.insertOrThrow(CarEntry.TABLE_NAME, null, values);
    }

    public Car[] get(int limit) {
        Car[] cars = null;
        String[] projection = new String[] {
                CarEntry._ID,
                CarEntry.COLUMN_NAME_CAR_NAME,
                CarEntry.COLUMN_NAME_IMAGE_PATH
        };
        Cursor cursor = db.query(CarEntry.TABLE_NAME,
                projection,
                null, null,
                null, null,
                null, String.valueOf(limit));

        if (cursor != null && cursor.moveToFirst()) {
            int i = 0;
            FuelRecordTable fuelRecordTable = new FuelRecordTable(db);
            cars = new Car[cursor.getCount()];

            do {
                cars[i] = new Car();
                cars[i].retrieveData(cursor);
                fuelRecordTable.get(cars[i]);

                i++;
            } while (cursor.moveToNext());

            cursor.close();
        }

        return cars;
    }

    public void delete(Car[] cars) {
        String delete = CarEntry._ID + " IN (";
        String[] ids = new String[cars.length];

        for (int i = 0; i < cars.length; i++) {
            cars[i].selfDestroy();
            ids[i] = String.valueOf(cars[i].getId());

            if (i == cars.length - 1) {
                delete += "?)";
            } else {
                delete += "?,";
            }
        }

        db.delete(CarEntry.TABLE_NAME, delete, ids);
    }
}
