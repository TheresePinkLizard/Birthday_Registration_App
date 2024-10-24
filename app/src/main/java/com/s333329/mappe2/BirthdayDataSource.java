package com.s333329.mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BirthdayDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public BirthdayDataSource(Context context) {     // konstruktøren lager et nytt databasecontext object
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {   // close, lukket dbhelperen slik at man ikke har noen åpne forbindelser til databasen
        dbHelper.close();
    }
    public Birthday addBirthday(String name, String number, String date) {  //insert metode returnerer verdien til raden som er satt inn
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KOLONNE_BIRTHDAY_NAME, name);
        values.put(DatabaseHelper.KOLONNE_BIRTHDAY_NUMBER, number);
        values.put(DatabaseHelper.KOLONNE_BIRTHDAY_DATE, date);

        long insertValues = database.insert(DatabaseHelper.BIRTHDAY_LIST, null, values);

        Cursor cursor = database.query(DatabaseHelper.BIRTHDAY_LIST, null, DatabaseHelper.KOLONNE_ID + " = " + insertValues, null, null, null, null);
        cursor.moveToFirst();
        Birthday newBirthday = cursorToBirthday(cursor);
        cursor.close();
        return newBirthday;
    }
    private Birthday cursorToBirthday(Cursor cursor) {  //cursor metode: lager et oppgave objekt og setter id til oppgaveobjektet?
        Birthday birthday = new Birthday();
        birthday.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.KOLONNE_ID)));
        birthday.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KOLONNE_BIRTHDAY_NAME)));
        birthday.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KOLONNE_BIRTHDAY_NUMBER)));
        birthday.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KOLONNE_BIRTHDAY_DATE)));
        return birthday;
    }
    public List<Birthday> findAllBirthdays() {
        Birthday oppgave = new Birthday();
        List<Birthday> oppgaver = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.BIRTHDAY_LIST, null,
                null, null, null, null, null);
        if (cursor.moveToFirst()) { // går til øverte raden
            do {
                oppgave = cursorToBirthday(cursor);
                oppgaver.add(oppgave);
            } while (cursor.moveToNext());
        }
        return oppgaver;
    }

}
