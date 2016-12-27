package com.snowk49.android.fueler.presenter;

import android.os.Environment;
import android.util.Log;

import com.snowk49.android.fueler.singleton.DatabaseFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BackupController {

    public void index() {
        File databaseFile = DatabaseFactory.getInstance().getDatabaseFile();
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        String fileName = "snow.db";
        File backupFile = new File(sdcardPath + File.separator + fileName);

        Log.d(this.getClass().getName(), backupFile.getAbsolutePath());

        try {
            backupFile.createNewFile();
            InputStream in = new FileInputStream(databaseFile);
            OutputStream out = new FileOutputStream(backupFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
