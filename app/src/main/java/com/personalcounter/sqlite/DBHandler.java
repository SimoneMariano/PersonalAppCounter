package com.personalcounter.sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class DBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "PersonalRecord.db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "Record";

    // below variable is for our date column.
    private static final String COLUMN_DATE = "date";

    // below variable is for our player1 column.
    private static final String COLUMN_player1 = "player1";

    // below variable is for our player2 column.
    private static final String COLUMN_player2 = "player2";

    // below variable is for our result column.
    private static final String COLUMN_result = "result";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_DATE + " TEXT, "
                + COLUMN_player1 + " TEXT, "
                + COLUMN_player2 + " TEXT, "
                + COLUMN_result + " TEXT)";
        // at last we are calling a exec sql
        db.execSQL(query);
    }

    /**
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        onUpgrade(db, oldVersion, newVersion);
    }
    public void updateRecord(String date, String player1, String player2, String result) {
        ContentValues values = new ContentValues();
        String txt = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txt = String.valueOf(LocalDate.now());
        }
        // we are inserting our values
        // in the form of key-value pair.
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_player1, player1);
        values.put(COLUMN_player2, player2);
        values.put(COLUMN_result, result);

        // after adding all values we are passing
        // content values to our table.
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, "date=?", new String[]{txt});

        // at last we are
        // closing our database.
        db.close();
    }

    public ArrayList<RecordModel> fetchRecord() {

        SQLiteDatabase db = this.getReadableDatabase();
        String data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            data = String.valueOf(LocalDate.now());
        }

        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME + " ORDER BY " + COLUMN_DATE + " DESC", null);

        ArrayList<RecordModel> arrRecords = new ArrayList<>();

        while(cursor.moveToNext()){

            RecordModel model = new RecordModel("","","","");

            model.date = cursor.getString(0);
            model.player1 = cursor.getString(1);
            model.player2 = cursor.getString(2);
            model.result = cursor.getString(3);

            arrRecords.add(model);
        }

        cursor.close();
        db.close();

        return arrRecords;
    }

    public void addRecord(String date, String player1, String player2, String result) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        // we are inserting our values
        // in the form of key-value pair.
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_player1, player1);
        values.put(COLUMN_player2, player2);
        values.put(COLUMN_result, result);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are
        // closing our database.
        db.close();
    }
}
