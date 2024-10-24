package com.s333329.mappe2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "birthdays.db";
    private static final int DATABASE_VERSION = 1;
    public static final String BIRTHDAY_LIST = "birthdays";
    public static final String KOLONNE_ID = "id";
    public static final String KOLONNE_BIRTHDAY_NAME = "name";
    public static final String KOLONNE_BIRTHDAY_NUMBER = "number";
    public static final String KOLONNE_BIRTHDAY_DATE = "date";

    // lager tabell til database
    private static final String CREATE_TABLE_TASKS =
            "CREATE TABLE " + BIRTHDAY_LIST+ "(" +
                    KOLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KOLONNE_BIRTHDAY_NAME +" TEXT NOT NULL," +
                    KOLONNE_BIRTHDAY_NUMBER +" TEXT NOT NULL," +
                    KOLONNE_BIRTHDAY_DATE +" TEXT NOT NULL)";

    // importerer variabel DATABASE_NAVN og DATABASE_VERSION
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // hvis den ikke finner en eksisterende database så kjører den opcreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
    }


    // eksisterer det en database fra før, så oppdateres den
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { onCreate(db);
    }
}