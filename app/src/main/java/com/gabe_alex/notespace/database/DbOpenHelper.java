package com.gabe_alex.notespace.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DbConstants.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DbConstants.NOTE_TABLE_NAME + " (" +
                DbConstants.NOTE_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbConstants.NOTE_TITLE + " NVARCHAR(255)," +
                DbConstants.NOTE_CONTENT + " TEXT NOT NULL," +
                DbConstants.NOTE_DATE + " DATE NOT NULL DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
