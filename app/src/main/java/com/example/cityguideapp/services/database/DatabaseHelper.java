package com.example.cityguideapp.services.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Baal on 3/27/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ DBConstants.TABLE_USERLIST +" ("
                +DBConstants.COLUMN_USERLIST_ID+ " integer primary key autoincrement, "
                +DBConstants.COLUMN_USERLIST_NAME+" text not NULL, "
                +DBConstants.COLUMN_USERLIST_USERID +" text);");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ DBConstants.TABLE_GOOGLEPLACE +" ("
                +DBConstants.COLUMN_GOOGLEPLACE_ID+ " integer primary key autoincrement, "
                +DBConstants.COLUMN_GOOGLEPLACE_PLACEID+" text not NULL, "
                +DBConstants.COLUMN_GOOGLEPLACE_NAME +" text, "
                +DBConstants.COLUMN_GOOGLEPLACE_FK_USERLIST +" integer, "
                +" FOREIGN KEY ("+DBConstants.COLUMN_GOOGLEPLACE_FK_USERLIST
                +") REFERENCES "+DBConstants.TABLE_USERLIST+"("+DBConstants.COLUMN_USERLIST_ID+"));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exits "+DBConstants.TABLE_USERLIST);
        sqLiteDatabase.execSQL("drop table if exits "+DBConstants.TABLE_GOOGLEPLACE);
        onCreate(sqLiteDatabase);
    }


}
