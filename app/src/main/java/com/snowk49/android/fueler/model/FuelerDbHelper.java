package com.snowk49.android.fueler.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.snowk49.android.fueler.model.FuelRecord.FuelEntry;
import static com.snowk49.android.fueler.model.Car.CarEntry;

public class FuelerDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Fueler.db";

    static final String TEXT = " TEXT";
    static final String DATE = TEXT;
    static final String DECIMAL = " REAL";
    static final String NUMBER = " INTEGER";
    static final String BOOLEAN = NUMBER;
    static final String PRIMARY = " PRIMARY KEY";
    static final String AUTO_INC = " AUTOINCREMENT";
    static final String SEPARATOR = ",";

    private static final String CREATE_FUEL_RECORD_TABLE =
            "CREATE TABLE " + FuelEntry.TABLE_NAME + "(" +
            FuelEntry._ID + NUMBER + PRIMARY + AUTO_INC + SEPARATOR +
            FuelEntry.COLUMN_NAME_CAR_ID + NUMBER + SEPARATOR +
            FuelEntry.COLUMN_NAME_DATE + DATE + SEPARATOR +
            FuelEntry.COLUMN_NAME_TOTAL_COST + DECIMAL + SEPARATOR +
            FuelEntry.COLUMN_NAME_LITRE + DECIMAL + SEPARATOR +
            FuelEntry.COLUMN_NAME_ODOMETER + DECIMAL + SEPARATOR +
            FuelEntry.COLUMN_NAME_PARTIAL_FILLUP + BOOLEAN + SEPARATOR +
            FuelEntry.COLUMN_NAME_DESCRIPTION + TEXT + SEPARATOR +
            "FOREIGN KEY(" + FuelEntry.COLUMN_NAME_CAR_ID +
                ") REFERENCES " + CarEntry.TABLE_NAME + "(" + CarEntry._ID + ")" +
            ")";

    private static final String CREATE_CAR_RECORD_TABLE =
            "CREATE TABLE " + CarEntry.TABLE_NAME + "(" +
            CarEntry._ID + NUMBER + PRIMARY + AUTO_INC + SEPARATOR +
            CarEntry.COLUMN_NAME_CAR_NAME + TEXT + SEPARATOR +
            CarEntry.COLUMN_NAME_IMAGE_PATH + TEXT +
            ")";

    public FuelerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FUEL_RECORD_TABLE);
        db.execSQL(CREATE_CAR_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public CarTable getCarTable() {
        return new CarTable(getWritableDatabase());
    }

    public FuelRecordTable getFuelRecordTable() {
        return new FuelRecordTable(getWritableDatabase());
    }
}
