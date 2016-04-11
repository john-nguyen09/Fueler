package com.snowk49.android.fueler.model;

import android.database.sqlite.SQLiteDatabase;

public abstract class FuelerTable {

    protected SQLiteDatabase db;

    public FuelerTable(SQLiteDatabase db) {
        this.db = db;
    }
}
