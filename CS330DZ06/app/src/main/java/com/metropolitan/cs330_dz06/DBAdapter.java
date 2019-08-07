package com.metropolitan.cs330_dz06;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
    static final String KEY_INDEX = "broj_indeksa";
    static final String KEY_NAME = "ime";
    static final String KEY_POINTS = "broj_bodova";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "ispit";
    static final String DATABASE_TABLE = "studenti";
    static final int DATABASE_VERSION = 3;

    static final String DATABASE_CREATE = "create table studenti " +
            "(broj_indeksa integer primary key, ime text not null, " +
            "broj_bodova integer not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Updating database version from " + oldVersion + " to version " +
                    newVersion + ", that will destroy the existing data.");
            db.execSQL("DROP TABLE IF EXISTS studenti");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertStudent(int broj_indeksa, String ime, int broj_bodova) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_INDEX, broj_indeksa);
        initialValues.put(KEY_NAME, ime);
        initialValues.put(KEY_POINTS, broj_bodova);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteStudent(long index) {
        return db.delete(DATABASE_TABLE, KEY_INDEX + "=" + index, null) > 0;
    }

    public Cursor getAllStudents() {
        return db.query(DATABASE_TABLE, new String[]{KEY_INDEX, KEY_NAME, KEY_POINTS},
                null, null, null, null, null);
    }

    public Cursor getStudent(long index) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{KEY_INDEX, KEY_NAME, KEY_POINTS},
                        KEY_INDEX + "=" + index, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateStudent(long index, String ime, int broj_bodova) {
        ContentValues args = new ContentValues();
        args.put(KEY_INDEX, index);
        args.put(KEY_NAME, ime);
        args.put(KEY_POINTS, broj_bodova);

        return db.update(DATABASE_TABLE, args, KEY_INDEX + "=" + index, null) > 0;
    }

}
