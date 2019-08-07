package com.metropolitan.cs330_dz07;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import android.net.Uri;
import android.text.TextUtils;

public class KonsultacijeProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.metropolitan.cs330_dz07.KonsultacijeProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/predmeti";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "sifra_predmeta";
    static final String NAME = "naziv_predmeta";
    static final String RAD = "rad_sa_studentima";

    private static HashMap<String, String> PREDMETI_PROJECTION_MAP;

    static final int PREDMETI = 1;
    static final int PREDMET_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "predmeti", PREDMETI);
        uriMatcher.addURI(PROVIDER_NAME, "predmeti/#", PREDMET_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "konsultacije";
    static final String PREDMETI_TABLE_NAME = "predmeti";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + PREDMETI_TABLE_NAME +
                    " (sifra_predmeta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " naziv_predmeta TEXT NOT NULL, " +
                    " rad_sa_studentima TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  PREDMETI_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(	PREDMETI_TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PREDMETI_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case PREDMETI:
                qb.setProjectionMap(PREDMETI_PROJECTION_MAP);
                break;

            case PREDMET_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == ""){
            sortOrder = NAME;
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case PREDMETI:
                count = db.delete(PREDMETI_TABLE_NAME, selection, selectionArgs);
                break;

            case PREDMET_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( PREDMETI_TABLE_NAME, _ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PREDMETI:
                count = db.update(PREDMETI_TABLE_NAME, values, selection, selectionArgs);
                break;

            case PREDMET_ID:
                count = db.update(PREDMETI_TABLE_NAME, values,
                        _ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case PREDMETI:
                return "vnd.android.cursor.dir/vnd.example.PREDMETI";
            case PREDMET_ID:
                return "vnd.android.cursor.item/vnd.example.PREDMETI";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

}
