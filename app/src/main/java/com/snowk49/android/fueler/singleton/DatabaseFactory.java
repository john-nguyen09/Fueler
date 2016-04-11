package com.snowk49.android.fueler.singleton;

import android.content.Context;

import com.snowk49.android.fueler.model.FuelerDbHelper;

public final class DatabaseFactory {

    private static FuelerDbHelper fuelerDbHelper;

    public static void init(Context context) {
        fuelerDbHelper = new FuelerDbHelper(context);
    }

    public static FuelerDbHelper getInstance() {
        if (fuelerDbHelper == null) {
            throw new IllegalStateException("init method has not been called");
        }

        return fuelerDbHelper;
    }

    public static void close() {
        fuelerDbHelper.close();
        fuelerDbHelper = null;
    }
}
