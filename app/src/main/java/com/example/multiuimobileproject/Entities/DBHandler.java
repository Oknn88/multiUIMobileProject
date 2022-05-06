package com.example.multiuimobileproject.Entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "KiloBoyDB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "tblKiloBoy";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String KILO_COL = "kilo";

    // below variable id for our course duration column.
    private static final String BOY_COL = "boy";

    // below variable for our course description column.
    private static final String TARIH_COL = "tarih";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KILO_COL + " TEXT,"
                + BOY_COL + " TEXT,"
                + TARIH_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewEntry(String kisiKilo, String kisiBoy, String tarih) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(KILO_COL, kisiKilo);
        values.put(BOY_COL, kisiBoy);
        values.put(TARIH_COL, tarih);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
    public ArrayList<kiloBoy> getAllItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);

        ArrayList<kiloBoy> kb = new ArrayList<kiloBoy>();

        Cursor cursor;

        try
        {
            // ask the database object to create the cursor.
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{ID_COL, KILO_COL, BOY_COL,TARIH_COL},
                    null, null, null, null, null
            );

            // move the cursors pointer to position zero.
            cursor.moveToFirst();

            // if there is data after the current cursor position, add it
            // to the ArrayList.
            if (!cursor.isAfterLast())
            {
                do
                {
                    kiloBoy data = new kiloBoy();

                    data.kilo = (cursor.getString(1));
                    data.boy = (cursor.getString(2));
                    data.tarih = (cursor.getString(3));

                    kb.add(data);
                }
                // move the cursor's pointer up one position.
                while (cursor.moveToNext());
            }
        }
        catch (SQLException e)
        {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        return kb;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
