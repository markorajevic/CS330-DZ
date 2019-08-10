package com.metropolitan.cs330_pz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import com.metropolitan.cs330_pz.entity.User;

public class DBHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movie.db";

    static final String TABLE_USER = "user";
    static final String TABLE_MOVIE = "movie";

    static final String COLUMN_USER_ID = "user_id";
    static final String COLUMN_USER_NAME = "user_name";
    static final String COLUMN_USER_EMAIL = "user_email";
    static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_AVATAR = "user_avatar";

    static final String COLUMN_MOVIE_ID = "movie_id";
    static final String COLUMN_MOVIE_NAME = "movie_name";
    static final String COLUMN_MOVIE_GENRE = "movie_genre";
    static final String COLUMN_MOVIE_DESCRIPTION = "movie_description";
    static final String COLUMN_MOVIE_RATING = "movie_rating";
    static final String COLUMN_MOVIE_IMG = "movie_img";

    static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_AVATAR + " BLOB" + ")";

    static final String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE + "("
            + COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MOVIE_NAME + " TEXT,"
            + COLUMN_MOVIE_GENRE + " TEXT," + COLUMN_MOVIE_DESCRIPTION + " TEXT,"
            + COLUMN_MOVIE_RATING + " INTEGER,"
            + COLUMN_MOVIE_IMG + " TEXT" + ")";

    static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    static final String DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + TABLE_MOVIE;

    private SQLiteDatabase db;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_MOVIE_TABLE);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL(DROP_USER_TABLE);
            db.execSQL(DROP_MOVIE_TABLE);
            onCreate(db);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void addUser(User user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String email){
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(((cursor != null) && (cursor.getCount() > 0))){
            Log.d("CursorCount", "Cursor count: " + cursorCount);
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String email, String password){
        String[] columns = {
                COLUMN_USER_ID
        };
        db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0){
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public String getUserByEmail(String email){

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        String username = "";
        while(cursor.moveToNext()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
        }
        cursor.close();
        db.close();
        return username;
    }

    public byte[] getImage(String email){
        String[] columns = {
                COLUMN_USER_AVATAR
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        byte[] avatar = null;
        while(cursor.moveToNext()) {
            avatar = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_USER_AVATAR));
        }
        cursor.close();
        db.close();
        return avatar;
    }

    public void updateImage(String email, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_AVATAR, image);

        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{email});
        db.close();
    }

    public void insertMovie(String name, String genre, String description, int rating, byte[] img){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_NAME, name);
        values.put(COLUMN_MOVIE_GENRE, genre);
        values.put(COLUMN_MOVIE_DESCRIPTION, description);
        values.put(COLUMN_MOVIE_RATING, rating);
        values.put(COLUMN_MOVIE_IMG, img);

        db.insert(TABLE_MOVIE, null, values);
        Log.d("NAME", "NAME: " + name);
        db.close();
    }

    public void updateMovie(int id, String name, String genre, String description,
                           int rating, byte[] img){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_NAME, name);
        values.put(COLUMN_MOVIE_GENRE, genre);
        values.put(COLUMN_MOVIE_DESCRIPTION, description);
        values.put(COLUMN_MOVIE_RATING, rating);
        values.put(COLUMN_MOVIE_IMG, img);

        db.update(TABLE_MOVIE, values, COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteMovie(int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM movie WHERE movie_id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getAllMovies() {
        String[] columns = {
                COLUMN_MOVIE_ID,
                COLUMN_MOVIE_NAME,
                COLUMN_MOVIE_DESCRIPTION,
                COLUMN_MOVIE_RATING,
                COLUMN_MOVIE_IMG
        };

        return db.query(TABLE_MOVIE,
                columns,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
